package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;

/**
 * Created by xiazl on 2016/12/8.
 */
public class InvoiceDtomodel {
    /**
     * 合同名称
     */
    private String inName;
    /**
     * 所属合同编号
     */
    private String inNum;
    /**
     * 发票金额
     */
    private BigDecimal money;
//    /**
//     * 发票税点
//     */
//    private BigDecimal taxPoint;
    /**
     * 发票类型
     */
    private String billType;


    /**
     * 乙方名称
     */
    private String partyB;
    /**
     * 开票日期
     */
    private String billDate;
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getInNum() {
        return inNum;
    }

    public void setInNum(String inNum) {
        this.inNum = inNum;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
