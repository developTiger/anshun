package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * 销售合同统计图 年度签单额统计 匹配数据库字段
 * Created by admin on 2016/12/22.
 */
public class QuerySaleContractsBy_contractTypeDto {

    private String month;
    private BigDecimal sum_judge_money;
    private String contract_type;

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getSum_judge_money() {
        return sum_judge_money;
    }

    public void setSum_judge_money(BigDecimal sum_judge_money) {
        this.sum_judge_money = sum_judge_money;
    }
}
