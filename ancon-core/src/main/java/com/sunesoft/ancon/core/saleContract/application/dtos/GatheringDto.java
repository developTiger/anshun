package com.sunesoft.ancon.core.saleContract.application.dtos;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/25.
 */
public class GatheringDto {

    private Long id;

    /**
     * 收款总金额 合计
     */
    private BigDecimal gatheringTotalMoney;

    /**
     * 收款金额
     */
    private BigDecimal gatheringMoney;

    /**
     * 收款日期
     */
    private Date gatheringTime;

    /**
     * 收款方式
     */
    private String gatheringType;

    /**
     * 甲方名称
     */
    private String jiafangName;

    /**
     * 收款人
     */
    private String gatheringPerson;

    /**
     * 最近修改人
     */
    private String lastUpdatePerson;

    /**
     * 备注
     */
    private String remark;



    private SaleContractDto saleContractDto;

    public BigDecimal getGatheringTotalMoney() {
        return gatheringTotalMoney;
    }

    public void setGatheringTotalMoney(BigDecimal gatheringTotalMoney) {
        this.gatheringTotalMoney = gatheringTotalMoney;
    }

    public SaleContractDto getSaleContractDto() {
        return saleContractDto;
    }

    public void setSaleContractDto(SaleContractDto saleContractDto) {
        this.saleContractDto = saleContractDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getLastUpdatePerson() {
        return lastUpdatePerson;
    }

    public void setLastUpdatePerson(String lastUpdatePerson) {
        this.lastUpdatePerson = lastUpdatePerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
