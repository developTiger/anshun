package com.sunesoft.ancon.core.saleContract.application.dtos;

import com.sunesoft.ancon.core.saleContract.domain.SaleContract;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/25.
 */
public class ConstructionValueDto {

    private Long id;

    /**
     * 产值(金额) 合计
     */
    private BigDecimal totalMoney;

    /**
     * 开始日期
     */
    private Date beginTime;

    /**
     * 结束日期
     */
    private Date endTime;

    /**
     * 产值
     */
    private BigDecimal outputValue;

    /**
     * 填写日期
     */
    private Date writeDate;

    /**
     * 填写人
     */
    private String writePerson;

    /**
     * 最近修改人
     */
    private String lastUpdatePerson;

    /**
     * 备注
     */
    private String remark;

    private SaleContractDto saleContractDto;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigDecimal outputValue) {
        this.outputValue = outputValue;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public String getWritePerson() {
        return writePerson;
    }

    public void setWritePerson(String writePerson) {
        this.writePerson = writePerson;
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
