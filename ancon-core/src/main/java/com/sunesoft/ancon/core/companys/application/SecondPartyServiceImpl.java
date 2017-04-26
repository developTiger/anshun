package com.sunesoft.ancon.core.companys.application;

import com.sunesoft.ancon.core.companys.application.criteria.SecondPartyCriteria;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.companys.domain.ContractorParty;
import com.sunesoft.ancon.core.companys.domain.hibernate.ContractorDbSupport;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DtoFactory;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
@Service("secondPartyService")
public class SecondPartyServiceImpl implements SecondPartyService {

    @Autowired
    ContractorDbSupport dbSupport;

    @Override
    public CommonResult addFirstParty(ContractorPartyDto dto) {

        List<ContractorParty> list=getByName(dto.getName());
        if(list!=null&&list.size()>0){
            return new CommonResult(false,"名称已存在");
        }

        ContractorParty firstParty = DtoFactory.convert(dto, new ContractorParty());

        Long id= (Long) dbSupport.getParameter().add(firstParty);
        return new CommonResult(id>0?true:false,"",id);
    }

    public  List<ContractorParty> getByName(String name){
        Criteria criteria = dbSupport.getSession().createCriteria(ContractorParty.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("name", "'" + name + "'"));
        return criteria.list();
    }

    @Override
    public boolean deleteFirstParty(Long[] id) {
        Criteria criteria = dbSupport.getSession().createCriteria(ContractorParty.class);
        if (id != null && id.length>0){
            criteria.add(Restrictions.in("id", id));
        }
        List<ContractorParty> list = criteria.list();
        for (ContractorParty value:list){
            value.setIsActive(false);
            dbSupport.getParameter().update(value);
        }
        return true;
    }

    @Override
    public CommonResult updateFirstParty(ContractorPartyDto dto) {

        dbSupport.getParameter().update(DtoFactory.convert(dto,new ContractorParty()));
        return new CommonResult(true);
    }

    @Override
    public PagedResult<ContractorPartyDto> findPages(SecondPartyCriteria valueCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(ContractorParty.class);
        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(valueCriteria.getNum())) {
            criteria.add(Restrictions.eq("num", valueCriteria.getNum()));
        }

        if (!StringUtils.isNullOrWhiteSpace(valueCriteria.getRepresentativePerson())) {
            criteria.add(Restrictions.eq("representativePerson",valueCriteria.getRepresentativePerson() ));
        }

        if (!StringUtils.isNullOrWhiteSpace(valueCriteria.getName())) {
            criteria.add(Restrictions.like("name", "%" + valueCriteria.getName() + "%"));
        }

        //获取总记录数
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((valueCriteria.getPageNumber() - 1) * valueCriteria.getPageSize()).setMaxResults(valueCriteria.getPageSize());
        List<ContractorParty> list = criteria.list();
        List<ContractorPartyDto> constructionValueDtos = new ArrayList<>();
        for (ContractorParty value:list){
            ContractorPartyDto dto = DtoFactory.convert(value,new ContractorPartyDto());
            constructionValueDtos.add(dto);
        }
        return new PagedResult<ContractorPartyDto>(constructionValueDtos, valueCriteria.getPageNumber(), valueCriteria.getPageSize(), totalCount);
    }

    @Override
    public Boolean deletePartyB(Long[] ids) {
        Criteria criterion = dbSupport.getSession().createCriteria(ContractorParty.class);
        if (ids == null || ids.length < 1) {
            return false;
        }
        criterion.add(Restrictions.in("id", ids));
        List<ContractorParty> list = criterion.list();
        for (ContractorParty role : list) {
            role.setIsActive(false);
            Date time = new Date();
            role.setLastUpdateTime(time);
            dbSupport.getParameter().update(role);
        }
        return true;
    }

    @Override
    public List<ContractorPartyDto> getAll() {
        Criteria criterion = dbSupport.getSession().createCriteria(ContractorParty.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<ContractorParty> list = criterion.list();
        List<ContractorPartyDto> dtos = new ArrayList<ContractorPartyDto>();
        if (list != null && list.size() > 0) {
            for (ContractorParty eg : list) {
                ContractorPartyDto dto = new ContractorPartyDto();
                dto = DtoFactory.convert(eg, dto);
                dtos.add(dto);
            }
        }
        return dtos;
    }



    @Override
    public List<ContractorPartyDto> getByTypeAll(Integer number) {
       /* String hql="select * from ancon_sys_contractor_party where type&"+number;
        List<ContractorParty> list=dbSupport.getParameter().queryByHql(hql,null);
        List<ContractorPartyDto> dtos=new ArrayList<>();
        if(list!=null && list.size()>0){
            for(ContractorParty cp :list){
                dtos.add(DtoFactory.convert(cp,new ContractorPartyDto()));
            }
        }
        return dtos;*/

        String hql = "from ContractorParty ap where ap.type=3 or ap.type="+number;

        List<ContractorParty> list = dbSupport.getParameter().queryByHql(hql,null);
        List<ContractorPartyDto> dtos = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (ContractorParty contractorParty : list) {

                dtos.add(DtoFactory.convert(contractorParty,new ContractorPartyDto()));
            }
        }
        return dtos;

    }

    @Override
    public ContractorPartyDto getByTypeAndName(Integer number, String name) {
        String hql=" from ContractorParty t where (t.type=3 or t.type="+number+") and t.name='"+name+"'";
        List<ContractorParty> list=dbSupport.getParameter().queryByHql(hql,null);
        if(list!=null&&list.size()>0)
            return DtoFactory.convert(list.get(0), new ContractorPartyDto());
        return null;
    }

    @Override
    public ContractorPartyDto getById(Long id) {
        ContractorParty value = dbSupport.getParameter().get(id);
        ContractorPartyDto dto = DtoFactory.convert(value,new ContractorPartyDto());
        return dto;
    }

}
