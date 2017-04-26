
package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.inContract.application.criteria.PayCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.PayDto;
import com.sunesoft.ancon.core.inContract.application.inContractFactory.PayFactory;
import com.sunesoft.ancon.core.inContract.domain.*;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.results.ResultFactory;
import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by xiazl on 2016/11/23.
 */
@Service("paySerivce")
public class PayServiceImpl extends GenericHibernateFinder implements PayService {
    @Autowired
    InContractRepository repository;
    @Autowired
    PayRepository payRepository;
    @Autowired
    SecondPartyService secondPartyService;
    @Autowired
    DeptmentService deptmentService;


    @Override
    public CommonResult create(PayDto dto) {
        Pay pay = new Pay();

        UniqueResult<PayDto> uniqueResult = fillA(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
        pay = PayFactory.convertFromDto(uniqueResult.getT(), pay);
        InContract contract = repository.getByNum(dto.getInNum().trim());
        if (contract == null) return ResultFactory.commonError("请关联合同！");
        pay.setInName(contract.getName());
        pay.setInId(contract.getId());
        pay.setInNum(contract.getNum());
        Long l = payRepository.save(pay);
        if (l != null) {
            CommonResult c = pPercent(dto.getInId());
            if (c.getIsSuccess()) return ResultFactory.commonSuccess(l);
        }
        return ResultFactory.commonError("创建付款记录失败");
    }

    @Override
    public CommonResult edit(PayDto dto) {
        UniqueResult<PayDto> uniqueResult = fillA(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
        Pay pay = payRepository.get(dto.getId());
        if (pay == null||!pay.getIsActive()) return ResultFactory.commonError("该付款记录不存在");
        pay = PayFactory.convertFromDto(uniqueResult.getT(), pay);
        InContract contract = repository.getByNum(dto.getInNum().trim());
        if (contract == null) return ResultFactory.commonError("请关联合同！");
        pay.setInName(contract.getName());
        pay.setInId(contract.getId());
        pay.setInNum(contract.getNum());
        Long l = payRepository.save(pay);
        if (l != null) {
            CommonResult c = pPercent(dto.getInId());
            if (c.getIsSuccess()) return ResultFactory.commonSuccess(l);
        }
        return ResultFactory.commonError("创建付款记录失败");
    }

    @Override
    public PayDto getById(Long id) {
        Pay pay = payRepository.get(id);
//        if (pay != null) return PayFactory.convertToDto(pay);
        if (pay != null&&pay.getIsActive()) {
            PayDto dto = PayFactory.convertToDto(pay);
            InContract inContract = repository.get(dto.getInId());
            dto.setTotalMoney(inContract.getValue().subtract(inContract.getPayMoney()));
            return dto;
        }
        return null;
    }


    @Override
    public CommonResult delete(Long id) {
        Pay pay = payRepository.get(id);
        if (pay == null||!pay.getIsActive()) return ResultFactory.commonError("该付款记录不存在！");
        pay.setIsActive(false);
        payRepository.save(pay);
        //减去已付款金额
        CommonResult c = pPercent(pay.getInId());
        if (c.getIsSuccess()) return ResultFactory.commonSuccess();
        return ResultFactory.commonError("操作进项合同已付款失败");
    }

    @Override
    public CommonResult delete(List<Long> ids) {
        Criteria criteria = getSession().createCriteria(Pay.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.in("id", ids));
        List<Pay> list = criteria.list();
        for (Pay pay : list) {
            pay.setIsActive(false);
            payRepository.save(pay);
            pPercent(pay.getInId());
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<PayDto> pg(PayCriteria criteria) {
        PagedResult<Pay> pg = payRepository.paged(criteria);
        List<Pay> list = payRepository.getList(criteria.getId());
        return new PagedResult<PayDto>(pg.getItems() != null && pg.getItems().size() > 0 ? PayFactory.convertList(pg.getItems(), list) : Collections.EMPTY_LIST, criteria.getPageNumber(), criteria.getPageSize(), pg.getTotalItemsCount());
    }

    @Override
    public List<Map<String, Object>> getCount(String beginTime, String endTime) {

        String sql = " SELECT SUM(s.money) totalPay,s.partyB partyB  from ancon_in_pay s WHERE s.is_active=1 ";

        if (!StringUtils.isNullOrWhiteSpace(beginTime)) {
            sql = sql + " AND s.pay_time >= '" + beginTime + "' ";
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)) {
            sql = sql + " AND  s.pay_time <= '" + endTime + "' ";
        }
//        if(!StringUtils.isNullOrWhiteSpace(beginTime) || !StringUtils.isNullOrWhiteSpace(endTime)){
//            int index=sql.lastIndexOf("AND");
//            sql=sql.substring(0,index);
//        }
//        if (StringUtils.isNullOrWhiteSpace(beginTime) && StringUtils.isNullOrWhiteSpace(endTime)) {
//            sql = " SELECT SUM(s.money) totalPay,s.partyB partyB  from ancon_in_pay s ";
//        }

        sql = sql + "  GROUP BY s.partyB ORDER BY totalPay DESC LIMIT 20";

        Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }

    /**
     * 填充数据
     *
     * @param dto
     * @return
     */
    private UniqueResult<PayDto> fillA(PayDto dto) {
        if (dto.getInId() != null) {
            InContract inContract = repository.get(dto.getInId());
            if (inContract == null ||!inContract.getIsActive()) return new UniqueResult<PayDto>("该进项合同不存在！");
            if(DateHelper.parse(dto.getSpayTime(),"yyyy-MM-dd").before(inContract.getBillDate())) return new UniqueResult<PayDto>("付款时间不能早于该合同签约时间");
            dto.setInNum(inContract.getNum());
            dto.setInName(inContract.getName());
        }
        if (dto.getPartyBId() != null) {
            ContractorPartyDto contractorPartyDto = secondPartyService.getById(dto.getPartyBId());
            if (contractorPartyDto == null) return new UniqueResult<PayDto>("该乙方合作单位不存在");
            dto.setPartyB(contractorPartyDto.getName());
        }
        if (dto.getCompanyId() != null) {
            DeptmentDto deptmentDto = deptmentService.getByDeptId(dto.getCompanyId());
            if (deptmentDto == null) return new UniqueResult<PayDto>("该分公司不存在");
            dto.setCompany(deptmentDto.getDeptName());
        }
        return new UniqueResult<PayDto>(dto);
    }

    /**
     * 设置开票率
     *
     * @param inId
     * @return
     */
    private CommonResult pPercent(Long inId) {
        BigDecimal total = BigDecimal.ZERO;
        List<Pay> list = payRepository.getList(inId);
        if (list != null && list.size() > 0) {
            for (Pay pay : list) {
                total = total.add(pay.getMoney() == null ? BigDecimal.ZERO : pay.getMoney());
            }
        }

        InContract inContract = repository.get(inId);
        if (inContract == null ||!inContract.getIsActive()) return new CommonResult(false, "该进项合同不存在！");
//        inContract.setStatus(ContractSpeed.Run);
        inContract.setPayMoney(total);
        BigDecimal radio = total.divide(inContract.getValue(), 4, BigDecimal.ROUND_HALF_EVEN);
        inContract.setpPercent((radio.multiply(BigDecimal.valueOf(100))).doubleValue());
        return ResultFactory.commonSuccess(repository.save(inContract));
    }


}