package com.sunesoft.ancon.core.companys.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by kkk on 2016/11/28.
 */
@Entity
@Table(name = "ancon_sys_contractor_party")
public class ContractorParty extends BaseEntity {


    /**
     *类型
     */
    @Column(columnDefinition = "int(2) COMMENT '用户状态：0代表未禁用,1代表禁用'")
    private Integer type; // 1: 接包方 2：发包方


    @Column(unique = true,columnDefinition = "varchar(50) COMMENT '编号'")
    private String num;

    @Column(columnDefinition = "varchar(50) COMMENT '名称'")
    private String name;

    @Column(columnDefinition = "varchar(50) COMMENT '地址'")
    private String address;

    /**
     * 所有制性质
     */
    private OwnerShip ownerShip;


    @Column(columnDefinition = "varchar(50) COMMENT '代表人'")
    private String RepresentativePerson;

    @Column(columnDefinition = "varchar(200) COMMENT '备注'")
    private String Remarks;

    public ContractorParty() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OwnerShip getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(OwnerShip ownerShip) {
        this.ownerShip = ownerShip;
    }

    public String getRepresentativePerson() {
        return RepresentativePerson;
    }

    public void setRepresentativePerson(String representativePerson) {
        RepresentativePerson = representativePerson;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
