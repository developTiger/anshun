package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/21.
 */
public class QuerySaleContractsBy_project_majorDto {

    private String project_major;
    private BigDecimal judge_money;

    public String getProject_major() {
        return project_major;
    }

    public void setProject_major(String project_major) {
        this.project_major = project_major;
    }

    public BigDecimal getJudge_money() {
        return judge_money;
    }

    public void setJudge_money(BigDecimal judge_money) {
        this.judge_money = judge_money;
    }
}
