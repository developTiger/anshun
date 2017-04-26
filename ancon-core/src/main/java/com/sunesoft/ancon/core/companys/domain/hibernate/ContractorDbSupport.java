package com.sunesoft.ancon.core.companys.domain.hibernate;


import com.sunesoft.ancon.core.companys.domain.ContractorParty;
import com.sunesoft.ancon.fr.infrastructure.HibernateDbSet;
import com.sunesoft.ancon.fr.infrastructure.HibernateDbSupport;
import org.springframework.stereotype.Repository;

/**
 * 系统数据库操作单元。
 */
@Repository("contractorDbSupport")
public class ContractorDbSupport extends HibernateDbSupport {

    private HibernateDbSet<ContractorParty> parameter;
    public HibernateDbSet<ContractorParty> getParameter() {
        if (parameter == null)
            parameter = new HibernateDbSet<ContractorParty>(this, ContractorParty.class);
        return parameter;
    }
}
