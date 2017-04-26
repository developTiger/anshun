package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.saleContract.application.criteria.BillingCriteria;
import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.saleContract.domain.Billing;
import com.sunesoft.ancon.core.saleContract.domain.SaleContract;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.DtoFactory;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/24.
 */
@Service("billingService")
public class BillingServiceImpl implements BillingService {

    @Autowired
    AnconDbSupport dbSupport;


    @Override
    public Long addBilling(BillingDto dto) {
        Billing billing = new Billing();
        SaleContract saleContract = DtoFactory.convert(dto.getSaleContractDto(),new SaleContract());
        billing.setSaleContract(saleContract);
        Billing bill = DtoFactory.convert(dto,billing);
        return (Long)dbSupport.getBilling().add(bill);
    }

    @Override
    public boolean deleteBilling(Long[] id) {
        Criteria criteria = dbSupport.getSession().createCriteria(Billing.class);
        if (id != null && id.length>0){
            criteria.add(Restrictions.in("id",id));
        }
        List<Billing> list = criteria.list();
        for (Billing billing:list){
            billing.setIsActive(false);
            dbSupport.getBilling().update(billing);
        }
        return true;
    }

    @Override
    public void updateBilling(BillingDto dto) {
        Billing billing = dbSupport.getBilling().get(dto.getId());

        billing.setBillingMoney(dto.getBillingMoney());
        billing.setBillingDate(dto.getBillingDate());
        billing.setTaxType(dto.getTaxType());
        billing.setJiafangName(dto.getJiafangName());
        billing.setRemark(dto.getRemark());

        billing.setTaxRate(dto.getTaxRate());
        billing.setTaxMoney(dto.getTaxMoney());
        billing.setPriceTaxCountMoney(dto.getPriceTaxCountMoney());

        billing.setLastEditPerson(dto.getLastEditPerson());

        dbSupport.getBilling().update(billing);
    }

    @Override
    public BillingDto getById(Long id) {
        Billing billing = dbSupport.getBilling().get(id);
        BillingDto dto = DtoFactory.convert(billing,new BillingDto());
        return dto;
    }

    @Override
    public List<BillingDto> getAll() {
        Criteria criteria = dbSupport.getSession().createCriteria(Billing.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<Billing> list = criteria.list();
        List<BillingDto> billingDtos = new ArrayList<>();
        for (Billing billing:list){
            SaleContract saleContract = billing.getSaleContract();
            SaleContractDto saleContractDto = DtoFactory.convert(saleContract,new SaleContractDto());
            BillingDto dto = DtoFactory.convert(billing,new BillingDto());
            dto.setSaleContractDto(saleContractDto);
            billingDtos.add(dto);
        }
        return billingDtos;
    }

    @Override
    public PagedResult<BillingDto> fingPages(BillingCriteria dtoCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(Billing.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.createAlias("saleContract","saleContract");

        if (dtoCriteria.getBe_time() != null)
            criteria.add(Restrictions.ge("billingDate",dtoCriteria.getBe_time()));
        if (dtoCriteria.getEnd_time() != null)
            criteria.add(Restrictions.le("billingDate",dtoCriteria.getEnd_time()));
        if (!StringUtils.isNullOrWhiteSpace(dtoCriteria.getTaxType()))
            criteria.add(Restrictions.eq("taxType",dtoCriteria.getTaxType()));
        if (!StringUtils.isNullOrWhiteSpace(dtoCriteria.getName()))
            criteria.add(Restrictions.like("saleContract.name","%"+dtoCriteria.getName()+"%"));
        if (dtoCriteria.getCompanyId() != null)
            criteria.add(Restrictions.eq("saleContract.companyId",dtoCriteria.getCompanyId()));
        if (!StringUtils.isNullOrWhiteSpace(dtoCriteria.getJiaFangName()))
            criteria.add(Restrictions.eq("jiafangName",dtoCriteria.getJiaFangName()));
        if (dtoCriteria.getSaleContractId() != null)
            criteria.add(Restrictions.eq("saleContract.id",dtoCriteria.getSaleContractId()));//树形结构 通过saleContractId查找



        //获取总记录数
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.addOrder(Order.desc("billingDate"));
        criteria.setResultTransformer(criteria.ROOT_ENTITY);
        criteria.setFirstResult((dtoCriteria.getPageNumber() - 1) * dtoCriteria.getPageSize()).setMaxResults(dtoCriteria.getPageSize());
        List<Billing> billings = criteria.list();
        List<BillingDto> list = new ArrayList<>();

        //计算开票总金额
        BigDecimal totalMoney =BigDecimal.ZERO;
        for (Billing billing:billings){
            BigDecimal money=billing.getPriceTaxCountMoney();
            totalMoney = totalMoney.add(money);
        }



        for (Billing billing:billings){
            SaleContractDto saleContractDto = DtoFactory.convert(billing.getSaleContract(),new SaleContractDto());
            BillingDto dto = new BillingDto();
            dto.setSaleContractDto(saleContractDto);
            BillingDto billingDto = DtoFactory.convert(billing,dto);

            billingDto.setBillTotalMoney(totalMoney);

            list.add(billingDto);
        }
        return new PagedResult<BillingDto>(list, dtoCriteria.getPageNumber(), dtoCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<BillingDto> getBySaleContractId(Long id) {
        Criteria criteria = dbSupport.getSession().createCriteria(Billing.class);

        criteria.createAlias("saleContract","sale");
        criteria.add(Restrictions.eq("isActive",true));
        if (id != null)
            criteria.add(Restrictions.eq("sale.id",id));
//        criteria.setResultTransformer(criteria.ROOT_ENTITY);
        List<Billing> list = criteria.list();
        List<BillingDto> billingDtos = new ArrayList<>();
        for (Billing billing:list){
            BillingDto dto = DtoFactory.convert(billing,new BillingDto());
            billingDtos.add(dto);
        }

        return billingDtos;
    }



    @Override
    public List<QuerySaleContractsBy_companyDto> querySaleContractsBy_company(SaleContractCriteria criteria) {

        String sql="SELECT companyId,branch_company,sum(judge_money) judge_money " +
                "from ancon_sys_sale_contract ";

        String group = " GROUP BY companyId ";
        String limit = " LIMIT 10 ";
        StringBuffer whereCondition = new StringBuffer(" where is_active =1 ");

        if (criteria.getBeginTime() != null){
            whereCondition.append(" and contract_begin_time>= ");

            whereCondition.append(DateHelper.formatDate(criteria.getBeginTime(), "yyyyMMdd"));
            //whereCondition.append("'");
        }

        if (criteria.getEndTime() != null){
            whereCondition.append(" and contract_begin_time<= ");
            //whereCondition.append("'");
            whereCondition.append(DateHelper.formatDate(criteria.getEndTime(), "yyyyMMdd"));

        }

        sql= sql+whereCondition.toString()+group+limit;

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<QuerySaleContractsBy_companyDto> result = dbSupport.queryForObjects(QuerySaleContractsBy_companyDto.class, query);

        return result;
    }

    @Override
    public List<QuerySaleContractsBy_project_majorDto> QuerySaleContractsBy_project_major(SaleContractCriteria criteria) {

        String sql="SELECT project_major ,sum(judge_money) judge_money from ancon_sys_sale_contract  " ;

        String group = " GROUP BY project_major ";
        StringBuffer whereCondition = new StringBuffer(" where is_active =1 ");

        if (criteria.getBeginTime() != null){
            whereCondition.append(" and contract_begin_time>= ");

            whereCondition.append(DateHelper.formatDate(criteria.getBeginTime(), "yyyyMMdd"));
            //whereCondition.append("'");
        }

        if (criteria.getEndTime() != null){
            whereCondition.append(" and contract_begin_time<= ");
            //whereCondition.append("'");
            whereCondition.append(DateHelper.formatDate(criteria.getEndTime(), "yyyyMMdd"));

        }

        sql= sql+whereCondition.toString()+group;

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<QuerySaleContractsBy_project_majorDto> result = dbSupport.queryForObjects(QuerySaleContractsBy_project_majorDto.class, query);

        return result;
    }

    @Override
    public List<QuerySaleContractsBy_yearDto> QuerySaleContractsBy_year(SaleContractCriteria criteria) {

        String sql="SELECT MONTH(contract_begin_time) month,sum(judge_money) sum_judge_money  " +
                "from ancon_sys_sale_contract  ";

        String group = " GROUP BY  MONTH(contract_begin_time) ";
        StringBuffer whereCondition = new StringBuffer(" where is_active =1 ");

        if (criteria.getYear() != null){
            whereCondition.append(" and YEAR(contract_begin_time)= '");
            whereCondition.append(criteria.getYear());
            whereCondition.append("'");
        }

        if (criteria.getCompanyId() != null){
            whereCondition.append(" and companyId= '");
            whereCondition.append(criteria.getCompanyId());
            whereCondition.append("'");
        }

        if (criteria.getJiaFangName() != null){
            whereCondition.append(" and jia_fang_name like '%");
            whereCondition.append(criteria.getJiaFangName());
            whereCondition.append("%'");
        }


        sql= sql+whereCondition.toString()+group;

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<QuerySaleContractsBy_yearDto> result = dbSupport.queryForObjects(QuerySaleContractsBy_yearDto.class, query);

        return result;
    }

    @Override
    public List<QuerySaleContractsBy_contractTypeDto> QuerySaleContractsBy_contractType(SaleContractCriteria criteria) {
        String sql="SELECT MONTH(contract_begin_time) month,sum(judge_money) sum_judge_money,contract_type " +
                "from ancon_sys_sale_contract ";

        String group = " GROUP BY month ,contract_type ";
        StringBuffer whereCondition = new StringBuffer(" where is_active =1 ");

        if (criteria.getBeginTime() != null){
            whereCondition.append(" and YEAR(contract_begin_time) = '");
            whereCondition.append(DateHelper.formatDate(criteria.getBeginTime(), "yyyy"));
            whereCondition.append("'");
        }

        


        sql= sql+whereCondition.toString()+group;

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<QuerySaleContractsBy_contractTypeDto> result = dbSupport.queryForObjects(QuerySaleContractsBy_contractTypeDto.class, query);

        return result;
    }
}
