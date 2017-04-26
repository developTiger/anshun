package com.sunesoft.ancon.core.inContract.application;

import java.math.BigDecimal;

/**
 * 统计的数据与名称
 * Created by xiazl on 2016/12/6.
 */
public class ChartCount {

    private BigDecimal totalMoney;

    private String partyB;


    public ChartCount(String partyB, BigDecimal totalMoney) {
        this.partyB = partyB;
        this.totalMoney = totalMoney;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
