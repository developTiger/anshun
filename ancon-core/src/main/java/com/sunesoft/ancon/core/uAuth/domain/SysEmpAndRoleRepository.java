package com.sunesoft.ancon.core.uAuth.domain;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface SysEmpAndRoleRepository {

    Long save(SysUserAndRole emp);

    void delete(Long empId);

    SysUserAndRole get(Long empId);

}
