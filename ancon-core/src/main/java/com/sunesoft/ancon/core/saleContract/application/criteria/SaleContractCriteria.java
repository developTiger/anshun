package com.sunesoft.ancon.core.saleContract.application.criteria;

import com.sunesoft.ancon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by admin on 2016/11/24.
 */
public class SaleContractCriteria extends PagedCriteria {

    private Long companyId;//分公司id

    private Date beginTime;
    private Date endTime;
    private String branchCompany;
    private String jiaFangName;
    private String contractType;
    private String projectMajor;
    private String contractStatus ;
    private Integer year;


    //排序
    private String bidNotice;
    private String constructLicense;
    private String finishCheck;
    private String projectSettlement;
    private String contractIsReturn;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractIsReturn() {
        return contractIsReturn;
    }

    public void setContractIsReturn(String contractIsReturn) {
        this.contractIsReturn = contractIsReturn;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getConstructLicense() {
        return constructLicense;
    }

    public void setConstructLicense(String constructLicense) {
        this.constructLicense = constructLicense;
    }

    public String getFinishCheck() {
        return finishCheck;
    }

    public void setFinishCheck(String finishCheck) {
        this.finishCheck = finishCheck;
    }

    public String getProjectSettlement() {
        return projectSettlement;
    }

    public void setProjectSettlement(String projectSettlement) {
        this.projectSettlement = projectSettlement;
    }

    public String getBidNotice() {
        return bidNotice;
    }

    public void setBidNotice(String bidNotice) {
        this.bidNotice = bidNotice;
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getProjectMajor() {
        return projectMajor;
    }

    public void setProjectMajor(String projectMajor) {
        this.projectMajor = projectMajor;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
