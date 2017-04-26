package com.sunesoft.ancon.core.uAuth.application.dtos;

import java.util.List;

/**
 * Created by zhouz on 2016/7/14.
 */
public class AuthResDto {

    private Long roleId;
    private Long id;


    private String resName; // 菜单名称

    private String url; // 菜单url地址

    private Integer resType;

    private String idCode; // 标识码
    private String target;//打开方式  _self  _blank  view_window


    private Long  parentId; // 父菜单Id

    private String iconName; //图标名称

    private String resource_operate;

    private Boolean operateFlag;

    private Integer sort; // 排序

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
