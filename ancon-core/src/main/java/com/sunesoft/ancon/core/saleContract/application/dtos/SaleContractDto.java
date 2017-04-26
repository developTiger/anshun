package com.sunesoft.ancon.core.saleContract.application.dtos;

import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public class SaleContractDto {

    /**
     * 当前用户的权限
     */
    private List<ResourceDto> authorityControl;

    private Long companyId;


    private Long id;

    private Long parentId;//父类id

    private SaleContractDto parentContractDto;//父类

    private List<SaleContractDto> childContractDto;//子类id(多个)

    private List<ContractorPartyDto> contractorPartyDtos;

    private BigInteger number;//合同签单数 展示数据

    /**
     * 合同是否已返回
     */
    private String contractIsReturn;

    /**
     * 定审状态 为定审之前 定审金额为合同金额
     */
    private String judgeStatus;

    /**
     * 工程专业
     */
    private String projectMajor;

    /**
     * 开工日期
     */
    private Date projectStartTime;

    /**
     * 合同状态
     */
    private String contractStatus;

    /**
     * 合同编号
     */
    private String num;

    /**
     * 合同名称
     */
    private String name;

    /**
     * 合同金额
     */
    private BigDecimal contractMoney;

    /**
     * 定审金额
     */
    private BigDecimal judgeMoney;

    /**
     * 定审日期
     */
    private Date judgeTime;

    /**
     * 所属分公司
     */
    private String branchCompany;

    /**
     * 甲方名称
     * 甲方(付钱)   乙方(打工)
     * 在销售合同中   我们是公司，是乙方
     * 在进项合同中   我们是打工的，是甲方
     */
    private String jiaFangName;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 中标通知书
     */
    private String bidNotice;

    /**
     * 施工许可证
     */
    private String constructLicense;

    /**
     * 竣工验收单
     */
    private String finishCheck;

    /**
     * 工程结算单
     */
    private String projectSettlement;

    /**
     * 合同签订日期
     */
    @Column(name = "contract_begin_time")
    private Date contractBeginTime;

    /**
     * 合同结束(到期)日期
     */
    private Date contractEndTime;

    /**
     * 剩余天数提醒
     */
    private Integer surplusDay;

    /**
     * 开票统计
     */
    private BigDecimal billingCount;

    /**
     * 收款统计
     */
    private BigDecimal receivablesCount;

    /**
     * 施工产值
     */
    private BigDecimal constructionValue;

    /**
     * 开票率
     */
    private String billingRate;

    /**
     * 收款率
     */
    private String gatheringRate;

    /**
     * 施工产值率
     */
    private String conValueRate;

    /**
     * 登记人
     */
    private String registerPerson;

    /**
     * 备注
     */
    private String remark;


//    -----------------------------------------------------------------

    /**
     * 所属项目部
     */
    private String theProjectDepartment;

    /**
     * 工期
     */
    private Integer timeLimit;

    public String getContractIsReturn() {
        return contractIsReturn;
    }

    public void setContractIsReturn(String contractIsReturn) {
        this.contractIsReturn = contractIsReturn;
    }

    public SaleContractDto getParentContractDto() {
        return parentContractDto;
    }

    public void setParentContractDto(SaleContractDto parentContractDto) {
        this.parentContractDto = parentContractDto;
    }

    public List<ResourceDto> getAuthorityControl() {
        return authorityControl;
    }

    public void setAuthorityControl(List<ResourceDto> authorityControl) {
        this.authorityControl = authorityControl;
    }

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(String billingRate) {
        this.billingRate = billingRate;
    }

    public String getGatheringRate() {
        return gatheringRate;
    }

    public void setGatheringRate(String gatheringRate) {
        this.gatheringRate = gatheringRate;
    }

    public String getConValueRate() {
        return conValueRate;
    }

    public void setConValueRate(String conValueRate) {
        this.conValueRate = conValueRate;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getJudgeTime() {
        return judgeTime;
    }

    public void setJudgeTime(Date judgeTime) {
        this.judgeTime = judgeTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<SaleContractDto> getChildContractDto() {
        return childContractDto;
    }

    public void setChildContractDto(List<SaleContractDto> childContractDto) {
        this.childContractDto = childContractDto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return bidNotice;
    }

    public void setBidNotice(String bidNotice) {
        this.bidNotice = bidNotice;
    }

    public String getConstructLicense() {
        return constructLicense;
    }

    public void setConstructLicense(String constructLicense) {
        this.constructLicense = constructLicense;
    }

    public String getFinishCheck() {
        return finishCheck;
    }

    public List<ContractorPartyDto> getContractorPartyDtos() {
        return contractorPartyDtos;
    }

    public void setContractorPartyDtos(List<ContractorPartyDto> contractorPartyDtos) {
        this.contractorPartyDtos = contractorPartyDtos;
    }

    public void setFinishCheck(String finishCheck) {
        this.finishCheck = finishCheck;
    }

    public String getProjectSettlement() {
        return projectSettlement;
    }

    public void setProjectSettlement(String projectSettlement) {
        this.projectSettlement = projectSettlement;
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

    public Integer getSurplusDay() {
        return surplusDay;
    }

    public void setSurplusDay(Integer surplusDay) {
        this.surplusDay = surplusDay;
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

    public String getTheProjectDepartment() {
        return theProjectDepartment;
    }

    public void setTheProjectDepartment(String theProjectDepartment) {
        this.theProjectDepartment = theProjectDepartment;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

}
