package com.sunesoft.ancon.core.inContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiazl on 2016/11/23.
 */
@Entity
@Table(name = "ancon_in_pay")
public class Pay extends BaseEntity {

    /**
     * 付款金额
     */
    @Column(columnDefinition = "decimal(19,4) COMMENT '付款金额'")
    private BigDecimal money;
    /**
     * 付款时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pay_time",columnDefinition = "datetime COMMENT '付款时间'")
    private Date payTime;
    /**
     * 付款方式
     */
    @Column(columnDefinition = "int(2) COMMENT '付款方式:0现金，1银行转账，2汇票，3支票，4本票，5其他'")
    private PayStyle payStyle;
//    /**
//     * 凭据 作废字段
//     */
//    @Column(columnDefinition = "varchar(2000) COMMENT '凭据'")
//    private String proof;
    /**
     * 操作人
     */
    @Column(name = "option_name",columnDefinition = "varchar(200) COMMENT '操作人'")
    private String optionName;
    /**
     * 最近修改人
     */
    @Column(columnDefinition = "varchar(200) COMMENT '最近修改人'")
    private String editor;
    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(7000) COMMENT '备注'")
    private String remark;
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
     * 乙方
     */
    @Column(columnDefinition = "varchar(200) COMMENT '乙方'")
    private String partyB;
    /**
     * 乙方标识
     */
    @Column(name = "partyB_id",columnDefinition = "bigint  COMMENT '乙方标识'")
    private Long partyBId;
    /**
     * 所属分公司
     */
    @Column(columnDefinition = "varchar(200) COMMENT '所属分公司'")
    private String company;
    /**
     * 所属分公司标识
     */
    @Column(name = "company_id",columnDefinition = "bigint  COMMENT '所属分公司标识'")
    private Long companyId;

    public Pay() {
        setIsActive(true);
        setCreateDateTime(new Date());
        setLastUpdateTime(new Date());
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PayStyle getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(PayStyle payStyle) {
        this.payStyle = payStyle;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
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
