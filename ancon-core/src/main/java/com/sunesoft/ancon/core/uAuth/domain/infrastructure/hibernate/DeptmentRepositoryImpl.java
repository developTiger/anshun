package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.core.uAuth.domain.Deptment;
import com.sunesoft.ancon.core.uAuth.domain.DeptmentRepository;
import com.sunesoft.ancon.core.uAuth.domain.SysRole;
import com.sunesoft.ancon.core.uAuth.domain.SysRoleRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("deptmentRepository")
public class DeptmentRepositoryImpl extends GenericHibernateRepository<Deptment, Long> implements DeptmentRepository {
    @Override
    public List<Deptment> getByIds(List<Long> ids) {
        return null;
    }
}
