package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.saleContract.application.criteria.ConstructionValueCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.ConstructionValueDto;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.core.saleContract.domain.ConstructionValue;
import com.sunesoft.ancon.core.saleContract.domain.SaleContract;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DtoFactory;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/25.
 */
@Service("constructionValueService")
public class ConstructionValueServiceImpl implements ConstructionValueService {

    @Autowired
    AnconDbSupport dbSupport;

    @Override
    public Long add(ConstructionValueDto dto) {
        SaleContract saleContract = new SaleContract();
        saleContract.setId(dto.getSaleContractDto().getId());
        ConstructionValue value = new ConstructionValue();
        value.setSaleContract(saleContract);
        ConstructionValue constructionValue = DtoFactory.convert(dto,value);
        return (Long)dbSupport.getConstructionValue().add(constructionValue);
    }

    @Override
    public boolean delete(Long[] id) {
        Criteria criteria = dbSupport.getSession().createCriteria(ConstructionValue.class);
        if (id != null && id.length>0){
            criteria.add(Restrictions.in("id",id));
        }
        List<ConstructionValue> list = criteria.list();
        for (ConstructionValue value:list){
            value.setIsActive(false);
            dbSupport.getConstructionValue().update(value);
        }
        return true;
    }

    @Override
    public void update(ConstructionValueDto dto) {
        ConstructionValue constructionValue=dbSupport.getConstructionValue().get(dto.getId());

        constructionValue.setBeginTime(dto.getBeginTime());
        constructionValue.setEndTime(dto.getEndTime());
        constructionValue.setOutputValue(dto.getOutputValue());
        constructionValue.setRemark(dto.getRemark());

        constructionValue.setLastUpdatePerson(dto.getLastUpdatePerson());

        dbSupport.getConstructionValue().update(constructionValue);

    }

    @Override
    public ConstructionValueDto getById(Long id) {
        ConstructionValue value = dbSupport.getConstructionValue().get(id);
        ConstructionValueDto dto = DtoFactory.convert(value,new ConstructionValueDto());
        return dto;
    }

    @Override
    public List<ConstructionValueDto> getAll() {
        Criteria criteria = dbSupport.getSession().createCriteria(ConstructionValue.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<ConstructionValue> list = criteria.list();
        List<ConstructionValueDto> dtoList = new ArrayList<>();
        for (ConstructionValue value:list){
            ConstructionValueDto dto = DtoFactory.convert(value,new ConstructionValueDto());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public PagedResult<ConstructionValueDto> fingPages(ConstructionValueCriteria valueCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(ConstructionValue.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.createAlias("saleContract","sale");

        if (valueCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("writeDate", valueCriteria.getBeginTime()));
        if (valueCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("writeDate", valueCriteria.getEndTime()));
        if (!StringUtils.isNullOrWhiteSpace(valueCriteria.getName()))
            criteria.add(Restrictions.like("sale.name", "%" + valueCriteria.getName() + "%"));
        if (valueCriteria.getCompnayId() != null)
            criteria.add(Restrictions.eq("sale.companyId",valueCriteria.getCompnayId()));
        if (!StringUtils.isNullOrWhiteSpace(valueCriteria.getJiafangName()))
            criteria.add(Restrictions.eq("sale.jiaFangName",valueCriteria.getJiafangName()));
        if (valueCriteria.getSaleContractId() != null)
            criteria.add(Restrictions.eq("sale.id",valueCriteria.getSaleContractId()));//树形结构 合同id查找

        //获取总记录数
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.addOrder(Order.desc("writeDate"));
        criteria.setResultTransformer(criteria.ROOT_ENTITY);
        criteria.setFirstResult((valueCriteria.getPageNumber() - 1) * valueCriteria.getPageSize()).setMaxResults(valueCriteria.getPageSize());
        List<ConstructionValue> list = criteria.list();
        List<ConstructionValueDto> constructionValueDtos = new ArrayList<>();

        //计算金额
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (ConstructionValue value:list){
            totalMoney = totalMoney.add(value.getOutputValue());
        }


        for (ConstructionValue value:list){
            SaleContractDto saleContractDto = DtoFactory.convert(value.getSaleContract(),new SaleContractDto());
            ConstructionValueDto valueDto = new ConstructionValueDto();
            valueDto.setSaleContractDto(saleContractDto);
            ConstructionValueDto dto = DtoFactory.convert(value,valueDto);

            dto.setTotalMoney(totalMoney);

            constructionValueDtos.add(dto);
        }
        return new PagedResult<ConstructionValueDto>(constructionValueDtos, valueCriteria.getPageNumber(), valueCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<ConstructionValueDto> getBySaleContractId(Long id) {
        Criteria criteria = dbSupport.getSession().createCriteria(ConstructionValue.class);
        criteria.createAlias("saleContract","sale");
        criteria.add(Restrictions.eq("isActive",true));
        if (id!=null)
            criteria.add(Restrictions.eq("sale.id",id));
        List<ConstructionValue> list = criteria.list();
        List<ConstructionValueDto> constructionValueDtos = new ArrayList<>();
        for(ConstructionValue value:list){
            ConstructionValueDto dto = DtoFactory.convert(value,new ConstructionValueDto());
            constructionValueDtos.add(dto);
        }

        return constructionValueDtos;
    }
}
