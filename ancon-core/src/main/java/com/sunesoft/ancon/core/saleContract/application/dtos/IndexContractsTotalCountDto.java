package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * 首页销售合同数据统计 匹配数据库字段
 * Created by admin on 2016/12/21.
 */
public class IndexContractsTotalCountDto {

    private BigDecimal sum_month_judge_money;//月份金额统计 今年
    private BigDecimal sum_year_judge_money;//年份金额统计 今年
    private BigDecimal sum_month_past_judge_money;
    private BigDecimal sum_year_past_judge_money;

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
}
