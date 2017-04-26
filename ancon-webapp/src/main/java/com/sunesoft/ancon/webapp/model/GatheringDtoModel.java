package com.sunesoft.ancon.webapp.model;

import java.io.PipedReader;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/12/13.
 */
public class GatheringDtoModel {

    private BigDecimal gatheringMoney;//收款金额
    private Date gatheringTime;//收款日期
    private String num;//所属合同编号
    private String name;//所属合同名称
    private String branchCompany;//所属分公司
    private String gatheringType;//收款方式
    private String jiafangName;//甲方名称
    private String gatheringPerson;//收款人
    private String remark;//备注

    public BigDecimal getGatheringMoney() {
        return gatheringMoney;
    }

    public void setGatheringMoney(BigDecimal gatheringMoney) {
        this.gatheringMoney = gatheringMoney;
    }

    public Date getGatheringTime() {
        return gatheringTime;
    }

    public void setGatheringTime(Date gatheringTime) {
        this.gatheringTime = gatheringTime;
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

    public String getBranchCompany() {
        return branchCompany;
    }

    public void setBranchCompany(String branchCompany) {
        this.branchCompany = branchCompany;
    }

    public String getGatheringType() {
        return gatheringType;
    }

    public void setGatheringType(String gatheringType) {
        this.gatheringType = gatheringType;
    }

    public String getJiafangName() {
        return jiafangName;
    }

    public void setJiafangName(String jiafangName) {
        this.jiafangName = jiafangName;
    }

    public String getGatheringPerson() {
        return gatheringPerson;
    }

    public void setGatheringPerson(String gatheringPerson) {
        this.gatheringPerson = gatheringPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
