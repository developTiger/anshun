package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2017/3/20.
 */
public class ImportSaleContractDtoModel {

    private String num;//合同编号

    private String name;//合同名称

    private BigDecimal contractMoney;//合同金额

    private BigDecimal judgeMoney;//定审金额

    private String judgeStatus;//定审状态

    private String judgeTime;//定审日期

    private String branchCompany;//所属分公司

    private String jiaFangName;//甲方名称

    private String contractType;//合同类型

    private String bidNotice;//中标通知书

    private String constructLicense;//施工许可证

    private String finishCheck;//竣工验收单

    private String projectSettlement;//工程结算单

    private String contractBeginTime;//合同签订日期

    private String projectStartTime;//合同开工日期

    private String contractEndTime;//合同结束日期

    //登记人 设置为当前用户

    private String projectMajor;//工程专业

    private String contractStatus;//合同状态

    private String contractIsReturn;//合同是否已返回

    private String remark;//备注


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

    public String getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(String judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public String getJudgeTime() {
        return judgeTime;
    }

    public void setJudgeTime(String judgeTime) {
        this.judgeTime = judgeTime;
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

    public void setFinishCheck(String finishCheck) {
        this.finishCheck = finishCheck;
    }

    public String getProjectSettlement() {
        return projectSettlement;
    }

    public void setProjectSettlement(String projectSettlement) {
        this.projectSettlement = projectSettlement;
    }

    public String getContractBeginTime() {
        return contractBeginTime;
    }

    public void setContractBeginTime(String contractBeginTime) {
        this.contractBeginTime = contractBeginTime;
    }

    public String getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(String projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public String getProjectMajor() {
        return projectMajor;
    }

    public void setProjectMajor(String projectMajor) {
        this.projectMajor = projectMajor;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractIsReturn() {
        return contractIsReturn;
    }

    public void setContractIsReturn(String contractIsReturn) {
        this.contractIsReturn = contractIsReturn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
