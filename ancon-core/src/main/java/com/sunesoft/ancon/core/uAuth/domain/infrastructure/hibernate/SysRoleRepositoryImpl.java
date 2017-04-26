package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.core.uAuth.domain.SysRole;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.core.uAuth.domain.SysRoleRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysRoleRepository")
public class SysRoleRepositoryImpl extends GenericHibernateRepository<SysRole,Long> implements SysRoleRepository {

    @Override
    public List<SysRole> getAllRoleName() {
        String hql=" from SysRole ";
        Query query=getSession().createQuery(hql);
        return query.list();
    }
}
