package com.sunesoft.ancon.webapp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售合同列表 下载 匹配字段
 * Created by admin on 2016/12/13.
 */
public class SaleContractDownModel {

    private String num;//合同编号
    private String name;//合同名称
    private BigDecimal  contract_money;//合同金额
    private BigDecimal judge_money;//定审金额
    private String branch_company;//所属分公司
    private String jia_fang_name;//甲方
    private String project_major;//工程专业
    private String contract_type;//合同类型
    private String bid_notice;//中标通知书
    private String construct_license;//施工许可证
    private String finish_check;//竣工验收单
    private String project_settlement;//工程结算单
    private Date contract_begin_time;//合同签订日期
    private Date contract_end_time;//合同结束日期
    private Integer surplus_day;//剩余天数提醒
    private String contract_status;//合同状态
    private BigDecimal sum_billing_money;//开票统计
    private BigDecimal sum_gathering_money;//收款统计
    private BigDecimal sum_output_value;//施工产值
    private String register_person;//登记人
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

    public BigDecimal getContract_money() {
        return contract_money;
    }

    public void setContract_money(BigDecimal contract_money) {
        this.contract_money = contract_money;
    }

    public BigDecimal getJudge_money() {
        return judge_money;
    }

    public void setJudge_money(BigDecimal judge_money) {
        this.judge_money = judge_money;
    }

    public String getBranch_company() {
        return branch_company;
    }

    public void setBranch_company(String branch_company) {
        this.branch_company = branch_company;
    }

    public String getJia_fang_name() {
        return jia_fang_name;
    }

    public void setJia_fang_name(String jia_fang_name) {
        this.jia_fang_name = jia_fang_name;
    }

    public String getProject_major() {
        return project_major;
    }

    public void setProject_major(String project_major) {
        this.project_major = project_major;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getBid_notice() {
        return bid_notice;
    }

    public void setBid_notice(String bid_notice) {
        this.bid_notice = bid_notice;
    }

    public String getConstruct_license() {
        return construct_license;
    }

    public void setConstruct_license(String construct_license) {
        this.construct_license = construct_license;
    }

    public String getFinish_check() {
        return finish_check;
    }

    public void setFinish_check(String finish_check) {
        this.finish_check = finish_check;
    }

    public String getProject_settlement() {
        return project_settlement;
    }

    public void setProject_settlement(String project_settlement) {
        this.project_settlement = project_settlement;
    }

    public Date getContract_begin_time() {
        return contract_begin_time;
    }

    public void setContract_begin_time(Date contract_begin_time) {
        this.contract_begin_time = contract_begin_time;
    }

    public Date getContract_end_time() {
        return contract_end_time;
    }

    public void setContract_end_time(Date contract_end_time) {
        this.contract_end_time = contract_end_time;
    }

    public Integer getSurplus_day() {
        return surplus_day;
    }

    public void setSurplus_day(Integer surplus_day) {
        this.surplus_day = surplus_day;
    }

    public String getContract_status() {
        return contract_status;
    }

    public void setContract_status(String contract_status) {
        this.contract_status = contract_status;
    }

    public BigDecimal getSum_billing_money() {
        return sum_billing_money;
    }

    public void setSum_billing_money(BigDecimal sum_billing_money) {
        this.sum_billing_money = sum_billing_money;
    }

    public BigDecimal getSum_gathering_money() {
        return sum_gathering_money;
    }

    public void setSum_gathering_money(BigDecimal sum_gathering_money) {
        this.sum_gathering_money = sum_gathering_money;
    }

    public BigDecimal getSum_output_value() {
        return sum_output_value;
    }

    public void setSum_output_value(BigDecimal sum_output_value) {
        this.sum_output_value = sum_output_value;
    }

    public String getRegister_person() {
        return register_person;
    }

    public void setRegister_person(String register_person) {
        this.register_person = register_person;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
