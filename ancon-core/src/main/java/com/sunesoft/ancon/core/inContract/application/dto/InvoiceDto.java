package com.sunesoft.ancon.core.inContract.application.dto;

import com.sunesoft.ancon.core.inContract.domain.InvoiceType;
import com.sunesoft.ancon.core.inContract.domain.RateType;

import java.math.BigDecimal;

/**
 * Created by xiazl on 2016/11/23.
 */
public class InvoiceDto {

    private Long id;
    /**
     * 是否有效
     */
    private Boolean isActive;
    /**
     * 创建时间
     */
    private String screateDateTime;
    /**
     * 最近一次修改时间
     */
    private String slastUpdateTime;


    /**
     * 开票时间
     */
    private String sinvoiceDate;
    /**
     * 金额
     */
    private BigDecimal money;



    /**
     * 关联的进项合同id
     */
    private Long inId;
    /**
     * 关联的进项合同num
     */
    private String inNum;
    /**
     * 关联的进项合同name
     */
    private String inName;
    /**
     * 补充说明
     */
    private String remark;
    /**
     * 发票类型
     */
    private InvoiceType type;
    /**
     * 发票类型
     */
    private String typeName;


    /**
     * 最近修改人
     */
    private String edtitor;
    /**
     * 登记人
     */
    private String optionName;
    /**
     * 计税方式
     */
    private RateType rateType;

    /**
     * 计税方式
     */
    private String rateTypeName;

    /**
     * 甲方名称
     */
    private String partyA;
    /**
     * 甲方单位标识
     */
    private Long partyAId;


    /**
     * 乙方单位标识
     */
    private Long partyBId;
    /**
     * 开票单位
     */
    private String partyB;
    /**
     * 用于页面展示合计金额
     */
    private BigDecimal totalMoney;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney.setScale(4,BigDecimal.ROUND_HALF_UP);
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

    public String getEdtitor() {
        return edtitor;
    }

    public void setEdtitor(String edtitor) {
        this.edtitor = edtitor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money.setScale(4,BigDecimal.ROUND_HALF_UP);
    }


    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
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

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getSinvoiceDate() {
        return sinvoiceDate;
    }

    public void setSinvoiceDate(String sinvoiceDate) {
        this.sinvoiceDate = sinvoiceDate;
    }

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public String getRateTypeName() {
        return rateTypeName;
    }

    public void setRateTypeName(String rateTypeName) {
        this.rateTypeName = rateTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
