package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/13.
 */
public class TotalReportFormDtoModel {

    private String branchCompany;
    private BigDecimal monthMoney;//本月累计销售合同额
    private String monthRate;//月增长幅度
    private BigDecimal yearMoney;//
    private String yearRate;

    private BigDecimal billingMoney;//开票统计
    private String billingRate;//开票率
    private BigDecimal gatheringMoney;
    private String gatheringRate;
    private BigDecimal conValueMone;
    private String conValueRate;

    public String getBranchCompany() {
        return branchCompany;
    }

    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    public BigDecimal getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(BigDecimal monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public BigDecimal getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(BigDecimal yearMoney) {
        this.yearMoney = yearMoney;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public BigDecimal getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public String getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(String billingRate) {
        this.billingRate = billingRate;
    }

    public BigDecimal getGatheringMoney() {
        return gatheringMoney;
    }

    public void setGatheringMoney(BigDecimal gatheringMoney) {
        this.gatheringMoney = gatheringMoney;
    }

    public String getGatheringRate() {
        return gatheringRate;
    }

    public void setGatheringRate(String gatheringRate) {
        this.gatheringRate = gatheringRate;
    }

    public BigDecimal getConValueMone() {
        return conValueMone;
    }

    public void setConValueMone(BigDecimal conValueMone) {
        this.conValueMone = conValueMone;
    }

    public String getConValueRate() {
        return conValueRate;
    }

    public void setConValueRate(String conValueRate) {
        this.conValueRate = conValueRate;
    }
}
