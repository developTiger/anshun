package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.core.uAuth.domain.SysUserRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.ancon.core.uAuth.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysUserRepository")
public class SysUserRepositoryImpl extends GenericHibernateRepository<SysUser, Long> implements SysUserRepository {
}
