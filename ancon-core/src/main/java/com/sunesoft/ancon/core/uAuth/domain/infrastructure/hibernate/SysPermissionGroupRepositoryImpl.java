package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.core.uAuth.domain.SysPermissionGroupRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.core.uAuth.domain.SysPermissionGroup;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysPermissionGroupRepository")
public class SysPermissionGroupRepositoryImpl extends GenericHibernateRepository<SysPermissionGroup,Long> implements SysPermissionGroupRepository {

    @Override
    public List<SysPermissionGroup> getAllName() {
        String hql=" from SysPermissionGroup";
        Query query=getSession().createQuery(hql);
        return query.list();
    }
}
