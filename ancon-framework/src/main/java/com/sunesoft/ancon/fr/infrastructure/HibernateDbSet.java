package com.sunesoft.ancon.fr.infrastructure;

import com.sunesoft.ancon.fr.results.PagedResult;
import org.hibernate.Query;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基于 Hibernate 的数据实体操作集合。
 */
public class HibernateDbSet<E> {

    private Class clazz;
    private HibernateDbSupport dbSupport;

    public HibernateDbSet(HibernateDbSupport dbSupport, Class clazz) {
        this.dbSupport = dbSupport;
        this.clazz = clazz;
    }

    public Serializable add(E e) {
        return dbSupport.getSession().save(e);
    }

    public void delete(E e) {
        dbSupport.getSession().delete(e);
    }

    public boolean deleteById(Serializable id) {
        E e = get(id);
        if (e == null)
            return false;
        delete(e);
        return true;
    }

    public void update(E e) {
        dbSupport.getSession().update(e);
    }

    public E get(Serializable id) {
        return (E) dbSupport.getSession().get(clazz, id);
    }

    public List<E> getAll() {
        return (List<E>) dbSupport.getSession().createCriteria(clazz).list();
    }

    public E getUniqueByHql(String hql, Map<String, Object> params) {
        Query query = dbSupport.getSession().createQuery(hql);
        setParams(query, params);
        return (E) query.uniqueResult();
    }

    public long getCountByHql(String hql, Map<String, Object> params) {
        String countHql = hql.substring(0, hql.toLowerCase().indexOf("from "));
        if (null == countHql || countHql.trim().equals("")) {
            countHql = "select count(*) " + hql;
        } else {
            countHql = hql.replace(countHql, "select count(*) ");
        }
        if (countHql.toLowerCase().contains(" order ")) {
            countHql = countHql.substring(0, countHql.toLowerCase().indexOf("order"));
        }
        Query query = dbSupport.getSession().createQuery(countHql);
        setParams(query, params);
        return Long.valueOf(query.uniqueResult().toString());
    }

    public boolean isExistByHql(String hql, Map<String, Object> params) {
        return getCountByHql(hql, params) > 0;
    }

    public List<E> queryByHql(String hql, Map<String, Object> params) {
        Query query = dbSupport.getSession().createQuery(hql);
        setParams(query, params);
        List<E> results = query.list();
        return results;
    }

    public PagedResult<E> pagingByHql(int pageIndex, int pageSize, String hql, Map<String, Object> params) {
        long totalCount = getCountByHql(hql, params);
        Query query = dbSupport.getSession().createQuery(hql);
        setParams(query, params);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<E> results = query.list();
        return new PagedResult<E>( results,pageIndex, pageSize, (int)totalCount);
    }

    void setParams(Query query, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (param.getValue() instanceof Collection)
                    query.setParameterList(param.getKey(), (Collection) param.getValue());
                else
                    query.setParameter(param.getKey(), param.getValue());
            }
        }
    }
}
