package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * 销售合同 动态总报表 匹配数据库字段
 * Created by admin on 2016/12/21.
 */
public class DynamicTotalReportFormDto {

    private String branch_company;
    private BigDecimal sum_month_judge_money;
    private BigDecimal sum_year_judge_money;
    private BigDecimal sum_billing_money;
    private BigDecimal sum_gathering_money;
    private BigDecimal sum_conValue_money;

    private String past_branch_company;
    private BigDecimal sum_month_past_judge_money;
    private BigDecimal sum_year_past_judge_money;
    private BigDecimal total_billing_money;
    private BigDecimal total_gathering_money;
    private BigDecimal total_conValue_money;

    public String getBranch_company() {
        return branch_company;
    }

    public void setBranch_company(String branch_company) {
        this.branch_company = branch_company;
    }

    public BigDecimal getSum_month_judge_money() {
        return sum_month_judge_money;
    }

    public void setSum_month_judge_money(BigDecimal sum_month_judge_money) {
        this.sum_month_judge_money = sum_month_judge_money;
    }

    public BigDecimal getSum_year_judge_money() {
        return sum_year_judge_money;
    }

    public void setSum_year_judge_money(BigDecimal sum_year_judge_money) {
        this.sum_year_judge_money = sum_year_judge_money;
    }

    public BigDecimal getSum_billing_money() {
        return sum_billing_money;
    }

    public void setSum_billing_money(BigDecimal sum_billing_money) {
        this.sum_billing_money = sum_billing_money;
    }

    public BigDecimal getSum_gathering_money() {
        return sum_gathering_money;
    }

    public void setSum_gathering_money(BigDecimal sum_gathering_money) {
        this.sum_gathering_money = sum_gathering_money;
    }

    public BigDecimal getSum_conValue_money() {
        return sum_conValue_money;
    }

    public void setSum_conValue_money(BigDecimal sum_conValue_money) {
        this.sum_conValue_money = sum_conValue_money;
    }

    public String getPast_branch_company() {
        return past_branch_company;
    }

    public void setPast_branch_company(String past_branch_company) {
        this.past_branch_company = past_branch_company;
    }

    public BigDecimal getSum_month_past_judge_money() {
        return sum_month_past_judge_money;
    }

    public void setSum_month_past_judge_money(BigDecimal sum_month_past_judge_money) {
        this.sum_month_past_judge_money = sum_month_past_judge_money;
    }

    public BigDecimal getSum_year_past_judge_money() {
        return sum_year_past_judge_money;
    }

    public void setSum_year_past_judge_money(BigDecimal sum_year_past_judge_money) {
        this.sum_year_past_judge_money = sum_year_past_judge_money;
    }

    public BigDecimal getTotal_billing_money() {
        return total_billing_money;
    }

    public void setTotal_billing_money(BigDecimal total_billing_money) {
        this.total_billing_money = total_billing_money;
    }

    public BigDecimal getTotal_gathering_money() {
        return total_gathering_money;
    }

    public void setTotal_gathering_money(BigDecimal total_gathering_money) {
        this.total_gathering_money = total_gathering_money;
    }

    public BigDecimal getTotal_conValue_money() {
        return total_conValue_money;
    }

    public void setTotal_conValue_money(BigDecimal total_conValue_money) {
        this.total_conValue_money = total_conValue_money;
    }
}
