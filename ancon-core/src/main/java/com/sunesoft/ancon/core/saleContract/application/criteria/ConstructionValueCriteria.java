package com.sunesoft.ancon.core.saleContract.application.criteria;

import com.sunesoft.ancon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by admin on 2016/11/25.
 */
public class ConstructionValueCriteria extends PagedCriteria {

    private Long compnayId;

    private Date beginTime;
    private Date endTime;
    private String name;
    private String branchCompany;
    private String jiafangName;
    private Long saleContractId;

    public Long getCompnayId() {
        return compnayId;
    }

    public void setCompnayId(Long compnayId) {
        this.compnayId = compnayId;
    }

    public Long getSaleContractId() {
        return saleContractId;
    }

    public void setSaleContractId(Long saleContractId) {
        this.saleContractId = saleContractId;
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

    public String getJiafangName() {
        return jiafangName;
    }

    public void setJiafangName(String jiafangName) {
        this.jiafangName = jiafangName;
    }

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
}
