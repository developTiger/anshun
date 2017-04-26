package com.sunesoft.ancon.core.saleContract.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import javax.xml.soap.Text;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 收款管理
 * Created by admin on 2016/11/25.
 */
@Entity
@Table(name = "ancon_sys_gathering_management")
public class Gathering extends BaseEntity {

    public Gathering(){
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setIsActive(true);
    }

    /**
     * 收款总金额 合计
     */
    @Column(name = "gathering_total_money",columnDefinition = "decimal(19,4) COMMENT '收款总金额统计'")
    private BigDecimal gatheringTotalMoney;

    /**
     * 收款金额
     */
    @Column(name = "gathering_money",columnDefinition = "decimal(19,4) COMMENT '收款金额'")
    private BigDecimal gatheringMoney;

    /**
     * 收款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gathering_time",columnDefinition = "DATETIME  COMMENT '收款日期'")
    private Date gatheringTime;

    /**
     * 收款方式
     */
    @Column(name = "gathering_type",columnDefinition = "varchar(50) COMMENT '收款方式'")
    private String gatheringType;

    /**
     * 甲方名称
     */
    @Column(name = "jia_fang_name",columnDefinition = "varchar(200) COMMENT '甲方名称'")
    private String jiafangName;

    /**
     * 收款人
     */
    @Column(name = "gathering_person",columnDefinition = "varchar(50) COMMENT '收款人'")
    private String gatheringPerson;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_contract_id",columnDefinition = "bigint  COMMENT '所属合同id'")
    private SaleContract saleContract;

    public BigDecimal getGatheringTotalMoney() {
        return gatheringTotalMoney;
    }

    public void setGatheringTotalMoney(BigDecimal gatheringTotalMoney) {
        this.gatheringTotalMoney = gatheringTotalMoney;
    }

    public SaleContract getSaleContract() {
        return saleContract;
    }

    public void setSaleContract(SaleContract saleContract) {
        this.saleContract = saleContract;
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
