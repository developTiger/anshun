package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.saleContract.application.criteria.GatheringCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.GatheringDto;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.core.saleContract.domain.Gathering;
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
@Service("gatheringService")
public class GatheringServiceImpl implements GatheringService {

    @Autowired
    AnconDbSupport dbSupport;

    @Override
    public Long add(GatheringDto dto) {

        Gathering gathering = new Gathering();
        SaleContract saleContract = new SaleContract();
        saleContract.setId(dto.getSaleContractDto().getId());
        gathering.setSaleContract(saleContract);
        Gathering gather = DtoFactory.convert(dto,gathering);
        return (Long)dbSupport.getGathering().add(gather);
    }

    @Override
    public boolean delete(Long[] ids) {
        Criteria criteria = dbSupport.getSession().createCriteria(Gathering.class);
        if (ids != null && ids.length>0){
            criteria.add(Restrictions.in("id",ids));
        }
        List<Gathering> list = criteria.list();
        for (Gathering gathering:list){
            gathering.setIsActive(false);
            dbSupport.getGathering().update(gathering);
        }
        return true;
    }

    @Override
    public void update(GatheringDto dto) {
        Gathering gathering = dbSupport.getGathering().get(dto.getId());

        gathering.setGatheringMoney(dto.getGatheringMoney());
        gathering.setGatheringTime(dto.getGatheringTime());
        gathering.setGatheringType(dto.getGatheringType());
        gathering.setJiafangName(dto.getJiafangName());
        gathering.setRemark(dto.getRemark());
        gathering.setLastUpdatePerson(dto.getLastUpdatePerson());

        dbSupport.getGathering().update(gathering);

    }

    @Override
    public GatheringDto getById(Long id) {
        Gathering gathering= dbSupport.getGathering().get(id);
        GatheringDto dto = DtoFactory.convert(gathering,new GatheringDto());
        return dto;
    }

    @Override
    public List<GatheringDto> getAll() {
        Criteria criteria = dbSupport.getSession().createCriteria(Gathering.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<Gathering> list = criteria.list();
        List<GatheringDto> gatheringDtos = new ArrayList<>();
        for (Gathering gathering:list){
            GatheringDto dto = DtoFactory.convert(gathering,new GatheringDto());
            gatheringDtos.add(dto);
        }
        return gatheringDtos;
    }

    @Override
    public PagedResult<GatheringDto> fingPages(GatheringCriteria gatheringCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(Gathering.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.createAlias("saleContract","sale");

        if (gatheringCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("gatheringTime", gatheringCriteria.getBeginTime()));
        if (gatheringCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("gatheringTime", gatheringCriteria.getEndTime()));
        if (!StringUtils.isNullOrWhiteSpace(gatheringCriteria.getName()))
            criteria.add(Restrictions.like("sale.name", "%" + gatheringCriteria.getName() + "%"));
        if (gatheringCriteria.getCompanyId() != null)
            criteria.add(Restrictions.eq("sale.companyId",gatheringCriteria.getCompanyId()));
        if (!StringUtils.isNullOrWhiteSpace(gatheringCriteria.getJiaFangName()))
            criteria.add(Restrictions.eq("jiafangName",gatheringCriteria.getJiaFangName()));
        if (gatheringCriteria.getSaleContractId() != null)
            criteria.add(Restrictions.eq("sale.id",gatheringCriteria.getSaleContractId()));//树形结构 合同id查找

        //获取总记录数
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.addOrder(Order.desc("gatheringTime"));
        criteria.setResultTransformer(criteria.ROOT_ENTITY);
        criteria.setFirstResult((gatheringCriteria.getPageNumber() - 1) * gatheringCriteria.getPageSize()).setMaxResults(gatheringCriteria.getPageSize());
        List<Gathering> gatherings = criteria.list();
        List<GatheringDto> list = new ArrayList<>();

        //收款总金额 计算
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (Gathering gathering:gatherings){
            totalMoney = totalMoney.add(gathering.getGatheringMoney());
        }

        for (Gathering gathering:gatherings){
            SaleContractDto saleContractDto = DtoFactory.convert(gathering.getSaleContract(),new SaleContractDto());
            GatheringDto gatheringDto = new GatheringDto();
            gatheringDto.setSaleContractDto(saleContractDto);

            gatheringDto.setGatheringTotalMoney(totalMoney);

            GatheringDto dto = DtoFactory.convert(gathering,gatheringDto);
            list.add(dto);
        }
        return new PagedResult<GatheringDto>(list, gatheringCriteria.getPageNumber(), gatheringCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<GatheringDto> getBysaleContractId(Long id) {
        Criteria criteria = dbSupport.getSession().createCriteria(Gathering.class);
        criteria.createAlias("saleContract","sale");

        criteria.add(Restrictions.eq("isActive",true));
        if (id != null)
            criteria.add(Restrictions.eq("sale.id",id));
        List<Gathering> list = criteria.list();
        List<GatheringDto> gatheringDtos = new ArrayList<>();
        for (Gathering gathering:list){
            GatheringDto dto = DtoFactory.convert(gathering,new GatheringDto());
            gatheringDtos.add(dto);
        }

        return gatheringDtos;
    }
}
