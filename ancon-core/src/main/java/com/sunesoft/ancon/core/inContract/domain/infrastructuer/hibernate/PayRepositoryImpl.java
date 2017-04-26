package com.sunesoft.ancon.core.inContract.domain.infrastructuer.hibernate;

import com.sunesoft.ancon.core.inContract.application.criteria.PayCriteria;
import com.sunesoft.ancon.core.inContract.domain.Pay;
import com.sunesoft.ancon.core.inContract.domain.PayRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiazl on 2016/12/1.
 */
@Repository("payRepository")
public class PayRepositoryImpl extends GenericHibernateRepository<Pay, Long> implements PayRepository {
    /**
     * 未用到
     * @param num
     * @return
     */
    @Override
    public Pay getByNum(String num) {
//        Criteria criteria=getSession().createCriteria(Pay.class);
//        criteria.add(Restrictions.eq("isActive",true));
//        criteria.add(Restrictions.eq(""))
        return null;
    }

    @Override
    public PagedResult<Pay> paged(PayCriteria criteria) {
        Criteria criterion = getSession().createCriteria(Pay.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (criteria.getPartyBId()!=null&&criteria.getPartyBId()>0) {
            criterion.add(Restrictions.eq("partyBId", criteria.getPartyBId()));
        }
        if (criteria.getPayStyle() != null) {
            criterion.add(Restrictions.eq("payStyle", criteria.getPayStyle()));
        }
        if (criteria.getId() != null && criteria.getId() > 0) {
            criterion.add(Restrictions.eq("inId", criteria.getId()));
        }
        if (criteria.getBeginTime() != null) {
            criterion.add(Restrictions.ge("payTime", criteria.getBeginTime()));
        }
        if (criteria.getEndTime() != null) {
            criterion.add(Restrictions.le("payTime", criteria.getEndTime()));
        }
        if(!StringUtils.isNullOrWhiteSpace(criteria.getContractName())){
            criterion.add(Restrictions.like("inName","%"+criteria.getContractName()+"%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.addOrder(Order.desc("payTime")).addOrder(Order.desc("createDateTime"));
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        return new PagedResult<Pay>(criterion.list() != null && criterion.list().size() > 0 ? criterion.list() : Collections.EMPTY_LIST, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<Pay> getList(Long inId) {
        Criteria criteria = getSession().createCriteria(Pay.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("inId",inId));
        List<Pay> list=criteria.list();
        if(list!=null&&list.size()>0){
            return list;
        }
        return Collections.EMPTY_LIST;
    }
}
