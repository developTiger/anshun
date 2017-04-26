package com.sunesoft.ancon.core.saleContract.application.dtos;

/**
 * Created by xiazl on 2017/4/6.
 */
public class InSimpleSaleContractDto {
    private Long id;

    private String name;

    private Long companyId;

    public InSimpleSaleContractDto(Long id, String name, Long companyId) {

        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
