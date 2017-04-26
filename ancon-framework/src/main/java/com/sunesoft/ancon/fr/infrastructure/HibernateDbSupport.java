package com.sunesoft.ancon.fr.infrastructure;

import com.sunesoft.ancon.fr.results.PagedResult;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基于 Hibernate 的基础数据库操作单元。
 */
@Repository("HibernateDbWork")
public class HibernateDbSupport {

    @Autowired
    protected SessionFactory sessionFactory;

    //用户维护Session的线程变量，避免并发访问时的数据错误。
    public static final ThreadLocal<Session> session = new ThreadLocal();

    public void openSession(boolean isOpenTransaction) {
        Session s = session.get();
        if (s == null) {
            session.set(sessionFactory.openSession());
            if (isOpenTransaction && !session.get().getTransaction().isActive()) {
                session.get().getTransaction().begin();
            }
        }
    }

    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery query = getSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
        return query.executeUpdate();
    }

    public <E> E queryForObject(Class<E> clazz, String sql, Map<String, Object> params) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        setParams(sqlQuery, params);
        addScalar(sqlQuery, clazz);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        return (E) sqlQuery.uniqueResult();
    }

    public <E> List<E> queryForObjects(Class<E> clazz, String sql, Map<String, Object> params) {
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        setParams(sqlQuery, params);
        addScalar(sqlQuery, clazz);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        return sqlQuery.list();
    }

    public <E> List<E> queryForObjects(Class<E> clazz,SQLQuery sqlQuery) {

        addScalar(sqlQuery, clazz);
        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        return sqlQuery.list();
    }


    public long getCountBySql(String sql, Map<String, Object> params) {
        String countSql = sql.substring(0, sql.toLowerCase().indexOf("from "));
        if (null == countSql || countSql.trim().equals("")) {
            countSql = "select count(*) " + sql;
        } else {
            countSql = sql.replace(countSql, "select count(*) ");
        }
        if (countSql.toLowerCase().contains(" order ")) {
            countSql = countSql.substring(0, countSql.toLowerCase().indexOf("order"));
        }
        SQLQuery query = getSession().createSQLQuery(countSql);
        setParams(query, params);
        return Long.valueOf(query.uniqueResult().toString());
    }

    public <E> PagedResult<E> pagingBySql(int pageIndex, int pageSize, Class<E> clazz, String sql, Map<String, Object> params) {
        long totalCount = getCountBySql(sql, params);
        SQLQuery query = getSession().createSQLQuery(sql);
        setParams(query, params);
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<E> results = query.list();
        return new PagedResult<E>( results,pageIndex, pageSize, (int)totalCount);
    }

    public void commit() {
        try {
            if (getSession().getTransaction().isActive())
                getSession().getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeSession();
        }
    }


    public Session getSession() {
        return  sessionFactory.getCurrentSession();
    }

    public void closeSession() {
        if (session != null)
            session.get().close();
        session.set(null);
    }

    public <E> void addScalar(SQLQuery sqlQuery, Class<E> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if ((field.getType() == long.class) || (field.getType() == Long.class)) {
                sqlQuery.addScalar(field.getName(), new LongType());
            } else if ((field.getType() == int.class) || (field.getType() == Integer.class)) {
                sqlQuery.addScalar(field.getName(), new IntegerType());
            } else if ((field.getType() == char.class) || (field.getType() == Character.class)) {
                sqlQuery.addScalar(field.getName(), new CharacterType());
            } else if ((field.getType() == short.class) || (field.getType() == Short.class)) {
                sqlQuery.addScalar(field.getName(), new ShortType());
            } else if ((field.getType() == double.class) || (field.getType() == Double.class)) {
                sqlQuery.addScalar(field.getName(), new DoubleType());
            } else if ((field.getType() == float.class) || (field.getType() == Float.class)) {
                sqlQuery.addScalar(field.getName(), new FloatType());
            } else if ((field.getType() == boolean.class) || (field.getType() == Boolean.class)) {
                sqlQuery.addScalar(field.getName(), new BooleanType());
            } else if (field.getType() == String.class) {
                sqlQuery.addScalar(field.getName(), new StringType());
            } else if (field.getType() == Date.class) {
                sqlQuery.addScalar(field.getName(), new TimestampType());
            }else if (field.getType() == BigDecimal.class) {
                sqlQuery.addScalar(field.getName(), new BigDecimalType());
            }
        }
    }

    private void setParams(SQLQuery query, Map<String, Object> params) {
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
