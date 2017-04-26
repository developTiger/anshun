package com.sunesoft.ancon.core.saleContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import javax.xml.soap.Text;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 开票管理
 * Created by admin on 2016/11/24.
 */
@Entity
@Table(name = "ancon_sys_billing")
public class  Billing extends BaseEntity {

    public Billing(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    //新增
    /**
     * 税率
     */
    @Column(name = "tax_rate")
    private Float taxRate;

    /**
     * 税额
     */
    @Column(name = "tax_money",columnDefinition = "decimal(19,4) COMMENT '税额'")
    private BigDecimal taxMoney;

    /**
     * 价税合计(计算金额以价税合计为主)
     */
    @Column(name = "price_tax_count_money",columnDefinition = "decimal(19,4) COMMENT '价税合计'")
    private BigDecimal priceTaxCountMoney;

//    ----------------------------------------------------------------------------------------------------------

    /**
     * 开票总金额 合计
     */
    @Column(name = "bill_total_money",columnDefinition = "decimal(19,4) COMMENT '开票总金额统计'")
    private BigDecimal billTotalMoney;

    /**
     * 金额(原开票金额)
     */
    @Column(name = "billing_money",columnDefinition = "decimal(19,4) COMMENT '开票金额'")
    private BigDecimal billingMoney;

    /**
     * 开票日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "billing_date",columnDefinition = "DATETIME  COMMENT '开票日期'")
    private Date billingDate;

    /**
     * 计税方式
     */
    @Column(name = "tax_type",columnDefinition = "varchar(50) COMMENT '计税方式'")
    private String taxType;

    /**
     * 甲方名称
     */
    @Column(name = "jia_fang_name",columnDefinition = "varchar(200) COMMENT '甲方名称'")
    private String jiafangName;

    /**
     * 开票人
     */
    @Column(name = "billing_person",columnDefinition = "varchar(50) COMMENT '开票人'")
    private String billingPerson;

    /**
     * 最近修改人
     */
    @Column(name = "last_edit_person",columnDefinition = "varchar(50) COMMENT '最近修改人'")
    private String lastEditPerson;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT DEFAULT NULL COMMENT '备注' ")
    private String remark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sale_contract_id",columnDefinition = "bigint  COMMENT '所属合同id'")
    private SaleContract saleContract;


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

    public SaleContract getSaleContract() {
        return saleContract;
    }

    public void setSaleContract(SaleContract saleContract) {
        this.saleContract = saleContract;
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
}
