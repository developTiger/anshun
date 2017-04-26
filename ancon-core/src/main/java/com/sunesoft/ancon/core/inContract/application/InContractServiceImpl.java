package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;

import com.sunesoft.ancon.core.inContract.application.criteria.InContractCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.application.inContractFactory.InContractFactory;
import com.sunesoft.ancon.core.inContract.domain.InContract;
import com.sunesoft.ancon.core.inContract.domain.InContractRepository;
import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.results.ResultFactory;
import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by xiazl on 2016/11/23.
 */
@Service("inContractService")
public class InContractServiceImpl extends GenericHibernateFinder implements InContractService {

    @Autowired
    InContractRepository repository;

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    SecondPartyService secondPartyService;
    @Autowired
    DeptmentService deptmentService;

    @Override
    public CommonResult create(InContractDto dto) {
        InContract contract = repository.getByNum(dto.getNum());
        if (contract != null && contract.getIsActive()) return ResultFactory.commonError("该合同编号的合同已经存在");
        InContract con = new InContract();
        //todo huoqu销售合同并且判断是否存在
        UniqueResult<InContractDto> uniqueResult = fill(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
        con = InContractFactory.convertFromDto(uniqueResult.getT(), con);
        return ResultFactory.commonSuccess(repository.save(con));
    }

    @Override
    public CommonResult edit(InContractDto dto) {
        InContract contract = repository.get(dto.getId());
        InContract contract1 = repository.getByNum(dto.getNum());
        if (contract!=null&&contract.getIsActive()&&!contract.getNum().equals(dto.getNum()) && (contract1 != null && contract1.getIsActive()))
            return ResultFactory.commonError("修改后的合同编号已经存在");
        UniqueResult<InContractDto> uniqueResult = fill(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
        contract = InContractFactory.convertFromDto(uniqueResult.getT(), contract);
        return ResultFactory.commonSuccess(repository.save(contract));
    }
//ajgiaht9qja
    @Override
    public InContractDto getById(Long id) {
        InContract contract = repository.get(id);
        if (contract == null || !contract.getIsActive()) return null;
        return InContractFactory.convertToDto(contract);
    }

    @Override
    public InContractDto getByNum(String num) {
        InContract contract = repository.getByNum(num);
        return InContractFactory.convertToDto(contract);
    }

    @Override
    public CommonResult delete(Long id) {
        InContract contract = repository.get(id);
        if (contract == null || !contract.getIsActive()) return ResultFactory.commonError("该合同不存在！");
        contract.setIsActive(false);
        return ResultFactory.commonSuccess(repository.save(contract));
    }

    @Override
    public CommonResult delete(List<Long> ids) {
        Criteria criteria = getSession().createCriteria(InContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.in("id", ids));
        List<InContract> list = criteria.list();
        for (InContract contract : list) {
            contract.setIsActive(false);
            repository.save(contract);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<InContractDto> getByName(String name) {
        List<InContract> list = repository.getByName(name);
        return InContractFactory.convertDtoList(list);
    }

    @Override
    public PagedResult<InContractDto> pg(InContractCriteria inContractCriteria) {
        PagedResult<InContract> pg = repository.paged(inContractCriteria);
        return new PagedResult<InContractDto>(pg.getItems().size() > 0 ? InContractFactory.convertDtoList(pg.getItems()) : Collections.EMPTY_LIST, pg.getPageNumber(), pg.getPageSize(), pg.getTotalItemsCount());
    }

    @Override
    public List<Map<String, Object>> getCount(String beginTime, String endTime) {
        // todo 默认查询全部乙方


        String sql = " SELECT SUM(s.contract_money) totalMoney,s.partyB partyB  from ancon_sys_in_contract s WHERE  s.is_active=1 ";
        if (!StringUtils.isNullOrWhiteSpace(beginTime)) {
            sql = sql + " AND  s.bill_date >= '" + beginTime + "' ";
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)) {
            sql = sql + " AND  s.bill_date <= '" + endTime + "' ";
        }
//        if (!StringUtils.isNullOrWhiteSpace(beginTime) || !StringUtils.isNullOrWhiteSpace(endTime)) {
//            int index = sql.lastIndexOf("AND");
//            sql = sql.substring(0, index);
//        }
//        if (StringUtils.isNullOrWhiteSpace(beginTime) && StringUtils.isNullOrWhiteSpace(endTime)) {
//            sql = " SELECT SUM(s.contract_money) totalMoney,s.partyB partyB  from ancon_sys_in_contract1 s ";
//        }

        sql = sql + " GROUP BY s.partyB ORDER BY totalMoney DESC LIMIT 20 ";
        Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    /**
     * 根据乙方公司查找进项合同列表
     *
     * @return
     */
    public List<InContractDto> getAll() {
        List<InContract> list = repository.getAll();
        if (list == null || list.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        return InContractFactory.convertDtoList(list);

    }

    /**
     * 完整数据
     *
     * @param dto
     * @return
     */
    private UniqueResult<InContractDto> fill(InContractDto dto) {
        // 销售合同
        if (dto.getSalId() != null) {
            SaleContractDto sale = saleContractService.getById(dto.getSalId());
            if (sale == null) return new UniqueResult<InContractDto>("销售合同不存在！");
            dto.setSalName(sale.getName());
            dto.setSalNum(sale.getNum());
        }
        //乙方单位
        if (dto.getPartyBId() != null) {
            ContractorPartyDto secondPartyDto = secondPartyService.getById(dto.getPartyBId());
            if (secondPartyDto == null) return new UniqueResult<InContractDto>("该乙方单位不存在！");
            dto.setPartyB(secondPartyDto.getName());
        }
        //所属分公司
        if (dto.getCompanyId() != null) {
            DeptmentDto deptmentDto = deptmentService.getByDeptId(dto.getCompanyId());
            if (deptmentDto == null) return new UniqueResult<InContractDto>("该分公司不存在！");

            dto.setCompany(deptmentDto.getDeptName());
            dto.setCompanyType(deptmentDto.getType() == null ? 1 : deptmentDto.getType());
        }

        return new UniqueResult<InContractDto>(dto);

    }
}
