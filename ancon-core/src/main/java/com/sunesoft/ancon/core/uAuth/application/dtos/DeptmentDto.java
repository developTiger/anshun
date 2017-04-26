package com.sunesoft.ancon.core.uAuth.application.dtos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiazl on 2016/6/22.
 */
public class DeptmentDto {

    private Long id;

    /**
     * 销售金额 按公司计算
     */
    private BigDecimal saleContractMoney;


    /**
     * 部门分管领导 id
     */
    private Long chargeLeaderId;

    /**
     * 部门分管领导 name
     */
    private String chargeLeaderName;

    /**
     * false代表删除， true代表未删除
     */
    private Boolean isActive;


    /**
     * 创建时间
     */
    private Date createDateTime;

    /**
     *  最后修改时间
     */
    private Date lastUpdateTime;

    /**
     * 部门或者分公司名称
     */
    private String deptName;

//    /**
//     * 部门简称
//     */
//    private String shortName;

    /**
     * 部门编号
     */

    private String deptNo;

    /**
     * 部门等级
     * 0代表院级，1代表科级
     */

    private Integer deptType;



    /**
     * 直属上级部门的id
     */
    private Long parentDeptId;

    /**
     * 直属上级部门的名称
     */
    private String parentDeptName;

    private Integer type; // 1:部门  2:独立分公司 3: 非独立分公司

    private Integer nature;//1：独立 2：非独立

    /**
     * 0代表未禁用，1代表禁用
     */
    private Integer status;

    private String brief;

    public Integer getType() {
        return type;
    }

    public BigDecimal getSaleContractMoney() {
        return saleContractMoney;
    }

    public void setSaleContractMoney(BigDecimal saleContractMoney) {
        this.saleContractMoney = saleContractMoney;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getChargeLeaderName() {
        return chargeLeaderName;
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName;
    }

    public Long getChargeLeaderId() {
        return chargeLeaderId;
    }

    public void setChargeLeaderId(Long chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId;
    }

    public Long getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Long parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getParentDeptName() {
        return parentDeptName;
    }

    public void setParentDeptName(String parentDeptName) {
        this.parentDeptName = parentDeptName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }
}
