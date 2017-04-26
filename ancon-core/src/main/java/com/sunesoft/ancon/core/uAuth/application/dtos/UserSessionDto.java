package com.sunesoft.ancon.core.uAuth.application.dtos;

/**
 * Created by zhouzh on 2016/11/16.
 */
public class UserSessionDto {

    private Long id;

    private String loginName; // 登录用户帐号

    private String name; // 真实姓名


    private Long roleId;

    private Long CompanyId;//机构ID
    private String CompanyName;//机构名称
    private Integer type;//机构类型
    private String deptNo;//机构编号

//
//    private Long corpId;
//
//
//    private String corpName;


    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        CompanyId = companyId;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
