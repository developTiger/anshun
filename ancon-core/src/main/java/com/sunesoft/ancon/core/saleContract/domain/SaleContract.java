package com.sunesoft.ancon.core.saleContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;
import com.sunesoft.ancon.fr.utils.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售合同
 * Created by admin on 2016/11/23.
 */
@Entity
@Table(name = "ancon_sys_sale_contract")
public class SaleContract extends BaseEntity {

    public SaleContract(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 合同是否已返回
     */
    @Column(name = "contract_is_return",columnDefinition = "varchar(100) COMMENT '合同是否已返回'")
    private String contractIsReturn;

    /**
     * 合同编号
     */
    @Column(columnDefinition = "varchar(100) COMMENT '合同编号'")
    private String num;

    /**
     * 合同名称
     */
    @Column(columnDefinition = "varchar(500) COMMENT '合同名称'")
    private String name;

    /**
     * 合同金额
     */
    @Column(name = "contract_money",columnDefinition = "decimal(19,4) COMMENT '合同金额'")
    private BigDecimal contractMoney;

    /**
     * 定审金额
     */
    @Column(name = "judge_money",columnDefinition = "decimal(19,4) COMMENT '定审金额'")
    private BigDecimal judgeMoney;

    /**
     * 定审状态 未定审之前 定审金额为合同金额
     */
    @Column(name = "judge_status",columnDefinition = "varchar(50) COMMENT '定审状态'")
    private String judgeStatus;

    /**
     * 定审日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "judge_time",columnDefinition = "DATETIME  COMMENT '定审日期'")
    private Date judgeTime;

    /**
     * 所属分公司
     */
    @Column(name = "branch_company",columnDefinition = "varchar(200) COMMENT '所属分公司'")
    private String branchCompany;

    /**
     * 甲方名称
     * 甲方(付钱)   乙方(打工)
     * 在销售合同中   我们是公司，是乙方
     * 在进项合同中   我们是打工的，是甲方
     */
    @Column(name = "jia_fang_name",columnDefinition = "varchar(200) COMMENT '甲方名称'")
    private String jiaFangName;

    /**
     * 合同类型
     */
    @Column(name = "contract_type",columnDefinition = "varchar(50) COMMENT '合同类型'")
    private String contractType;

    /**
     * 中标通知书
     */
    @Column(name = "bid_notice",columnDefinition = "varchar(50) COMMENT '中标通知书'")
    private String bidNotice;

    /**
     * 施工许可证
     */
    @Column(name = "construct_license",columnDefinition = "varchar(50) COMMENT '施工许可证'")
    private String constructLicense;

    /**
     * 竣工验收单
     */
    @Column(name = "finish_check",columnDefinition = "varchar(50) COMMENT '竣工验收单'")
    private String finishCheck;

    /**
     * 工程结算单
     */
    @Column(name = "project_settlement",columnDefinition = "varchar(50) COMMENT '工程结算单'")
    private String projectSettlement;

    /**
     * 合同签订日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_begin_time",columnDefinition = "DATETIME  COMMENT '合同签订日期'")
    private Date contractBeginTime;

    /**
     * 合同结束日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_end_time",columnDefinition = "DATETIME  COMMENT '合同结束日期'")
    private Date contractEndTime;



    /**
     * 开票统计
     */
    @Column(name = "billing_count",columnDefinition = "decimal(19,4) COMMENT '开票统计'")
    private BigDecimal billingCount;

    /**
     * 收款统计
     */
    @Column(name = "receivables_count",columnDefinition = "decimal(19,4) COMMENT '收款统计'")
    private BigDecimal receivablesCount;

    /**
     * 施工产值
     */
    @Column(name = "construction_value",columnDefinition = "decimal(19,4) COMMENT '施工产值'")
    private BigDecimal constructionValue;

    /**
     * 登记人
     */
    @Column(name = "register_person",columnDefinition = "varchar(50) COMMENT '登记人'")
    private String registerPerson;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT DEFAULT NULL COMMENT '备注' ")
    private String remark;


//    -----------------------------------------------------------------
    //工程专业 开工日期 合同状态

    /**
     * 工程专业
     */
    @Column(name = "project_major",columnDefinition = "varchar(50) COMMENT '工程专业'")
    private String projectMajor;

    /**
     * 合同开工日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "project_start_time",columnDefinition = "DATETIME  COMMENT '合同开工日期'")
    private Date projectStartTime;

    /**
     * 合同状态
     */
    @Column(name = "contract_status",columnDefinition = "varchar(50) COMMENT '合同状态'")
    private String contractStatus;



    @ManyToOne
    @JoinColumn(name = "parent_contract_id",columnDefinition = "bigint  COMMENT '父集id'")
    private SaleContract parentContract;        //可以理解为父集一张表


    @OneToMany
    @JoinColumn(name = "parent_contract_id",columnDefinition = "bigint  COMMENT '父集id'")
    private List<SaleContract> childContract;       //子集一张表，查询时可以直接把子元素查出来



    /**
     * 所属分公司 id
     */
    @Column(columnDefinition = "bigint(20) COMMENT '所属分公司id'")
    private Long companyId;

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public SaleContract getParentContract() {
        return parentContract;
    }

    public void setParentContract(SaleContract parentContract) {
        this.parentContract = parentContract;
    }

    public List<SaleContract> getChildContract() {
        return childContract;
    }

    public void setChildContract(List<SaleContract> childContract) {
        this.childContract = childContract;
    }

    public String getProjectMajor() {
        return projectMajor;
    }

    public void setProjectMajor(String projectMajor) {
        this.projectMajor = projectMajor;
    }

    public Date getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(Date projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getJudgeMoney() {
        return judgeMoney;
    }

    public void setJudgeMoney(BigDecimal judgeMoney) {
        this.judgeMoney = judgeMoney;
    }

    public String getBranchCompany() {
        return branchCompany;
    }

    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    public String getJiaFangName() {
        return jiaFangName;
    }

    public void setJiaFangName(String jiaFangName) {
        this.jiaFangName = jiaFangName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getBidNotice() {
        return bidNotice.substring(1,bidNotice.length());
    }

    public String getContractIsReturn() {
        if(!StringUtils.isNullOrWhiteSpace(contractIsReturn))

        return contractIsReturn.substring(1,contractIsReturn.length());
        else
            return "否";
    }

    public void setContractIsReturn(String contractIsReturn) {


        if (contractIsReturn.equals("是")){
            this.contractIsReturn = "0"+contractIsReturn;
        }else{
            this.contractIsReturn = "1"+contractIsReturn;
        }

    }

    public void setBidNotice(String bidNotice) {
        if (bidNotice.equals("有已收回")){
            this.bidNotice = "0"+bidNotice;
        }else if (bidNotice.equals("无")){
            this.bidNotice = "1"+bidNotice;
        }else if (bidNotice.equals("有未收回")) {
            this.bidNotice = "2" + bidNotice;
        }
    }

    public String getConstructLicense() {
        return constructLicense.substring(1,constructLicense.length());
    }

    public void setConstructLicense(String constructLicense) {
        if (constructLicense.equals("有已收回")){
            this.constructLicense = "0"+constructLicense;
        }else if (constructLicense.equals("无")){
            this.constructLicense = "1"+constructLicense;
        }else if (constructLicense.equals("有未收回")) {
            this.constructLicense = "2" + constructLicense;
        }
    }

    public String getFinishCheck() {
        return finishCheck.substring(1,finishCheck.length());
    }

    public void setFinishCheck(String finishCheck) {
        if (finishCheck.equals("有已收回")){
            this.finishCheck = "0"+finishCheck;
        }else if (finishCheck.equals("无")){
            this.finishCheck = "1"+finishCheck;
        }else if (finishCheck.equals("有未收回")) {
            this.finishCheck = "2" + finishCheck;
        }
    }

    public String getProjectSettlement() {
        return projectSettlement.substring(1,projectSettlement.length());
    }

    public void setProjectSettlement(String projectSettlement) {
        if (projectSettlement.equals("有已收回")){
            this.projectSettlement = "0"+projectSettlement;
        }else if (projectSettlement.equals("无")){
            this.projectSettlement = "1"+projectSettlement;
        }else if (projectSettlement.equals("有未收回")) {
            this.projectSettlement = "2" + projectSettlement;
        }
    }

    public Date getContractBeginTime() {
        return contractBeginTime;
    }

    public void setContractBeginTime(Date contractBeginTime) {
        this.contractBeginTime = contractBeginTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }



    public BigDecimal getBillingCount() {
        return billingCount;
    }

    public void setBillingCount(BigDecimal billingCount) {
        this.billingCount = billingCount;
    }

    public BigDecimal getReceivablesCount() {
        return receivablesCount;
    }

    public void setReceivablesCount(BigDecimal receivablesCount) {
        this.receivablesCount = receivablesCount;
    }

    public BigDecimal getConstructionValue() {
        return constructionValue;
    }

    public void setConstructionValue(BigDecimal constructionValue) {
        this.constructionValue = constructionValue;
    }

    public String getRegisterPerson() {
        return registerPerson;
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public Date getJudgeTime() {
        return judgeTime;
    }

    public void setJudgeTime(Date judgeTime) {
        this.judgeTime = judgeTime;
    }
}
