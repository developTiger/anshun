package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.saleContract.application.criteria.BillingCriteria;
import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public interface BillingService {

    public Long addBilling(BillingDto dto);

    public boolean deleteBilling(Long[] id);

    public void updateBilling(BillingDto dto);

    public BillingDto getById(Long id);

    public List<BillingDto> getAll();

    public PagedResult<BillingDto> fingPages(BillingCriteria dtoCriteria);

    public List<BillingDto> getBySaleContractId(Long id);

    //销售合同统计图 有四个接口写在开票里面

    public List<QuerySaleContractsBy_companyDto> querySaleContractsBy_company(SaleContractCriteria criteria);

    public List<QuerySaleContractsBy_project_majorDto> QuerySaleContractsBy_project_major(SaleContractCriteria criteria);

    public List<QuerySaleContractsBy_yearDto> QuerySaleContractsBy_year(SaleContractCriteria criteria);

    public List<QuerySaleContractsBy_contractTypeDto> QuerySaleContractsBy_contractType(SaleContractCriteria criteria);

}
