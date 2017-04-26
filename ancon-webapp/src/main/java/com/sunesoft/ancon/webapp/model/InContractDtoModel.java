package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;

/**
 * 用于进项合同excel导出
 * Created by xiazl on 2016/12/8.
 */
public class InContractDtoModel {
    /**
     * 合同名称
     */
    private String name;
    /**
     * 合同编号
     */
    private String num;


    /**
     * 合同金额
     */
    private BigDecimal money;
    /**
     * 已付款
     */
    private BigDecimal payMoney;
    /**
     * 已开发票
     */
    private BigDecimal billMoney;
    /**
     * 乙方
     */
    private String partyB;
    /**
     * 合同签订时间
     */
    private String billDate;
    /**
     * 合同到期时间
     */
    private String endDate;
    /**
     * 剩余天数
     */
    private String newDays;
    /**
     * 登记人
     */
    private String booker;
    /**
     * 备注
     */
    private String remark;

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewDays() {
        return newDays;
    }

    public void setNewDays(String newDays) {
        this.newDays = newDays;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
