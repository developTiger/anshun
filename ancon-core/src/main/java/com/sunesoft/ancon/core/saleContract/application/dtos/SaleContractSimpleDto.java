package com.sunesoft.ancon.core.saleContract.application.dtos;

import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaowy on 2017/3/23.
 */
public class SaleContractSimpleDto {

    private Long id;

    private Long parentId;//父类id
    /**
     * 合同名称
     */
    private String name;

    private Long companyId;

    /**
     * 合同状态
     */
    private String contractStatus;

    /**
     * 合同编号
     */
    private String num;

    /**
     * 合同类型
     */
    private String contractType;

    //region get set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    //endregion
}
