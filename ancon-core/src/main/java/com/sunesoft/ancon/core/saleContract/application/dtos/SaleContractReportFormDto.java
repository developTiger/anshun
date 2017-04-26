package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/9.
 */
public class SaleContractReportFormDto {

    private Long branchCompanyId;
    private String branchCompanyName;
    private BigDecimal saleContractMoney;//合同金额 累计
    private BigDecimal billingMoney;//开票金额 累计
    private BigDecimal gatheringMoney;//收款金额 累计
    private BigDecimal conValueMoney;//施工产值金额 累计
    private BigDecimal judgeMoney;//定审金额 累计

    private BigDecimal thisMonthMoney;//本月累计销售合同额
    private String monthIncreaseRange;//当月同期增长幅度
    private BigDecimal thisYearMoney;//今年累计销售合同额
    private String yearIncreaseRange;//年度同期增长幅度

    /**
     * 开票率
     */
    private String billingRate;

    /**
     * 收款率
     */
    private String gatheringRate;

    /**
     * 施工产值率
     */
    private String conValueRate;


    public Long getBranchCompanyId() {
        return branchCompanyId;
    }

    public BigDecimal getJudgeMoney() {
        return judgeMoney;
    }

    public void setJudgeMoney(BigDecimal judgeMoney) {
        this.judgeMoney = judgeMoney;
    }

    public BigDecimal getThisMonthMoney() {
        return thisMonthMoney;
    }

    public void setThisMonthMoney(BigDecimal thisMonthMoney) {
        this.thisMonthMoney = thisMonthMoney;
    }

    public String getMonthIncreaseRange() {
        return monthIncreaseRange;
    }

    public void setMonthIncreaseRange(String monthIncreaseRange) {
        this.monthIncreaseRange = monthIncreaseRange;
    }

    public BigDecimal getThisYearMoney() {
        return thisYearMoney;
    }

    public void setThisYearMoney(BigDecimal thisYearMoney) {
        this.thisYearMoney = thisYearMoney;
    }

    public String getYearIncreaseRange() {
        return yearIncreaseRange;
    }

    public void setYearIncreaseRange(String yearIncreaseRange) {
        this.yearIncreaseRange = yearIncreaseRange;
    }

    public String getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(String billingRate) {
        this.billingRate = billingRate;
    }

    public String getGatheringRate() {
        return gatheringRate;
    }

    public void setGatheringRate(String gatheringRate) {
        this.gatheringRate = gatheringRate;
    }

    public String getConValueRate() {
        return conValueRate;
    }

    public void setConValueRate(String conValueRate) {
        this.conValueRate = conValueRate;
    }

    public void setBranchCompanyId(Long branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public BigDecimal getSaleContractMoney() {
        return saleContractMoney;
    }

    public void setSaleContractMoney(BigDecimal saleContractMoney) {
        this.saleContractMoney = saleContractMoney;
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
