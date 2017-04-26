package com.sunesoft.ancon.core;


import com.sunesoft.ancon.core.inContract.domain.Pay;
import com.sunesoft.ancon.core.saleContract.domain.Billing;
import com.sunesoft.ancon.core.saleContract.domain.ConstructionValue;
import com.sunesoft.ancon.core.saleContract.domain.Gathering;
import com.sunesoft.ancon.core.saleContract.domain.SaleContract;
import com.sunesoft.ancon.fr.infrastructure.HibernateDbSet;
import com.sunesoft.ancon.fr.infrastructure.HibernateDbSupport;
import com.sunesoft.ancon.core.parameter.entity.Parameter;
import com.sunesoft.ancon.core.parameter.entity.ParameterType;
import org.springframework.stereotype.Repository;

/**
 * 系统数据库操作单元。
 */
@Repository("anconDbSupport")
public class AnconDbSupport extends HibernateDbSupport {

    private HibernateDbSet<Parameter> parameter;

    public HibernateDbSet<Parameter> getParameter() {
        if (parameter == null)
            parameter = new HibernateDbSet<Parameter>(this, Parameter.class);
        return parameter;
    }


    private HibernateDbSet<ParameterType> parameterType;

    public HibernateDbSet<ParameterType> getParameterType() {
        if (parameterType == null)
            parameterType = new HibernateDbSet<ParameterType>(this, ParameterType.class);
        return parameterType;
    }

//    -----------------------------------------------------------------------------------
    /**
     * saleContract 销售合同
     */
    private HibernateDbSet<SaleContract> saleContract;

    public HibernateDbSet<SaleContract> getSaleContract() {
        if (saleContract == null)
            saleContract = new HibernateDbSet<>(this, SaleContract.class);
        return saleContract;
    }

//---------------------------------------------------------------------------------------------
    /**
     * billing  开票管理
     */
    private HibernateDbSet<Billing> billing;

    public HibernateDbSet<Billing> getBilling() {
        if (billing == null)
            billing = new HibernateDbSet<>(this, Billing.class);
        return billing;
    }

    //------------------------------------------------------------------------------------------------
    /**
     * gathering 收款管理
     */
    private HibernateDbSet<Gathering> gathering;

    public HibernateDbSet<Gathering> getGathering() {
        if (gathering == null)
            gathering = new HibernateDbSet<>(this, Gathering.class);
        return gathering;
    }

    //--------------------------------------------------------------------------------------------------
    /**
     * constructionValue 施工产值
     */
    private HibernateDbSet<ConstructionValue> constructionValue;

    public HibernateDbSet<ConstructionValue> getConstructionValue() {
        if (constructionValue == null)
            constructionValue = new HibernateDbSet<>(this, ConstructionValue.class);
        return constructionValue;
    }

    /**
     * 付款管理
     */
    private HibernateDbSet<Pay> pay;

    public HibernateDbSet<Pay> getPay() {
        if (pay == null) {
            pay = new HibernateDbSet<Pay>(this, Pay.class);
        }
        return pay;
    }
}
