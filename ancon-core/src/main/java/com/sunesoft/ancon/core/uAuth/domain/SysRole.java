package com.sunesoft.ancon.core.uAuth.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Entity
@Table(name = "ancon_sys_role")
public class SysRole extends BaseEntity {


    @Column(name = "role_name",columnDefinition = "varchar(50) COMMENT '系统角色名称'")
    private String name; // 系统角色名称


    @Column(name = "id_code",columnDefinition = "varchar(50) COMMENT '标识码'")
    private String idCode; // 标识码


    @Column(columnDefinition = "varchar(50) COMMENT '描述'")
    private String description; // 描述
    /**
     * 排序
     */
    @Column(columnDefinition = "int(2) COMMENT '排序'")
    private double sort; // 排序

    @ManyToMany
    @JoinTable(name = "ancon_role_pmgroup", inverseJoinColumns = @JoinColumn(name = "sys_permitgroup_id"), joinColumns = @JoinColumn(name = "user_role_id"))
    private List<SysPermissionGroup> permissonGroupList; // 角色下的权限组

    public List<SysPermissionGroup> getPermissonGroupList() {
        return permissonGroupList;
    }

    public void setPermissonGroupList(List<SysPermissionGroup> permissonGroupList) {
        this.permissonGroupList = permissonGroupList;
    }

    public SysRole() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getSort() {
        return sort;
    }

    public void setSort(double sort) {
        this.sort = sort;
    }

}
