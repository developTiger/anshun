package com.sunesoft.ancon.core.saleContract.application.criteria;

import com.sunesoft.ancon.fr.results.PagedCriteria;

import java.io.PipedReader;
import java.util.Date;

/**
 * Created by admin on 2016/11/25.
 */
public class GatheringCriteria extends PagedCriteria {

    private Long companyId;

    private Date beginTime;
    private Date endTime;
    private String name;
    private String branchCompany;
    private String jiaFangName;
    private Long saleContractId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getSaleContractId() {
        return saleContractId;
    }

    public void setSaleContractId(Long saleContractId) {
        this.saleContractId = saleContractId;
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

    public String getJiaFangName() {
        return jiaFangName;
    }

    public void setJiaFangName(String jiaFangName) {
        this.jiaFangName = jiaFangName;
    }
}
