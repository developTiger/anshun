package com.sunesoft.ancon.core.uAuth.domain.infrastructure.hibernate;

import com.sunesoft.ancon.core.uAuth.domain.SysResource;
import com.sunesoft.ancon.core.uAuth.domain.SysResourceRepository;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("sysResourceRepository")
public class SysResourceRepositoryImpl extends GenericHibernateRepository<SysResource,Long> implements SysResourceRepository {

}
