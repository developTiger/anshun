package com.sunesoft.ancon.core.inContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进项合同实体对象
 * Created by xiazl on 2016/11/22.
 */
@Entity
@Table(name = "ancon_sys_in_contract")
public class InContract extends BaseEntity {

    /**
     * 合同编号
     */
    @Column(unique = true,columnDefinition = "varchar(200) COMMENT '合同编号'")
    private String num;
    /**
     * 合同名称
     */
    @Column(columnDefinition = "varchar(200) COMMENT '合同名称'")
    private String name;
    /**
     * 乙方名称
     */
    @Column(columnDefinition = "varchar(200) COMMENT '乙方名称'")
    private String partyB;
    /**
     * 乙方公司标识
     */
    @Column(name = "partyB_id",columnDefinition = "bigint(20)  COMMENT '乙方公司标识'" )
    private Long partyBId;
    /**
     * 所属分公司名称
     */
    @Column(columnDefinition = "varchar(200) COMMENT '所属分公司名称'")
    private String company;
    /**
     * 所属分公司标识
     */
    @Column(name = "company_id",columnDefinition = "bigint(20)  COMMENT '所属分公司标识'")
    private Long companyId;
    /**
     * 合同金额
     */
    @Column(name = "contract_money",columnDefinition = "decimal(19,4) COMMENT '合同金额' ")
    private BigDecimal value;

    /**
     * 已付款金额
     */
    @Column(name = "pay_money",columnDefinition = "decimal(19,4) COMMENT '已付款金额'")
    private BigDecimal payMoney;
    /**
     * 已开票金额
     */
    @Column(name = "bill_money", columnDefinition = "decimal(19,4) COMMENT '已开票金额'")
    private BigDecimal billMoney;
    /**
     * 截止日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date",columnDefinition = "datetime COMMENT '截止日期'")
    private Date endDate;
    /**
     * 签约时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bill_date",columnDefinition = "datetime COMMENT '签约时间'")
    private Date billDate;
    /**
     * 登记人（操作人名称）
     */
    @Column(columnDefinition = "varchar(100) COMMENT '登记人'")
    private String optionName;
    /**
     * 备注
     */
    @Column(name = "remark",columnDefinition = "varchar(7000) COMMENT '备注'")
    private String remark;
    /**
     * 关联的销售合同的id
     */
    @Column(name = "sale_id",columnDefinition = "bigint  COMMENT '关联的销售合同的id'")
    private Long salId;
    /**
     * 关联的销售合同的num
     */
    @Column(name = "sal_num",columnDefinition = "varchar(1000) COMMENT '备注'")
    private String salNum;
    /**
     * 关联的销售合同的name
     */
    @Column(name = "sal_name",columnDefinition = "varchar(1000) COMMENT '备注'")
    private String salName;
    /**
     * 合同状态
     */
    @Column(columnDefinition = "int(2) COMMENT '合同状态：0施工中,1已完结'")
    private ContractSpeed status;
    /**
     * 最近修改人
     */
    @Column(columnDefinition = "varchar(200) COMMENT '最近修改人'")
    private String editor;

    /**
     * 开票率
     */
    @Column(columnDefinition = "decimal(19,2) COMMENT '开票率'")
    private Double iPercent;
    /**
     * 付款率
     */
    @Column( columnDefinition = "decimal(19,2) COMMENT '付款率'")
    private Double pPercent;

    /**
     * 公司属性(总部1 ，独立2， 非独3)
     */
    @Column(name = "compay_type",columnDefinition = "int(2) COMMENT '公司属性(总部1 ，独立2， 非独3)'")
    private int companyType;

    public InContract() {
        setCreateDateTime(new Date());
        setLastUpdateTime(new Date());
        setIsActive(true);

        setStatus(ContractSpeed.Run);
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

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        this.value = value;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
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

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }
}
