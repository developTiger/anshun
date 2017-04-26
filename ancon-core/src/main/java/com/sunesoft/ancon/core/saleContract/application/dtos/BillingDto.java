package com.sunesoft.ancon.core.saleContract.application.dtos;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/24.
 */
public class BillingDto {


    private Long id;


    /**
     * 税率
     */
    private Float taxRate;

    /**
     * 税额
     */
    private BigDecimal taxMoney;

    /**
     * 价税合计(计算金额以价税合计为主)
     */
    private BigDecimal priceTaxCountMoney;
//    -----------------------------------------------------------

    /**
     * 开票总金额 合计
     */
    private BigDecimal billTotalMoney;

    /**
     * 开票金额
     */
    private BigDecimal billingMoney;

    /**
     * 开票日期
     */
    private Date billingDate;

    /**
     * 计税方式
     */
    private String taxType;

    /**
     * 甲方名称
     */
    private String jiafangName;

    /**
     * 开票人
     */
    private String billingPerson;

    /**
     * 最近修改人
     */
    private String lastEditPerson;

    /**
     * 备注
     */
    private String remark;

    private SaleContractDto saleContractDto;


    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(BigDecimal taxMoney) {
        this.taxMoney = taxMoney;
    }

    public BigDecimal getPriceTaxCountMoney() {
        return priceTaxCountMoney;
    }

    public void setPriceTaxCountMoney(BigDecimal priceTaxCountMoney) {
        this.priceTaxCountMoney = priceTaxCountMoney;
    }

    public BigDecimal getBillTotalMoney() {
        return billTotalMoney;
    }

    public void setBillTotalMoney(BigDecimal billTotalMoney) {
        this.billTotalMoney = billTotalMoney;
    }

    public SaleContractDto getSaleContractDto() {
        return saleContractDto;
    }

    public void setSaleContractDto(SaleContractDto saleContractDto) {
        this.saleContractDto = saleContractDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getJiafangName() {
        return jiafangName;
    }

    public void setJiafangName(String jiafangName) {
        this.jiafangName = jiafangName;
    }

    public String getBillingPerson() {
        return billingPerson;
    }

    public void setBillingPerson(String billingPerson) {
        this.billingPerson = billingPerson;
    }

    public String getLastEditPerson() {
        return lastEditPerson;
    }

    public void setLastEditPerson(String lastEditPerson) {
        this.lastEditPerson = lastEditPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Created by admin on 2016/12/21.
     */
    public static class DynamicTotalReportDtoModel {



    }
}
