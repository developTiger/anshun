package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/21.
 */
public class QuerySaleContractsBy_companyDto {

    private String branch_company;
    private BigDecimal judge_money;

    public String getBranch_company() {
        return branch_company;
    }

    public void setBranch_company(String branch_company) {
        this.branch_company = branch_company;
    }

    public BigDecimal getJudge_money() {
        return judge_money;
    }

    public void setJudge_money(BigDecimal judge_money) {
        this.judge_money = judge_money;
    }
}
