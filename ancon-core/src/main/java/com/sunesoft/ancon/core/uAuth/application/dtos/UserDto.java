package com.sunesoft.ancon.core.uAuth.application.dtos;


import com.sunesoft.ancon.core.uAuth.domain.Deptment;
import com.sunesoft.ancon.fr.utils.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by xiazl on 2016/5/25.
 */
public class UserDto {

    private Long id;//用户id

    private int operate;// 0 ：新增 1 禁用 2 启用

    private int status; // 0代表未禁用,1代表禁用

    private int levels; // 等级

    private String loginName; // 登录用户帐号

    private String name; // 真实姓名

    private String password; // 登录密码

    private String alt; // 加密规则

    private String mobile; // 手机

    private String email; // 邮箱

    private String qq; // qq

    private String phone; // 座机

    private String photo; // 照片

    private String brief; // 简介

    private Date createDateTime; // 创建时间

    private Date lastUpdateTime; // 最后修改时间

    private Date lastLoginTime; // 账号最后登录时间

    private int loginCount; // 账号登录次数


    private String roleId;

    private Long deptId;

    private String  deptName;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public UserDto() {

    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }


    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!StringUtils.isNullOrWhiteSpace(email))
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        if(!StringUtils.isNullOrWhiteSpace(loginName))
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!StringUtils.isNullOrWhiteSpace(name))
        this.name = name;
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(!StringUtils.isNullOrWhiteSpace(phone))
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
