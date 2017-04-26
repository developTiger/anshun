package com.sunesoft.ancon.core.inContract.application.dto;

import com.sunesoft.ancon.core.inContract.domain.ContractSpeed;

import java.math.BigDecimal;

/**
 * Created by xiazl on 2016/11/22.
 */
public class InContractDto {
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
     * 合同编号
     */
    private String num;
    /**
     * 合同名称
     */
    private String name;
    /**
     * 乙方名称
     */
    private String partyB;
    /**
     * 乙方公司名称
     */
    private Long partyBId;

    /**
     * 所属分公司名称
     */
    private String company;
    /**
     * 所属分公司标识
     */
    private Long companyId;
    /**
     * 公司属性(总部1 ，独立2， 非独3)
     */
    private int companyType;
    /**
     * 合同金额
     */
    private BigDecimal value;
    /**
     * 截止日期
     */
    private String sendDate;
    /**
     * 签约时间
     */
    private String sbillDate;
    /**
     * 登记人（操作人名称）
     */
    private String optionName;

    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的销售合同的id
     */
    private Long salId;
    /**
     * 关联的销售合同的num
     */
    private String salNum;
    /**
     * 关联的销售合同的name
     */
    private String salName;
    /**
     * 合同状态
     */
    private ContractSpeed status;

    /**
     * 已付款金额
     */
    private BigDecimal payMoney;
    /**
     * 已开票金额
     */
    private BigDecimal billMoney;
    /**
     * 工期
     */
    private float days;
    /**
     * 剩余工期
     */
    private float nextDays;
    /**
     * 最近修改人
     */
    private String editor;
    /**
     * 开票率
     */
    private Double iPercent;
    /**
     * 付款率
     */
    private Double pPercent;
    /**
     * 是否可编辑的 0 no 1,yes
     */
    private int canEdit;
    /**
     *可否发票登记0 no 1,yes
     */
    private int canV;
    /**
     * 可否付款0 no 1,yes
     */
    private int canP;

    public int getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(int canEdit) {
        this.canEdit = canEdit;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num.trim();
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
        this.partyB = partyB.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }

    public String getSalName() {
        return salName;
    }

    public void setSalName(String salName) {
        this.salName = salName;
    }

    public String getSalNum() {
        return salNum;
    }

    public void setSalNum(String salNum) {
        this.salNum = salNum;
    }

    public String getSbillDate() {
        return sbillDate;
    }

    public void setSbillDate(String sbillDate) {
        this.sbillDate = sbillDate;
    }

    public String getScreateDateTime() {
        return screateDateTime;
    }

    public void setScreateDateTime(String screateDateTime) {
        this.screateDateTime = screateDateTime;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSlastUpdateTime() {
        return slastUpdateTime;
    }

    public void setSlastUpdateTime(String slastUpdateTime) {
        this.slastUpdateTime = slastUpdateTime;
    }

    public ContractSpeed getStatus() {
        return status;
    }

    public void setStatus(ContractSpeed status) {
        this.status = status;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value.setScale(4,BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney.setScale(4,BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney.setScale(4,BigDecimal.ROUND_HALF_UP);
    }

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public float getNextDays() {
        return nextDays;
    }

    public void setNextDays(float nextDays) {
        this.nextDays = nextDays;
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

    public Double getiPercent() {
        return iPercent;
    }

    public void setiPercent(Double iPercent) {
        this.iPercent = iPercent;
    }

    public Double getpPercent() {
        return pPercent;
    }

    public void setpPercent(Double pPercent) {
        this.pPercent = pPercent;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public int getCanP() {
        return canP;
    }

    public void setCanP(int canP) {
        this.canP = canP;
    }

    public int getCanV() {
        return canV;
    }

    public void setCanV(int canV) {
        this.canV = canV;
    }
}

