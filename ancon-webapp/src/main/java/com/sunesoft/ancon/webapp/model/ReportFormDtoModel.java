package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/13.
 */
public class ReportFormDtoModel {

    private String branchCompany;
    private BigDecimal contractMoney;
    private BigDecimal billingMoney;
    private BigDecimal gatheringMoney;
    private BigDecimal conValueMoney;

    public String getBranchCompany() {
        return branchCompany;
    }

    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public BigDecimal getGatheringMoney() {
        return gatheringMoney;
    }

    public void setGatheringMoney(BigDecimal gatheringMoney) {
        this.gatheringMoney = gatheringMoney;
    }

    public BigDecimal getConValueMoney() {
        return conValueMoney;
    }

    public void setConValueMoney(BigDecimal conValueMoney) {
        this.conValueMoney = conValueMoney;
    }
}
