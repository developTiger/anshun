package com.sunesoft.ancon.core.inContract.domain.infrastructuer.hibernate;

import com.sunesoft.ancon.core.inContract.application.criteria.InvoiceCriteria;
import com.sunesoft.ancon.core.inContract.domain.Invoice;
import com.sunesoft.ancon.core.inContract.domain.InvoiceRepository;
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
 * Created by xiazl on 2016/11/23.
 */
@Service("invoiceRepository")
public class InvoiceRepositoryImpl extends GenericHibernateRepository<Invoice, Long> implements InvoiceRepository {
//    @Override
//    public Invoice getByNum(String num) {
//        Criteria criteria = getSession().createCriteria(Invoice.class);
//        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("num", num));
//        List<Invoice> list = criteria.list();
//        if (list != null && list.size() > 0) return list.get(0);
//        return null;
//    }

    @Override
    public List<Invoice> getList(Long inId) {
        Criteria criteria = getSession().createCriteria(Invoice.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("inId", inId));
        List<Invoice> list = criteria.list();
        if (list != null && list.size() > 0) return list;
        return Collections.EMPTY_LIST;
    }

    @Override
    public PagedResult<Invoice> paged(InvoiceCriteria criteria) {
        Criteria criterion = getSession().createCriteria(Invoice.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (criteria.getId() != null && criteria.getId() > 0) {
            criterion.add(Restrictions.eq("inId", criteria.getId()));
        }
        if (criteria.getType() != null) {
            criterion.add(Restrictions.eq("type", criteria.getType()));
        }
        if (criteria.getBeginTime() != null) {
            criterion.add(Restrictions.ge("invoiceDate", criteria.getBeginTime()));
        }
        if (criteria.getEndTime() != null) {
            criterion.add(Restrictions.le("invoiceDate", criteria.getEndTime()));
        }
        if (criteria.getPartyBId()!=null&&criteria.getPartyBId()>0) {
            criterion.add(Restrictions.eq("partyBId", criteria.getPartyBId()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getContract())) {
            criterion.add(Restrictions.like("inName", "%"+criteria.getContract()+"%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.addOrder(Order.desc("invoiceDate")).addOrder(Order.desc("createDateTime"));
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());

        return new PagedResult<Invoice>(criterion.list() == null ? Collections.EMPTY_LIST : criterion.list(), criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }
}
