package com.sunesoft.ancon.core.inContract.domain.infrastructuer.hibernate;

import com.sunesoft.ancon.core.inContract.application.criteria.InContractCriteria;
import com.sunesoft.ancon.core.inContract.domain.InContract;
import com.sunesoft.ancon.core.inContract.domain.InContractRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiazl on 2016/11/22.
 */
@Service("inContractRepository")
public class InContractRepositoryImpl extends GenericHibernateRepository<InContract, Long> implements InContractRepository {

    @Override
    public InContract getByNum(String num) {
        Criteria criteria = getSession().createCriteria(InContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("num", num));
        List<InContract> list = criteria.list();
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public List<InContract> getByName(String name) {
        Criteria criteria = getSession().createCriteria(InContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("name", name));
        return criteria.list();
    }

    @Override
    public PagedResult<InContract> paged(InContractCriteria criteria) {
        Criteria criterion = getSession().createCriteria(InContract.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            criterion.add(Restrictions.like("name", "%" + criteria.getName() + "%"));
        }
        if (criteria.getPartyBId()!=null&&criteria.getPartyBId()>0) {
            criterion.add(Restrictions.eq("partyBId", criteria.getPartyBId()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getOptionName())) {
            criterion.add(Restrictions.eq("optionName", criteria.getOptionName()));
        }
        if (criteria.getBeginTime() != null) {
            criterion.add(Restrictions.ge("billDate", criteria.getBeginTime()));
        }
        if (criteria.getEndTime() != null) {
            criterion.add(Restrictions.le("billDate", criteria.getEndTime()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.addOrder(Order.desc("billDate")).addOrder(Order.desc("createDateTime"));
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        return new PagedResult<InContract>(criterion.list() == null ? Collections.EMPTY_LIST : criterion.list(), criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<InContract> getAll() {
        Criteria criteria = getSession().createCriteria(InContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        return criteria.list();
    }
}
