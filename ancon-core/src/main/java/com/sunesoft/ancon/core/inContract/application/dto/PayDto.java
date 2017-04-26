package com.sunesoft.ancon.core.inContract.application.dto;

import com.sunesoft.ancon.core.inContract.domain.PayStyle;

import java.math.BigDecimal;

/**
 * Created by xiazl on 2016/11/23.
 */
public class PayDto {
    /**
     * 标识
     */
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
     * 最近修改时间
     */
    private String slastUpdateTime;

    /**
     * 付款金额
     */
    private BigDecimal money;
    /**
     * 付款时间
     */
    private String spayTime;
    /**
     * 付款方式
     */
    private PayStyle payStyle;
    /**
     * 用于展示
     */
    private String payStyleName;
//    /**
//     * 凭据  作废字段
//     */
//    private String proof;
    /**
     * 操作人
     */
    private String optionName;
    /**
     * 最近修改人
     */
    private String editor;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的进项合同id
     */
    private Long inId;
    /**
     * 关联的进项合同name
     */
    private String inName;
    /**
     * 关联的进项合同num
     */
    private String inNum;
    /**
     * 乙方
     */
    private String partyB;

    /**
     * 乙方标识
     */
    private Long partyBId;

    /**
     * 所属分公司
     */
    private String company;
    /**
     * 所属分公司标识
     */
    private Long companyId;
    /**
     * 组装数据，用于展示
     */
    private BigDecimal totalMoney;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney.setScale(4,BigDecimal.ROUND_HALF_UP);
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
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

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }

    public String getSpayTime() {
        return spayTime;
    }

    public void setSpayTime(String spayTime) {
        this.spayTime = spayTime;
    }

    public PayStyle getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(PayStyle payStyle) {
        this.payStyle = payStyle;
    }

    public String getPayStyleName() {
        return payStyleName;
    }

    public void setPayStyleName(String payStyleName) {
        this.payStyleName = payStyleName;
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
