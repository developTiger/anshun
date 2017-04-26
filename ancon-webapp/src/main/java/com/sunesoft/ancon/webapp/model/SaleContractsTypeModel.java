package com.sunesoft.ancon.webapp.model;

import com.sunesoft.ancon.core.saleContract.application.dtos.QuerySaleContractsBy_contractTypeDto;

/**
 * 销售合同统计表 合同类型查询 model
 * Created by admin on 2016/12/22.
 */
public class SaleContractsTypeModel {

    private QuerySaleContractsBy_contractTypeDto[] common;
    private QuerySaleContractsBy_contractTypeDto[] important;

    public QuerySaleContractsBy_contractTypeDto[] getCommon() {
        return common;
    }

    public void setCommon(QuerySaleContractsBy_contractTypeDto[] common) {
        this.common = common;
    }

    public QuerySaleContractsBy_contractTypeDto[] getImportant() {
        return important;
    }

    public void setImportant(QuerySaleContractsBy_contractTypeDto[] important) {
        this.important = important;
    }
}
