package com.sunesoft.ancon.core.uAuth.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouz on 2016/7/14.
 */
@Entity
@Table(name="ancon_sys_user_role")
public class SysUserAndRole extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="user_id",columnDefinition = "int(2) COMMENT '用户标识'")
    private SysUser sysUser;

    @ManyToOne
    @JoinColumn(name="role_id",columnDefinition = "int(2) COMMENT '角色标识'")
    private SysRole role;

    public SysUserAndRole() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
