package com.sunesoft.ancon.core.uAuth.domain;

import com.sunesoft.ancon.fr.BaseEntity;
import com.sunesoft.ancon.fr.utils.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Entity
@Table(name = "ancon_sys_user")
//@org.hibernate.annotations.Table(appliesTo = "ancon_sys_user",comment="表注释")
public class SysUser extends BaseEntity {

    /**
     * 系统用户状态
     */
    @Column(columnDefinition = "int(2) COMMENT '用户状态：0代表未禁用,1代表禁用'")
    private int status; // 0代表未禁用,1代表禁用
    /**
     * 系统用户等级
     */
    @Column(columnDefinition = "int(2) COMMENT '等级'")
    private int levels; // 等级

    @Column(name="user_name", nullable = false, columnDefinition="varchar(50) COMMENT '登录'")
    private String loginName; // 登录用户帐号

    @Column(name = "real_name",columnDefinition = "varchar(50) COMMENT '真实姓名'")
    private String name; // 真实姓名

    @Column(columnDefinition = "varchar(50) COMMENT '登录密码'")
    private String password; // 登录密码

    @Column(columnDefinition = "varchar(50) COMMENT '加密规则'")
    private String alt; //加密规则

    @Column(columnDefinition = "varchar(50) COMMENT '手机号'")
    private String mobile; // 手机

    @Column(columnDefinition = "varchar(50) COMMENT '邮箱'")
    private String email; // 邮箱

    @Column(columnDefinition = "varchar(50) COMMENT 'qq'")
    private String qq; // qq

    @Column(columnDefinition = "varchar(50) COMMENT '座机号'")
    private String phone; // 座机

    @Column(columnDefinition = "varchar(50) COMMENT '照片'")
    private String photo; // 照片

    @Column(columnDefinition = "varchar(50) COMMENT '简介'")
    private String brief; // 简介

    @Column(columnDefinition = "varchar(50) COMMENT '角色名称'")
    private String roleName;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time",columnDefinition = "DATETIME  COMMENT '账号最后登录时间'")
    private Date lastLoginTime; // 账号最后登录时间

    @Column(name = "login_count",columnDefinition = "int(2) COMMENT '账号登录次数'")
    private int loginCount; // 账号登录次数


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.PERSIST})
    @JoinColumn(name = "user_dept_id",columnDefinition = "bigint  COMMENT '部门标识'")
    private Deptment dept;

    public SysUser() {
        this.setLoginCount(0);
        this.setIsActive(true);
        this.setPassword("123456");
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public SysUser(String loginName, String password, String name, String email, String mobile) {
        if (!StringUtils.isNullOrWhiteSpace(loginName) && !StringUtils.isNullOrWhiteSpace(password)) {
            this.loginName = loginName;
            this.password = password;
            this.email = email;
            this.name = name;
            this.mobile = mobile;
        }
        this.setLoginCount(0);
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setStatus(0);

    }

    public boolean checkPassword(String password) {
        if (this.password.equals(password))
            return true;
        return false;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!StringUtils.isNullOrWhiteSpace(loginName)) {
            this.loginName = loginName;
        }
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!StringUtils.isNullOrWhiteSpace(password)) {
            this.password = password;
        }
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public Deptment getDept() {
        return dept;
    }

    public void setDept(Deptment dept) {
        this.dept = dept;
    }
}
