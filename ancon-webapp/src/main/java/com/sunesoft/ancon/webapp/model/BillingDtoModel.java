package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/12/8.
 */
public class BillingDtoModel {

    private BigDecimal billingMoney;
    private Date billingDate;
    private String taxType;
    private String num;
    private String name;
    private String branchCompany;
    private String billingPerson;
    private String jiafangName;
    private String remark;



    public BigDecimal getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchCompany() {
        return branchCompany;
    }

    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    public String getBillingPerson() {
        return billingPerson;
    }

    public void setBillingPerson(String billingPerson) {
        this.billingPerson = billingPerson;
    }

    public String getJiafangName() {
        return jiafangName;
    }

    public void setJiafangName(String jiafangName) {
        this.jiafangName = jiafangName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
