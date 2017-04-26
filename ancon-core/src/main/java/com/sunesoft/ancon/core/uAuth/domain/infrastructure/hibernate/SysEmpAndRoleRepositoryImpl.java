package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.core.uAuth.domain.SysUserAndRole;
import com.sunesoft.ancon.core.uAuth.domain.SysEmpAndRoleRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysEmpAndRoleRepository")
public class SysEmpAndRoleRepositoryImpl extends GenericHibernateRepository<SysUserAndRole,Long> implements SysEmpAndRoleRepository {


}
