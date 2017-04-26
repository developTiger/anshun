package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于付款列表下载的模板
 * Created by xiazl on 2016/12/8.
 */
public class PayDtoModel {
    /**
     * 所属合同名称
     */
    private String inName;
    /**
     * 所属合同编号
     */
    private  String inNum;
    /**
     * 付款金额
     */
    private BigDecimal money;



    /**
     * 付款方式
     */
    private String payStyle;
//    /**
//     * 凭据
//     */
//    private String proof;
    /**
     * 乙方单位名称
     */
    private String partyB;
    /**
     * 付款时间
     */
    private String payTime;

    /**
     * 登记人
     */
    private String booker;
    /**
     * 备注
     */
    private String remark;

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

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
