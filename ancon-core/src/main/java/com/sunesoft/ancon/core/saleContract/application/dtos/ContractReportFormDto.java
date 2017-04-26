package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * 销售合同报表 匹配数据库字段
 * Created by admin on 2016/12/21.
 */
public class ContractReportFormDto {

    private String branch_company;
    private BigDecimal sum_year_judge_money;
    private BigDecimal sum_billing_money;
    private BigDecimal sum_gathering_money;
    private BigDecimal sum_output_value;

    public String getBranch_company() {
        return branch_company;
    }

    public void setBranch_company(String branch_company) {
        this.branch_company = branch_company;
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

    public BigDecimal getSum_output_value() {
        return sum_output_value;
    }

    public void setSum_output_value(BigDecimal sum_output_value) {
        this.sum_output_value = sum_output_value;
    }
}
