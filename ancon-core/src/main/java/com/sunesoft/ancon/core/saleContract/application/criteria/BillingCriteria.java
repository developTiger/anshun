package com.sunesoft.ancon.core.saleContract.application.criteria;

import com.sunesoft.ancon.core.parameter.application.criteria.ParameterCriteria;
import com.sunesoft.ancon.fr.results.PagedCriteria;

import java.util.Date;

/**
 * Created by admin on 2016/11/24.
 */
public class BillingCriteria extends PagedCriteria {

    private Long companyId;

    private Date be_time;
    private Date end_time;
    private String taxType;
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

    public Date getBe_time() {
        return be_time;
    }

    public void setBe_time(Date be_time) {
        this.be_time = be_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }


}
