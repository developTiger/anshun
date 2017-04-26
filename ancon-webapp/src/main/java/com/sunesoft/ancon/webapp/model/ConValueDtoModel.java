package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/12/13.
 */
public class ConValueDtoModel {

    private Date beginTime;
    private Date endTime;
    private String num;
    private String name;
    private String branchCompany;
    private BigDecimal outputValue;
    private Date wirteTime;
    private String wirtePerson;
    private String jiafangName;
    private String remark;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public BigDecimal getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigDecimal outputValue) {
        this.outputValue = outputValue;
    }

    public Date getWirteTime() {
        return wirteTime;
    }

    public void setWirteTime(Date wirteTime) {
        this.wirteTime = wirteTime;
    }

    public String getWirtePerson() {
        return wirtePerson;
    }

    public void setWirtePerson(String wirtePerson) {
        this.wirtePerson = wirtePerson;
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
