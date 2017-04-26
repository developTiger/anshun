package com.sunesoft.ancon.core.saleContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import javax.xml.soap.Text;
import java.io.PipedReader;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/11/25.
 */
@Entity
@Table(name = "ancon_sys_construction_value")
public class ConstructionValue extends BaseEntity {

    public ConstructionValue(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 产值(金额) 合计
     */
    @Column(name = "total_money",columnDefinition = "decimal(19,4) COMMENT '施工产值总金额统计'")
    private BigDecimal totalMoney;

    /**
     * 开始日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin_time",columnDefinition = "DATETIME  COMMENT '开始日期'")
    private Date beginTime;

    /**
     * 结束日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time",columnDefinition = "DATETIME  COMMENT '结束日期'")
    private Date endTime;

    /**
     * 产值
     */
    @Column(name = "output_value",columnDefinition = "decimal(19,4) COMMENT '施工产值'")
    private BigDecimal outputValue;

    /**
     * 填写日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "write_date",columnDefinition = "DATETIME  COMMENT '填写日期'")
    private Date writeDate;

    /**
     * 填写人
     */
    @Column(name = "write_person",columnDefinition = "varchar(50) COMMENT '填写人'")
    private String writePerson;

    /**
     * 最近修改人
     */
    @Column(name = "last_update_person",columnDefinition = "varchar(50) COMMENT '最近修改人'")
    private String lastUpdatePerson;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT DEFAULT NULL COMMENT '备注' ")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "sale_contract_id",columnDefinition = "bigint  COMMENT '所属合同id'")
    private SaleContract saleContract;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public SaleContract getSaleContract() {
        return saleContract;
    }

    public void setSaleContract(SaleContract saleContract) {
        this.saleContract = saleContract;
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
