package com.sunesoft.ancon.core.uAuth.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Entity
@Table(name = "ancon_sys_resource")
public class SysResource extends BaseEntity {


    public SysResource() {
        this.resourceType=1;
        this.target = "_self";
        setIsActive(true);
        this.setLastUpdateTime(new Date());
    }

    /**
     *  1: menu   2:form  3:filepath
     */
    @Column(name = "resource_type",columnDefinition = "int(2) COMMENT '菜单类型：1:menu,2,'")
    private Integer resourceType;


    /**
     *  //menu :菜单 edit 编辑
     */
    @Column(name = "resource_operate",columnDefinition = "varchar(50) COMMENT '类型：menu:菜单,edit:编辑'")
    private String resource_operate; //menu :菜单  edit 编辑

    @Column(name = "operateFlag",columnDefinition = "bit COMMENT '是否可编辑：1 可编辑 0 不可编辑'")
    private Boolean operateFlag;//true 可编辑 false 不可编辑

    @Column(name = "res_name",columnDefinition = "varchar(50) COMMENT '菜单名称'")
    private String name;

    @Column(columnDefinition = "varchar(50) COMMENT '菜单url地址'")
    private String url; // 菜单url地址
    @Column(columnDefinition = "varchar(50) COMMENT '菜单打开方式：_self:当前窗口,_blank:新窗口'")
    private String target;//菜单打开方式 _self  _blank  view_window


    @Column(name = "id_code",columnDefinition = "varchar(50) COMMENT '标识码'")
    private String idCode;

    @Column(columnDefinition = "varchar(50) COMMENT '是否是根目录'")
    private Boolean isRoot;


    @ManyToOne
    @JoinColumn(name = "parent_res_id",columnDefinition = "int(2) COMMENT '父菜单'")
    private SysResource parentResource; // 父菜单


    @Column(name = "icon_name",columnDefinition = "int(2) COMMENT '图标名称'")
    private String iconName; //图标名称


    @Column(columnDefinition = "int(2) COMMENT '排序'")
    private Integer sort; // 排序


    /**
     * 子菜单
     */
    @OneToMany
    @JoinColumn(name = "parent_res_id")
    private List<SysResource> childResource; // 子菜单

    public List<SysResource> getChildResource() {
        return childResource;
    }


    public void setChildResource(List<SysResource> childResource) {
        this.childResource = childResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public SysResource getParentResource() {
        return parentResource;
    }

    public void setParentResource(SysResource parentResource) {
        this.parentResource = parentResource;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResource_operate() {
        return resource_operate;
    }

    public void setResource_operate(String resource_operate) {
        this.resource_operate = resource_operate;
    }

    public Boolean getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(Boolean operateFlag) {
        this.operateFlag = operateFlag;
    }
}

