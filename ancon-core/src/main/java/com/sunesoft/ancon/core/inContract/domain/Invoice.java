package com.sunesoft.ancon.core.inContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;
import com.sunesoft.ancon.fr.utils.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiazl on 2016/11/23.
 */
@Entity
@Table(name = "ancon_sys_Invoice")
public class Invoice extends BaseEntity {
    /**
     * 发票编号
     */
    @Column(unique = true,columnDefinition = "varchar(200) COMMENT '发票编号'")
    private String num;
    /**
     * 发票名称
     */
    @Column(columnDefinition = "varchar(200) COMMENT '发票名称'")
    private String name;
    /**
     * 开票单位
     */

    @Column(name = "party_b",columnDefinition = "varchar(200) COMMENT '开票单位'")
    private String partyB;
    /**
     * 乙方单位标识
     */
    @Column(name = "partyB_id",columnDefinition = "bigint  COMMENT '乙方单位标识'")
    private Long partyBId;
    /**
     * 开票时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invoice_date",columnDefinition = "datetime COMMENT '开票时间'")
    private Date invoiceDate;
    /**
     * 金额(专票)
     */
    @Column(columnDefinition = "decimal(19,4) COMMENT '金额(专票)'")
    private BigDecimal smoney;
    /**
     * 金额（普票）
     */
    @Column(columnDefinition = "decimal(19,4) COMMENT '金额（普票）'")
    private BigDecimal cmoney;
    /**
     * 税率
     */
    @Column(columnDefinition = "decimal(19,2) COMMENT '税率'")
    private BigDecimal rate;
    /**
     * 关联的进项合同
     */
    @Column(name = "in_id",columnDefinition = "bigint  COMMENT '关联的进项合同标识'")
    private Long inId;
    @Column(name = "in_num",columnDefinition = "varchar(200) COMMENT '关联的进项合同编号'")
    private String inNum;
    @Column(name = "in_name",columnDefinition = "varchar(200) COMMENT '关联的进项合同名称'")
    private String inName;
    /**
     * 补充说明
     */
    @Column(columnDefinition = "varchar(7000) COMMENT '补充说明'")
    private String remark;
    /**
     * 发票类型
     */
    @Column(name = "invoice_type", nullable = false,columnDefinition = "int(2) COMMENT '发票类型：0普票,1专票'")
    private InvoiceType type;
    /**
     * 最近修改人
     */
    @Column(columnDefinition = "varchar(200) COMMENT '最近修改人'")
    private String edtitor;
    /**
     * 登记人
     */
    @Column(columnDefinition = "varchar(200) COMMENT '登记人'")
    private String optionName;

    /**
     * 税额
     */
    @Column(columnDefinition = "decimal(19,4) COMMENT '税额'")
    private BigDecimal rateMoney;
    /**
     * 税价合计
     */
    @Column(columnDefinition = "decimal(19,4) COMMENT '税价合计'")
    private BigDecimal rateSum;
    /**
     * 计税方式
     */
    @Column(columnDefinition = "int(2) COMMENT '计税方式:0一般计税，1简易征税'")
    private RateType rateType;
    /**
     * 甲方名称
     */
    @Column(columnDefinition = "varchar(200) COMMENT '甲方名称'")
    private String partyA;
    /**
     * 甲方单位标识
     */
   @Column(name = "partyA_id",columnDefinition = "bigint COMMENT '甲方单位标识'")
    private Long  partyAId;



    public Invoice() {
        setIsActive(true);
        setLastUpdateTime(new Date());
        setCreateDateTime(new Date());
        setType(InvoiceType.Common);
    }


    public Long getPartyAId() {
        return partyAId;
    }

    public void setPartyAId(Long partyAId) {
        this.partyAId = partyAId;
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getInId() {
        return inId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
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

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public String getEdtitor() {
        return edtitor;
    }

    public void setEdtitor(String edtitor) {
        this.edtitor = edtitor;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public BigDecimal getRateMoney() {
        return rateMoney;
    }

    public void setRateMoney(BigDecimal rateMoney) {
        this.rateMoney = rateMoney;
    }

    public BigDecimal getRateSum() {
        return rateSum;
    }

    public void setRateSum(BigDecimal rateSum) {
        this.rateSum = rateSum;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getCmoney() {
        return cmoney;
    }

    public void setCmoney(BigDecimal cmoney) {
        this.cmoney = cmoney;
    }

    public BigDecimal getSmoney() {
        return smoney;
    }

    public void setSmoney(BigDecimal smoney) {
        this.smoney = smoney;
    }
}
