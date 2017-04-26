package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.inContract.application.criteria.InvoiceCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InvoiceDto;
import com.sunesoft.ancon.core.inContract.application.inContractFactory.InvoiceFactory;
import com.sunesoft.ancon.core.inContract.domain.InContract;
import com.sunesoft.ancon.core.inContract.domain.InContractRepository;
import com.sunesoft.ancon.core.inContract.domain.Invoice;
import com.sunesoft.ancon.core.inContract.domain.InvoiceRepository;
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
import java.util.*;

/**
 * Created by xiazl on 2016/11/23.
 */
@Service("invoiceService")
public class InvoiceServiceImpl extends GenericHibernateFinder implements InvoiceService {
    @Autowired
    InvoiceRepository repository;
    @Autowired
    InContractRepository inContractRepository;
    @Autowired
    SecondPartyService secondPartyService;
    @Autowired
    DeptmentService deptmentService;

    @Override
    public CommonResult create(InvoiceDto dto) {
        UniqueResult<InvoiceDto> uniqueResult = fillAB(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
//        Invoice invoice = repository.getByNum(dto.getNum().trim());
//        if (invoice != null&& invoice.getIsActive()) return ResultFactory.commonError("该编号的开票已经存在");
        Invoice invoice1 = new Invoice();
        invoice1 = InvoiceFactory.convertFromDto(uniqueResult.getT(), invoice1);
        Long l = repository.save(invoice1);
        if (l != null) {
            CommonResult c = iPercent(dto.getInId());
            if (c.getIsSuccess()) return ResultFactory.commonSuccess(l);
        }
        return ResultFactory.commonError("创建新发票失败");
    }

    @Override
    public CommonResult edit(InvoiceDto dto) {
        UniqueResult<InvoiceDto> uniqueResult = fillAB(dto);
        if (!uniqueResult.getIsSuccess()) return new CommonResult(false, uniqueResult.getMsg());
//        Invoice invoice = repository.getByNum(dto.getNum());
        Invoice invoice1 = repository.get(dto.getId());
//        if ((invoice1!=null&& invoice1.getIsActive() && !invoice1.getNum().equals(dto.getNum()))&& invoice != null&& invoice.getIsActive())
//            return ResultFactory.commonError("修改后编号后的开票已经存在");
        invoice1 = InvoiceFactory.convertFromDto(uniqueResult.getT(), invoice1);
        Long l = repository.save(invoice1);
        if (l != null) {
            CommonResult c = iPercent(dto.getInId());//设置进项合同开票率
            if (c.getIsSuccess()) return ResultFactory.commonSuccess(l);
        }
        return ResultFactory.commonError("创建新发票失败");
    }

    @Override
    public InvoiceDto getById(Long id) {
        Invoice invoice = repository.get(id);
        if (invoice != null &&invoice.getIsActive()) {
            InvoiceDto dto = InvoiceFactory.convertToDto(invoice);
            InContract inContract = inContractRepository.get(dto.getInId());//这里页面上的进项合同一定存在
            dto.setTotalMoney(inContract.getValue().subtract(inContract.getBillMoney()));
            return dto;
        }
        return null;
    }

//    @Override
//    public InvoiceDto getByNum(String num) {
//        Invoice invoice = repository.getByNum(num);
//        if (invoice != null&&invoice.getIsActive()) return InvoiceFactory.convertToDto(invoice);
//        return null;
//    }

    @Override
    public CommonResult delete(Long id) {
        Invoice invoice = repository.get(id);
        if (invoice == null||!invoice.getIsActive()) return ResultFactory.commonError("该开票不存在!");
        invoice.setIsActive(false);
        repository.save(invoice);
        CommonResult c = iPercent(invoice.getInId());
        if (c.getIsSuccess())
            return ResultFactory.commonSuccess();
        return ResultFactory.commonError("操作失败");
    }

    @Override
    public CommonResult delete(List<Long> ids) {
        Criteria criteria = getSession().createCriteria(Invoice.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.in("id", ids));
        List<Invoice> list = criteria.list();
        if (list != null && list.size() > 0) {
            for (Invoice invoice : list) {
                invoice.setIsActive(false);
                repository.save(invoice);
                iPercent(invoice.getInId());
            }
        }
        return ResultFactory.commonError("该开票不存在!");
    }

    @Override
    public PagedResult<InvoiceDto> pg(InvoiceCriteria criteria) {
        PagedResult<Invoice> pagedResult = repository.paged(criteria);
        List<Invoice> list = repository.getList(criteria.getId());
        return new PagedResult<InvoiceDto>(pagedResult.getItems().size() > 0 ? InvoiceFactory.convertList(pagedResult.getItems(), list) : Collections.EMPTY_LIST, pagedResult.getPageNumber(), pagedResult.getPageSize(), pagedResult.getTotalItemsCount());
    }

    @Override
    public List<Map<String, Object>> getCount(String beginTime, String endTime) {
        String sql = "  SELECT SUM(IFNULL(s.smoney,0)) totals,SUM(IFNULL(s.cmoney,0)) totalc,s.party_b partyB from ancon_sys_Invoice s WHERE s.is_active=1 ";

        if (!StringUtils.isNullOrWhiteSpace(beginTime)) {
            sql = sql + " AND s.invoice_date >= '" + beginTime + "' ";
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)) {
            sql = sql + " AND s.invoice_date <= '" + endTime + "' ";
        }
//        if(!StringUtils.isNullOrWhiteSpace(beginTime) || !StringUtils.isNullOrWhiteSpace(endTime)){
//            int index=sql.lastIndexOf("AND");
//            sql=sql.substring(0,index);
//        }
//        if (StringUtils.isNullOrWhiteSpace(beginTime) && StringUtils.isNullOrWhiteSpace(endTime)) {
//            sql = "  SELECT SUM(IFNULL(s.smoney,0)) totals,SUM(IFNULL(s.cmoney,0)) totalc,s.party_b partyB from ancon_sys_Invoice1 s ";
//        }

        sql = sql + "  GROUP BY s.party_b  ORDER BY (SUM(IFNULL(s.smoney,0))+SUM(IFNULL(s.cmoney,0))) DESC LIMIT 20 ";

        Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }

    /**
     * 填充甲方单位和乙方单位
     *
     * @param dto
     * @return
     */
    private UniqueResult<InvoiceDto> fillAB(InvoiceDto dto) {
        if (dto.getPartyBId() != null) {
            ContractorPartyDto contractorPartyDto = secondPartyService.getById(dto.getPartyBId());
            if (contractorPartyDto == null) return new UniqueResult<InvoiceDto>("该乙方单位不存在！");
            dto.setPartyB(contractorPartyDto.getName());
        }
        if (dto.getPartyAId() != null) {
            DeptmentDto deptmentDto = deptmentService.getByDeptId(dto.getPartyAId());
            if (deptmentDto == null) return new UniqueResult<InvoiceDto>("该分公司不存在");
            dto.setPartyA(deptmentDto.getDeptName());
        }
        InContract inContract = inContractRepository.get(dto.getInId());

        if (inContract == null||!inContract.getIsActive()) return new UniqueResult<InvoiceDto>("该进项合同不存在！");
        if(DateHelper.parse(dto.getSinvoiceDate(),"yyyy-MM-dd").before(inContract.getBillDate())) return  new UniqueResult<InvoiceDto>("发票时间不能晚于该合同的签约时间");
        dto.setInName(inContract.getName());
        dto.setInNum(inContract.getNum());

        return new UniqueResult<InvoiceDto>(dto);
    }

    /**
     * 设置开票率
     *
     * @param inId
     * @return
     */
    private CommonResult iPercent(Long inId) {
        BigDecimal total = BigDecimal.ZERO;
        List<Invoice> list = repository.getList(inId);
        if (list != null && list.size() > 0) {
            for (Invoice invoice : list) {
                total = total.add(invoice.getCmoney() == null ? invoice.getSmoney() : invoice.getCmoney());
            }
        }
        InContract inContract = inContractRepository.get(inId);
        if (inContract == null ||!inContract.getIsActive()) return new CommonResult(false, "该进项合同不存在！");
        inContract.setBillMoney(total);
        BigDecimal radio = total.divide(inContract.getValue(), 4, BigDecimal.ROUND_HALF_EVEN);

        inContract.setiPercent((radio.multiply(BigDecimal.valueOf(100))).doubleValue());
        return ResultFactory.commonSuccess(inContractRepository.save(inContract));
    }

}
