package com.sunesoft.ancon.webapp.model;

import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;

import java.util.List;

/**
 * Created by admin on 2016/12/1.
 */
public class SaleContractModel {

    private List<SaleContractDto> saleContractDtos;
    private List<ContractorPartyDto> contractorPartyDtos;

    public List<SaleContractDto> getSaleContractDtos() {
        return saleContractDtos;
    }

    public void setSaleContractDtos(List<SaleContractDto> saleContractDtos) {
        this.saleContractDtos = saleContractDtos;
    }

    public List<ContractorPartyDto> getContractorPartyDtos() {
        return contractorPartyDtos;
    }

    public void setContractorPartyDtos(List<ContractorPartyDto> contractorPartyDtos) {
        this.contractorPartyDtos = contractorPartyDtos;
    }
}
