package com.sunesoft.ancon.core.companys.application.dto;

import com.sunesoft.ancon.core.companys.domain.OwnerShip;
import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by kkk on 2016/11/28.
 */

public class ContractorPartyDto {


    private Long id;

    /**
     * 销售金额 按公司计算
     */
    private BigDecimal saleContractMoney;

    /**
     * 编号
     */
    private String num;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 所有制性质
     */
    private OwnerShip ownerShip;

    /**
     *代表人
     */
    private String RepresentativePerson;

    /**
     * 备注
     */
    private String Remarks;


    private Integer type;//  1: 接包方  2：发包方  3:既不是接包方也不是发包方

    public BigDecimal getSaleContractMoney() {
        return saleContractMoney;
    }

    public void setSaleContractMoney(BigDecimal saleContractMoney) {
        this.saleContractMoney = saleContractMoney;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
