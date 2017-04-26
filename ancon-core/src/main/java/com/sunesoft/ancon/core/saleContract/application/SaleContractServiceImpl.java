package com.sunesoft.ancon.core.saleContract.application;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.saleContract.domain.SaleContract;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.results.ResultFactory;
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

import java.io.Console;
import java.util.*;

/**
 * Created by admin on 2016/11/24.
 */
@Service("saleContractService")
public class SaleContractServiceImpl implements SaleContractService {

    @Autowired
    AnconDbSupport dbSupport;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    SecondPartyService secondPartyService;

    @Override
    public CommonResult addSaleContract(SaleContractDto dto) {
        SaleContract sale = new SaleContract();
        SaleContract contract = new SaleContract();

        if (dto.getParentId() != null) {
            sale.setId(dto.getParentId());
            contract.setParentContract(sale);
        }


        //判断合同编号是否重复
//        List<SaleContractDto> list = this.getAll();
//        for (SaleContractDto saleContractDto : list) {
//            if (dto.getNum().equals(saleContractDto.getNum())) {
//                return ResultFactory.commonError("该合同编号已存在，添加失败！");
//            }
//        }
        SaleContract saleCon = this.getBySaleContractNum(dto.getNum());
        if (saleCon != null)
            return ResultFactory.commonError("该合同编号已存在，添加失败！");

        SaleContract saleContract = DtoFactory.convert(dto, contract);
        Long id = (Long) dbSupport.getSaleContract().add(saleContract);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public boolean deleteSaleContract(Long[] id) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        if (id != null && id.length > 0) {
            criteria.add(Restrictions.in("id", id));
        }
        List<SaleContract> list = criteria.list();
        for (SaleContract sale : list) {
            sale.setIsActive(false);
            dbSupport.getSaleContract().update(sale);
        }
        return true;
    }

    @Override
    public CommonResult updateSaleContract(SaleContractDto dto) {

        SaleContract contract = this.getBySaleContractNum(dto.getNum());
        SaleContractDto contractDto = this.getById(dto.getId());//原来的数椐   自身
        if (contract != null && !dto.getNum().equals(contractDto.getNum()))
            return ResultFactory.commonError("该合同编号已存在，更新失败！");

        SaleContract saleContract = dbSupport.getSaleContract().get(dto.getId());

        //判断合同编号是否重复
//        List<SaleContractDto> list = this.getAll();
//        for (SaleContractDto saleContractDto : list) {
//            //去掉自身
//            if (dto.getNum().equals(saleContractDto.getNum()) && saleContractDto.getId() != dto.getId()) {
//                return ResultFactory.commonError("该合同编号已存在，添加失败！");
//            }
//        }


        saleContract.setName(dto.getName());
        saleContract.setNum(dto.getNum());//合同编号不能修改
        saleContract.setJiaFangName(dto.getJiaFangName());
        saleContract.setContractMoney(dto.getContractMoney());
        saleContract.setProjectMajor(dto.getProjectMajor());
        saleContract.setContractBeginTime(dto.getContractBeginTime());
        saleContract.setContractEndTime(dto.getContractEndTime());
        saleContract.setProjectStartTime(dto.getProjectStartTime());
        saleContract.setContractStatus(dto.getContractStatus());
        saleContract.setContractType(dto.getContractType());
        saleContract.setBidNotice(dto.getBidNotice());
        saleContract.setConstructLicense(dto.getConstructLicense());
        saleContract.setProjectSettlement(dto.getProjectSettlement());
        saleContract.setFinishCheck(dto.getFinishCheck());
        saleContract.setRemark(dto.getRemark());
        saleContract.setJudgeMoney(dto.getJudgeMoney());
        saleContract.setJudgeTime(dto.getJudgeTime());
        saleContract.setJudgeStatus(dto.getJudgeStatus());
        saleContract.setContractIsReturn(dto.getContractIsReturn());
        saleContract.setBranchCompany(dto.getBranchCompany());
        saleContract.setCompanyId(dto.getCompanyId());

        dbSupport.getSaleContract().update(saleContract);
        return ResultFactory.commonSuccess(dto.getId());
    }

    @Override
    public List<SaleContractDto> getAll() {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("hasParent",false));
        List<SaleContract> saleContracts = criteria.list();
        List<SaleContractDto> list = new ArrayList<>();
        for (SaleContract sale : saleContracts) {
            SaleContractDto dto = new SaleContractDto();
            if (sale.getParentContract() != null)
                dto.setParentId(sale.getParentContract().getId());

            List<SaleContractDto> childDto = new ArrayList<>();
            if (sale.getChildContract() != null && sale.getChildContract().size() > 0) {
                for (SaleContract con : sale.getChildContract()) {
                    childDto.add(DtoFactory.convert(con, new SaleContractDto()));
                }
            }
            dto.setChildContractDto(childDto);

            SaleContractDto dto_1 = DtoFactory.convert(sale, dto);
            list.add(dto_1);
        }


        return list;
    }

    @Override
    public List<InSimpleSaleContractDto> getInSimpleAll() {
        String hql = " select new com.sunesoft.ancon.core.saleContract.application.dtos.InSimpleSaleContractDto(s.id,s.name,s.companyId)  from SaleContract as s where s.isActive=1";
        Query query = dbSupport.getSession().createQuery(hql);
        List<InSimpleSaleContractDto> list = query.list();
        return list == null ? Collections.EMPTY_LIST : list;
    }

    @Override
    public List<SaleContractSimpleDto> getSimpleAll() {
//        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
//        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("hasParent",false));
//        List<SaleContract> saleContracts = criteria.list();
        SQLQuery query = dbSupport.getSession().createSQLQuery("select id,contract_type as contractType,contract_status as contractStatus,num name,parent_contract_id as parentId,companyId  from  ancon_sys_sale_contract");
        List<SaleContractSimpleDto> result = dbSupport.queryForObjects(SaleContractSimpleDto.class, query);
        return result;
//        for(Object contr : objLink)
//        {
//
//        }
//        List<SaleContractSimpleDto> list = new ArrayList<>();
//        for (SaleContract sale : saleContracts) {
//            SaleContractSimpleDto dto = new SaleContractSimpleDto();
//            if (sale.getParentId() != null)
//                dto.setParentId(sale.getParentId());
//
//            SaleContractSimpleDto dto_1 = DtoFactory.convert(sale, dto);
//            list.add(dto_1);
//        }
//        return list;
    }


    @Override
    public SaleContractDto getById(Long id) {
        SaleContract saleContract = dbSupport.getSaleContract().get(id);
        List<SaleContractDto> childDtos = new ArrayList<>();//子集
        SaleContract parentContract = null;//父集
        if (saleContract.getChildContract() != null && saleContract.getChildContract().size() > 0) {
            List<SaleContract> childList = saleContract.getChildContract();
            for (SaleContract sale : childList) {
                SaleContractDto childrenDto = DtoFactory.convert(sale, new SaleContractDto());
                childDtos.add(childrenDto);
            }
        }
        if (saleContract.getParentContract() != null) {
            parentContract = saleContract.getParentContract();
        }


        SaleContractDto dto = new SaleContractDto();
        if (childDtos != null && childDtos.size() > 0)
            dto.setChildContractDto(childDtos);
        if (parentContract != null)
            dto.setParentContractDto(DtoFactory.convert(parentContract, new SaleContractDto()));

        SaleContractDto lastDto = DtoFactory.convert(saleContract, dto);
        return lastDto;
    }

    @Override
    public SaleContractDto getByNum(String num) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("num", num));
        List<SaleContract> list = criteria.list();
        if (list != null && list.size() > 0) return DtoFactory.convert(list.get(0), new SaleContractDto());
        return null;
    }

    @Override
    public PagedResult<SaleContractDtoModel> findPages(SaleContractCriteria contractCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);

        //sql拼接
        String querySql = "select y.*,sum(c.output_value) sum_output_value from (select x.*,sum(g.gathering_money) sum_gathering_money      " +
                "      from (select  s.*,sum(b.price_tax_count_money) sum_billing_money          " +
                "          from    ancon_sys_sale_contract s left join ancon_sys_billing b  on b.sale_contract_id=s.id             " +
                "       and  b.is_active=1  group by id) x       " +
                "     left join ancon_sys_gathering_management g on x.id=g.sale_contract_id and g.is_active=1   group by x.id) y " +
                " left join ancon_sys_construction_value c on y.id=c.sale_contract_id AND c.is_active=1 ";

        String countSql = "select count(*) from ancon_sys_sale_contract y";

        String group = " GROUP BY y.id  ";

        String order = " order by y.contract_begin_time desc ";

        StringBuffer whereCondition = new StringBuffer(" where y.is_active =1 ");
//        criteria.add(Restrictions.eq("isActive",true));

        if (contractCriteria.getBeginTime() != null) {
            whereCondition.append(" and y.contract_begin_time >");
            //whereCondition.append("'");
            whereCondition.append(DateHelper.formatDate(contractCriteria.getBeginTime(), "yyyyMMdd"));
            //whereCondition.append("'");
        }
        if (contractCriteria.getEndTime() != null) {
            whereCondition.append(" and y.contract_begin_time <");
            //whereCondition.append("'");
            whereCondition.append(DateHelper.formatDate(contractCriteria.getEndTime(), "yyyyMMdd"));
            //  whereCondition.append("'");
        }

        if (contractCriteria.getCompanyId() != null) {
            whereCondition.append(" and y.companyId = ");
            whereCondition.append("'");
            whereCondition.append(contractCriteria.getCompanyId());
            whereCondition.append("'");
        }

        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getJiaFangName())) {

            whereCondition.append(" and y.jia_fang_name like ");
            whereCondition.append("'%");
            whereCondition.append(contractCriteria.getJiaFangName().trim());
            whereCondition.append("%'");
        }

        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getContractType())) {

            whereCondition.append(" and y.contract_type like ");
            whereCondition.append("'%");
            whereCondition.append(contractCriteria.getContractType().trim());
            whereCondition.append("%'");

        }
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getProjectMajor())) {

            whereCondition.append(" and y.project_major like ");
            whereCondition.append("'%");
            whereCondition.append(contractCriteria.getProjectMajor().trim());
            whereCondition.append("%'");

        }
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getContractStatus())) {

            whereCondition.append(" and y.contract_status like ");
            whereCondition.append("'%");
            whereCondition.append(contractCriteria.getContractStatus().trim());
            whereCondition.append("%'");

        }


        //四个排序
        //中标通知书
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getBidNotice()) || !StringUtils.isNullOrWhiteSpace(contractCriteria.getConstructLicense()) ||
                !StringUtils.isNullOrWhiteSpace(contractCriteria.getFinishCheck()) || !StringUtils.isNullOrWhiteSpace(contractCriteria.getProjectSettlement())
                || !StringUtils.isNullOrWhiteSpace(contractCriteria.getContractIsReturn()) || !StringUtils.isNullOrWhiteSpace(contractCriteria.getNumber())) {
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getBidNotice())) {
                if (contractCriteria.getBidNotice().equals("a_bidNotice")) {
                    order = " order by y.bid_notice ASC ";//顺序
                } else {
                    order = " order by y.bid_notice DESC ";//倒序
                }
            }

            //施工许可证 CALL usp_dynamic_total_report();
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getConstructLicense())) {
                if (contractCriteria.getConstructLicense().equals("a_constrctLicense")) {
                    order = " order by y.construct_license ASC ";
                } else {
                    order = " order by y.construct_license DESC ";
                }
            }

            //竣工验收单
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getFinishCheck())) {
                if (contractCriteria.getFinishCheck().equals("a_finishCheck")) {
                    order = " order by y.finish_check ASC ";
                } else {
                    order = " order by y.finish_check DESC ";
                }
            }

            //工程结算单
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getProjectSettlement())) {
                if (contractCriteria.getProjectSettlement().equals("a_projectSettlement")) {
                    order = " order by y.project_settlement ASC ";
                } else {
                    order = " order by y.project_settlement DESC ";
                }
            }

            //合同是否有返回
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getContractIsReturn())) {
                if (contractCriteria.getContractIsReturn().equals("a_contractIsReturn")) {
                    order = " order by y.contract_is_return ASC ";
                } else {
                    order = " order by y.contract_is_return DESC ";
                }
            }

            //合同编号
            if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getNumber())) {
                if (contractCriteria.getNumber().equals("a_number")) {
                    order = " order by y.num ASC ";
                } else {
                    order = " order by y.num DESC ";
                }
            }
        } else {
            order = " order by y.contract_begin_time DESC ";
        }


        querySql += whereCondition.toString() + group + order;
        //获取总记录数
        Long totalCount = dbSupport.getCountBySql(countSql + whereCondition.toString(), null);


//        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
//        criteria.setProjection(null);

        SQLQuery query = dbSupport.getSession().createSQLQuery(querySql);

        query.setFirstResult((contractCriteria.getPageNumber() - 1) * contractCriteria.getPageSize()).setMaxResults(contractCriteria.getPageSize());
        //转换成指定对象结果集
        List<SaleContractDtoModel> result = dbSupport.queryForObjects(SaleContractDtoModel.class, query);

        for (SaleContractDtoModel dto : result) {
            //剩余天数计算 合同到期时间-当前时间
            Date beginTime = new Date();
            Date endTime = dto.getContract_end_time();
            long intervalMilli = endTime.getTime() - beginTime.getTime();

            int days = (int) (intervalMilli / (24 * 60 * 60 * 1000));
            dto.setSurplus_day(days);
        }


//        List<Map<String,Object>> result =query.list();

        return new PagedResult<>(result, contractCriteria.getPageNumber(), contractCriteria.getPageSize(), totalCount.intValue());
    }

    public PagedResult<SaleContractDto> findPagesBySaleContract_echarts(SaleContractCriteria contractCriteria) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (contractCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("contractBeginTime", contractCriteria.getBeginTime()));
        if (contractCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("contractBeginTime", contractCriteria.getEndTime()));
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getBranchCompany()))
            criteria.add(Restrictions.eq("branchCompany", contractCriteria.getBranchCompany()));
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getJiaFangName()))
            criteria.add(Restrictions.eq("jiaFangName", contractCriteria.getJiaFangName()));
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getContractType()))
            criteria.add(Restrictions.eq("contractType", contractCriteria.getContractType()));
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getProjectMajor()))
            criteria.add(Restrictions.eq("projectMajor", contractCriteria.getProjectMajor()));
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getContractStatus()))
            criteria.add(Restrictions.eq("contractStatus", contractCriteria.getContractStatus()));

        //获取总记录数
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);

        criteria.addOrder(Order.asc("contractBeginTime"));

        criteria.setFirstResult((contractCriteria.getPageNumber() - 1) * contractCriteria.getPageSize()).setMaxResults(contractCriteria.getPageSize());
        List<SaleContract> saleContracts = criteria.list();
        List<SaleContractDto> list = new ArrayList<>();
        for (SaleContract sale : saleContracts) {
            SaleContractDto dto = DtoFactory.convert(sale, new SaleContractDto());
            list.add(dto);
        }
        return new PagedResult<SaleContractDto>(list, contractCriteria.getPageNumber(), contractCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<SaleContractDto> getByCompanyId(Long companyId) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("companyId", companyId));
        List<SaleContract> saleContracts = criteria.list();
        List<SaleContractDto> list = new ArrayList<>();
        for (SaleContract sale : saleContracts) {
            SaleContractDto dto = DtoFactory.convert(sale, new SaleContractDto());
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getSaleContractsCountData(String year, String branchCompany, String jiafangName) {

        Date date = new Date();
        String current_year = DateHelper.formatDate(date, "yyyy");
        String year_start = current_year + "0101";
        String year_end = current_year + "1231";
        String hql = "select MONTH(contract_begin_time) sale_month,sum(judge_money) judge_money  " +
                "from ancon_sys_sale_contract where is_active=1 AND  contract_begin_time BETWEEN '" + year_start + "' AND '" + year_end + "' GROUP BY sale_month  ";

        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();

        return result;
    }

    @Override
    public List<Map<String, Object>> getSaleContracts_count_byNumber() {

        Date date = new Date();
        String current_year = DateHelper.formatDate(date, "yyyy");
        String year_start = current_year + "0101";
        String year_end = current_year + "1231";

        String hql = "select MONTH(contract_begin_time) sale_month,count(*) count  from ancon_sys_sale_contract" +
                "  where  is_active=1 AND contract_begin_time BETWEEN '" + year_start + "' AND '" + year_end + "' GROUP BY sale_month ;";

        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    @Override
    public List<Map<String, Object>> getBilling_count() {

        Date date = new Date();
        String current_year = DateHelper.formatDate(date, "yyyy");
        String year_start = current_year + "0101";
        String year_end = current_year + "1231";

        String hql = "select MONTH(billing_date) month,sum(price_tax_count_money) money FROM ancon_sys_billing " +
                "where is_active=1 AND billing_date BETWEEN '" + year_start + "' AND '" + year_end + "' GROUP BY month";
        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    @Override
    public List<Map<String, Object>> getGathering_count() {
        Date date = new Date();
        String current_year = DateHelper.formatDate(date, "yyyy");
        String year_start = current_year + "0101";
        String year_end = current_year + "1231";

        String hql = "select MONTH(gathering_time) month,sum(gathering_money) money FROM ancon_sys_gathering_management " +
                "where  is_active=1 AND gathering_time BETWEEN '" + year_start + "' AND '" + year_end + "' GROUP BY month";

        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    @Override
    public List<Map<String, Object>> getConValue_count() {

        Date date = new Date();
        String current_year = DateHelper.formatDate(date, "yyyy");
        String year_start = current_year + "0101";
        String year_end = current_year + "1231";

        String hql = "select MONTH(write_date) month,sum(output_value) money FROM ancon_sys_construction_value " +
                "where  is_active=1 AND write_date BETWEEN '" + year_start + "' AND '" + year_end + "' GROUP BY month";

        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    @Override
    public List<Map<String, Object>> getAllCount_biilingAndGatheringAndConValue(Long saleContractId) {

        String hql = "";
        if (saleContractId != null) {
            hql = "select y.*,sum(c.output_value) sum_conValue_money from (select x.*,sum(g.gathering_money) sum_gathering_money      " +
                    "      from (select  s.id id,b.sale_contract_id sale_contract_id, s.judge_money,sum(b.price_tax_count_money) sum_billing_money          " +
                    "          from    ancon_sys_sale_contract s left join ancon_sys_billing b  on b.sale_contract_id=s.id             " +
                    "       and  b.is_active=1  group by id) x        " +
                    "     left join ancon_sys_gathering_management g on x.id=g.sale_contract_id and g.is_active=1   group by x.id) y " +
                    " left join ancon_sys_construction_value c on y.id=c.sale_contract_id AND c.is_active=1" +
                    " where y.id='" + saleContractId + "'  " +
                    "group by y.id  ";
        }

        Query query = dbSupport.getSession().createSQLQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> result = query.list();
        return result;
    }

    @Override
    public List<DynamicTotalReportFormDto> getDynamicTotalReportForm() {

        String sql = "CALL usp_dynamic_total_report();";

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<DynamicTotalReportFormDto> result = dbSupport.queryForObjects(DynamicTotalReportFormDto.class, query);


        return result;
    }

    @Override
    public List<ContractReportFormDto> getReportFormByBranchCompany(SaleContractCriteria contractCriteria) {

        SQLQuery query = null;
        //时间查询

        //sql拼接
        //开始sql
        String querySql =
                "select a.companyId,a.branch_company,  " +
                        "a.sum_judge_money sum_year_judge_money, " +
                        "b.sum_billing_money, " +
                        "c.sum_gathering_money, " +
                        "d.sum_output_value from  " +
                        "(select s.companyId,s.branch_company, " +
                        "sum(s.judge_money) sum_judge_money  " +
                        "from ancon_sys_sale_contract s ";

        //开票 sql
        String querySql_2 = " group by s.companyId  )a left join ( " +
                "select  s.companyId,sum(b.price_tax_count_money) sum_billing_money from ancon_sys_sale_contract s join ancon_sys_billing b on s.id=b.sale_contract_id ";

        //收款 sql
        String querySql_3 = " GROUP BY s.companyId " +
                ") b  on a.companyId = b.companyId " +
                "left join ( " +
                "select s.companyId,sum(g.gathering_money) sum_gathering_money from ancon_sys_sale_contract s join ancon_sys_gathering_management g on s.id=g.sale_contract_id ";

        //施工产值 sql
        String querySql_4 = " GROUP BY s.companyId " +
                " ) c  on a.companyId = c.companyId " +
                "left join ( " +
                "select s.companyId,sum(c.output_value) sum_output_value from ancon_sys_sale_contract s join ancon_sys_construction_value c on s.id = c.sale_contract_id ";

        //结束 sql
        String querySql_5 = " GROUP BY s.companyId ) d  on a.companyId = d.companyId ";


        StringBuffer whereCondition = new StringBuffer(" where s.is_active =1  ");//合同条件


        if (contractCriteria.getCompanyId() != null) {
            whereCondition.append(" and s.companyId = '");
            whereCondition.append(contractCriteria.getCompanyId());
            whereCondition.append("'");
        }

        if (contractCriteria.getContractType() != null) {
            whereCondition.append(" and s.contract_type like '%");
            whereCondition.append(contractCriteria.getContractType());
            whereCondition.append("%'");
        }
        if (contractCriteria.getJiaFangName() != null) {
            whereCondition.append(" and s.jia_fang_name like '%");
            whereCondition.append(contractCriteria.getJiaFangName());
            whereCondition.append("%'");
        }

        String timeCondition_1 = "";//收款条件

        String timeCondition_2 = "";//开票条件
        String timeCondition_3 = "";//收款条件
        String timeCondition_4 = "";//施工产值条件

        if (contractCriteria.getBeginTime() != null) {

            timeCondition_1 += " and s.contract_begin_time >=" + DateHelper.formatDate(contractCriteria.getBeginTime(), "yyyyMMdd");//收款条件

            timeCondition_2 += " and b.billing_date >=" + DateHelper.formatDate(contractCriteria.getBeginTime(), "yyyyMMdd");//开票条件
            timeCondition_3 += " and g.gathering_time >=" + DateHelper.formatDate(contractCriteria.getBeginTime(), "yyyyMMdd");//收款条件
            timeCondition_4 += " and c.write_date >=" + DateHelper.formatDate(contractCriteria.getBeginTime(), "yyyyMMdd");//施工产值条件

        }
        if (contractCriteria.getEndTime() != null) {
            timeCondition_1 += " and s.contract_begin_time <=" + DateHelper.formatDate(contractCriteria.getEndTime(), "yyyyMMdd");//收款条件

            timeCondition_2 += " and b.billing_date <=" + DateHelper.formatDate(contractCriteria.getEndTime(), "yyyyMMdd");//开票条件
            timeCondition_3 += " and g.gathering_time <=" + DateHelper.formatDate(contractCriteria.getEndTime(), "yyyyMMdd");//收款条件
            timeCondition_4 += " and c.write_date <=" + DateHelper.formatDate(contractCriteria.getEndTime(), "yyyyMMdd");//施工产值条件

        }


        querySql = querySql + whereCondition + timeCondition_1 + querySql_2 + whereCondition + timeCondition_2 + querySql_3 + whereCondition + timeCondition_3 + querySql_4 + whereCondition + timeCondition_4 + querySql_5;

        query = dbSupport.getSession().createSQLQuery(querySql);


        List<ContractReportFormDto> result = dbSupport.queryForObjects(ContractReportFormDto.class, query);

        return result;
    }

    @Override
    public List<IndexContractsTotalCountDto> getContractsTotalByMonthAndYear() {

        String sql = "select t.*,y.* from " +
                "(select sum_m_judge_money sum_month_judge_money, " +
                "a.sum_judge_money sum_year_judge_money  from  " +
                "(select sum(s.judge_money) sum_judge_money  " +
                "from ancon_sys_sale_contract s where YEAR(s.contract_begin_time)=YEAR(NOW())  )a, " +
                "(select sum(s.judge_money) sum_m_judge_money  " +
                "from ancon_sys_sale_contract s where YEAR(s.contract_begin_time)=YEAR(NOW()) and MONTH(s.contract_begin_time)=MONTH(NOW())  )ma )t ,(  " +
                "select  sum_m_judge_money sum_month_past_judge_money, " +
                "a.sum_judge_money sum_year_past_judge_money from " +
                "(select sum(s.judge_money) sum_judge_money  " +
                "from ancon_sys_sale_contract s where YEAR(s.contract_begin_time)=YEAR(NOW())-1 )a , " +
                "(select sum(s.judge_money) sum_m_judge_money  " +
                "from ancon_sys_sale_contract s where YEAR(s.contract_begin_time)=YEAR(NOW())-1 and MONTH(s.contract_begin_time)=MONTH(NOW()) )ma )y; ";

        SQLQuery query = dbSupport.getSession().createSQLQuery(sql);
        List<IndexContractsTotalCountDto> result = dbSupport.queryForObjects(IndexContractsTotalCountDto.class, query);


        return result;
    }

    @Override
    public List<Map<Integer, Object>> summeryJudgeMoney(SaleContractCriteria contractCriteria) {
        String sql = " SELECT SUM(IFNULL(s.judge_money,0)) money, DATE_FORMAT(s.contract_begin_time,'%m') mon FROM ancon_sys_sale_contract s WHERE ";

        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getBranchCompany())) {
            sql = sql + " '" + contractCriteria.getBranchCompany() + "'AND ";
        }
        if (!StringUtils.isNullOrWhiteSpace(contractCriteria.getJiaFangName())) {
            sql = sql + " '" + contractCriteria.getJiaFangName() + "'AND ";
        }
        if (contractCriteria.getYear() != null) {
            sql = sql + " DATE_FORMAT(s.contract_begin_time,'%Y')= '" + contractCriteria.getYear() + "' AND";
        }
        int i = sql.lastIndexOf("AND");
        if (i != -1) {
            sql = sql.substring(0, i);

        } else {
            int d = sql.indexOf("WHERE");
            sql = sql.substring(0, d);
        }
        sql = sql + "   GROUP BY mon";

        Query query = dbSupport.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<Integer, Object>> summerysalType(String begin, String end) {
//        String sql = " SELECT  SUM(s.judge_money) v, DATE_FORMAT(s.contract_begin_time,'%m') m , s.contract_type t  FROM ancon_sys_sale_contract s  ";
        String sql = " SELECT SUM(if(s.contract_type='标准合同',s.judge_money,0)) b  , SUM(if(s.contract_type='重点合同',s.judge_money,0))  z  ,  DATE_FORMAT(s.contract_begin_time,'%m') m  FROM ancon_sys_sale_contract s  WHERE  ";
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            sql = sql + " s.contract_begin_time < '" + begin + "' AND ";

        }
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            sql = sql + " s.contract_begin_time > '" + end + "' AND ";
        }
        int i = sql.lastIndexOf("AND");
        if (i != -1) {
            sql = sql.substring(0, i);

        } else {
            int d = sql.indexOf("WHERE");
            sql = sql.substring(0, d);
        }

        sql = sql + "   GROUP BY m ";

        Query query = dbSupport.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);


        return query.list();
    }

    @Override
    public CommonResult editUploadDtoInfo(SaleContractDto dto) {

//        SaleContract saleContract = DtoFactory.convert(dto, new SaleContract());
        CommonResult commonResult = null;

        //判断分公司
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDeptCommpany();
        Boolean isName = false;
        for (DeptmentDto deptmentDto : deptmentDtos) {
            isName = dto.getBranchCompany().equals(deptmentDto.getDeptName());
            if (dto.getBranchCompany().equals(deptmentDto.getDeptName())) {
                dto.setCompanyId(deptmentDto.getId());
                break;
            }
        }
        if (!isName) {
            System.out.println(dto.getBranchCompany() + ",名称不正确，请重新填写！");
            return ResultFactory.commonError(dto.getBranchCompany() + ",名称不正确，请重新填写！");
        }

        //判断甲方名称
        List<ContractorPartyDto> contractorPartyDtos = secondPartyService.getByTypeAll(2);
        Boolean isExist = false;
        if (dto.getJiaFangName() == null || dto.getJiaFangName().equals("")) {
            System.out.println("合同名称：" + dto.getName() + "\n" + "问题：无甲方单位！");
            return ResultFactory.commonError("合同名称：" + dto.getName() + "\n" + "问题：无甲方单位！");
        }
        for (ContractorPartyDto partyDto : contractorPartyDtos) {
            isExist = dto.getJiaFangName().equals(partyDto.getName());
            if (isExist)
                break;
        }
        if (!isExist) {
            ContractorPartyDto contractorPartyDto = new ContractorPartyDto();
            contractorPartyDto.setName(dto.getJiaFangName());
            contractorPartyDto.setType(2);
            secondPartyService.addFirstParty(contractorPartyDto);
        }

        //判断合同编号是否重复
//        List<SaleContractDto> list = this.getAll();
//        Boolean numIsExist = false;
//        for (SaleContractDto saleContractDto : list) {
//            numIsExist = dto.getNum().equals(saleContractDto.getNum());
//            if (numIsExist) {
//                dto.setId(saleContractDto.getId());
//                commonResult=this.updateSaleContract(dto);
//                break;
//            }
//        }
//        if (!numIsExist) {
//            commonResult=this.addSaleContract(dto);
//        }

        SaleContract contract = this.getBySaleContractNum(dto.getNum());
        if (contract != null)
            commonResult = this.updateSaleContractByNum(dto);
        else
            commonResult = this.addSaleContract(dto);

        System.out.println("合同编号：" + dto.getNum() + "录入成功！");
        return commonResult;
    }

    @Override
    public SaleContract getBySaleContractNum(String number) {
        Criteria criteria = dbSupport.getSession().createCriteria(SaleContract.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("num", number));
        SaleContract saleContract = (SaleContract) criteria.uniqueResult();
        return saleContract;
    }

    @Override
    public CommonResult updateSaleContractByNum(SaleContractDto dto) {


        SaleContract saleContract = this.getBySaleContractNum(dto.getNum());

        //判断合同编号是否重复
//        List<SaleContractDto> list = this.getAll();
//        for (SaleContractDto saleContractDto : list) {
//            //去掉自身
//            if (dto.getNum().equals(saleContractDto.getNum()) && saleContractDto.getId() != dto.getId()) {
//                return ResultFactory.commonError("该合同编号已存在，添加失败！");
//            }
//        }


        saleContract.setName(dto.getName());
        saleContract.setNum(dto.getNum());//合同编号不能修改
        saleContract.setJiaFangName(dto.getJiaFangName());
        saleContract.setContractMoney(dto.getContractMoney());
        saleContract.setProjectMajor(dto.getProjectMajor());
        saleContract.setContractBeginTime(dto.getContractBeginTime());
        saleContract.setContractEndTime(dto.getContractEndTime());
        saleContract.setProjectStartTime(dto.getProjectStartTime());
        saleContract.setContractStatus(dto.getContractStatus());
        saleContract.setContractType(dto.getContractType());
        saleContract.setBidNotice(dto.getBidNotice());
        saleContract.setConstructLicense(dto.getConstructLicense());
        saleContract.setProjectSettlement(dto.getProjectSettlement());
        saleContract.setFinishCheck(dto.getFinishCheck());
        saleContract.setRemark(dto.getRemark());
        saleContract.setJudgeMoney(dto.getJudgeMoney());
        saleContract.setJudgeTime(dto.getJudgeTime());
        saleContract.setJudgeStatus(dto.getJudgeStatus());
        saleContract.setContractIsReturn(dto.getContractIsReturn());
        saleContract.setBranchCompany(dto.getBranchCompany());
        saleContract.setCompanyId(dto.getCompanyId());

//        SaleContract sale = DtoFactory.convert(saleContract,new SaleContract());
//        dbSupport.getSession().evict(saleContract);
        dbSupport.getSaleContract().update(saleContract);
        return ResultFactory.commonSuccess(saleContract.getId());
    }


}
