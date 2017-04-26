package com.sunesoft.ancon.webapp.controller.saleContract;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.saleContract.application.BillingService;
import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.results.ListResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.model.SaleContractsTypeModel;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import com.sunesoft.ancon.webapp.utils.URI;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/6.
 */
@Controller
public class SaleContractEchartsController extends Layout {

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    SecondPartyService secondPartyService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    BillingService billingService;


    @RequestMapping(value = "ajax_query_echarts_saleContracts_x")
    @ResponseBody
    public void queryJudgeMoney(HttpServletRequest request, HttpServletResponse response) {
        String sale_countTime = request.getParameter("sale_countTime");
        String sale_branchCompany = request.getParameter("sale_branchCompany");
        String sale_jiafang = request.getParameter("sale_jiafang");
        SaleContractCriteria criteria = new SaleContractCriteria();
        if (!StringUtils.isNullOrWhiteSpace(sale_countTime))
            criteria.setYear(Integer.parseInt(sale_countTime));
        criteria.setBranchCompany(sale_branchCompany);
        criteria.setJiaFangName(sale_jiafang);
        List<Map<Integer, Object>> list = saleContractService.summeryJudgeMoney(criteria);
        AjaxResponse.write(response, JsonHelper.toJson(list));


    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts")
    @ResponseBody
    public SaleContractDto[] queryBySaleContract(HttpServletRequest request, SaleContractCriteria criteria) {
        String sale_countTime = request.getParameter("sale_countTime");
        String sale_branchCompany = request.getParameter("sale_branchCompany");
        String sale_jiafang = request.getParameter("sale_jiafang");


        if (!StringUtils.isNullOrWhiteSpace(sale_countTime)) {
            Integer endTime = Integer.parseInt(sale_countTime) + 1;

            criteria.setBeginTime(DateHelper.parse(sale_countTime, "yyyy"));
            criteria.setEndTime(DateHelper.parse(endTime.toString(), "yyyy"));
        }

        if (!StringUtils.isNullOrWhiteSpace(sale_branchCompany))
            criteria.setBranchCompany(sale_branchCompany);
        if (!StringUtils.isNullOrWhiteSpace(sale_jiafang))
            criteria.setJiaFangName(sale_jiafang);

        criteria.setPageSize(1000000000);
        PagedResult<SaleContractDto> pagedResult = saleContractService.findPagesBySaleContract_echarts(criteria);
        List<SaleContractDto> list = pagedResult.getItems();
        for (int i = 0; i < list.size(); i++) {
            Date time_1 = list.get(i).getContractBeginTime();
            int month_1 = time_1.getMonth() + 1;//判断月份
            int year_1 = time_1.getYear() + 1900;//判断年份
            BigDecimal totalMoney = list.get(i).getJudgeMoney();
            for (int j = 0; j < list.size(); j++) {
                Date time_2 = list.get(j).getContractBeginTime();
                int month_2 = time_2.getMonth() + 1;
                int year_2 = time_2.getYear() + 1900;
                if (month_1 == month_2 && year_1 == year_2 && time_1 != time_2) {
                    if (list.get(i).getJudgeMoney() != null && list.get(j).getJudgeMoney() != null) {

                        BigDecimal money_2 = list.get(j).getJudgeMoney();
                        totalMoney = totalMoney.add(money_2);
                        list.get(i).setJudgeMoney(totalMoney);
                        list.remove(j);
                        j--;       //移除完之后下标需要回到原来的位置   像这种条件判断的需要考虑全面
                    }
                }
            }
        }

        List<SaleContractDto> saleContractDtos = new ArrayList<>();
        Date singleSaleContractTime = null;
        int year = 0;
        if (list != null && list.size() > 0) {
            singleSaleContractTime = list.get(0).getContractBeginTime();
            year = singleSaleContractTime.getYear() + 1900;//当前所有销售合同的年份 一致

        }
        SaleContractDto[] currentDtos = new SaleContractDto[12];
        for (SaleContractDto dto : list) {
            Date time = dto.getContractBeginTime();
            int month = time.getMonth() + 1;

            currentDtos[month - 1] = dto;

        }

        for (int i = 0; i < currentDtos.length; i++) {
            if (currentDtos[i] == null) {
                SaleContractDto dto = new SaleContractDto();
                dto.setJudgeMoney(BigDecimal.ZERO);
                currentDtos[i] = dto;
            }
        }

        return currentDtos;
    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts_second")
    @ResponseBody
    public List<QuerySaleContractsBy_companyDto> queryData_second(HttpServletRequest request, SaleContractCriteria criteria) {
        String branchCompany_begin_time = request.getParameter("branchCompany_begin_time");
        String branchCompany_end_time = request.getParameter("branchCompany_end_time");

        if (!StringUtils.isNullOrWhiteSpace(branchCompany_begin_time))
            criteria.setBeginTime(DateHelper.parse(branchCompany_begin_time, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(branchCompany_end_time))
            criteria.setEndTime(DateHelper.parse(branchCompany_end_time,"yyyy-MM-dd"));

        criteria.setPageSize(1000000000);
        List<QuerySaleContractsBy_companyDto> list = billingService.querySaleContractsBy_company(criteria);


        return list;
    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts_third")
    @ResponseBody
    public SaleContractsTypeModel querySalType(HttpServletRequest request,SaleContractCriteria criteria){
        String begin_time=request.getParameter("sale_countTime");

        if (!StringUtils.isNullOrWhiteSpace(begin_time))
            criteria.setBeginTime(DateHelper.parse(begin_time, "yyyy"));

        criteria.setPageSize(1000000000);
        QuerySaleContractsBy_contractTypeDto[] array_common = new QuerySaleContractsBy_contractTypeDto[12];
        QuerySaleContractsBy_contractTypeDto[] array_important = new QuerySaleContractsBy_contractTypeDto[12];

        List<QuerySaleContractsBy_contractTypeDto> list=billingService.QuerySaleContractsBy_contractType(criteria);

        List<QuerySaleContractsBy_contractTypeDto> commonContracts = new ArrayList<>();
        List<QuerySaleContractsBy_contractTypeDto> importantContracts = new ArrayList<>();

        if (list != null && list.size()>0){
            for (QuerySaleContractsBy_contractTypeDto dto:list){
                if (dto.getContract_type().equals("标准合同")){
                    commonContracts.add(dto);
                }
                if (dto.getContract_type().equals("重点合同")){
                    importantContracts.add(dto);
                }
            }
        }

        if (commonContracts != null && commonContracts.size()>0) {
            for (QuerySaleContractsBy_contractTypeDto dto : commonContracts) {
                String month = dto.getMonth();
                Integer month_1 = Integer.parseInt(month);
                array_common[month_1 - 1] = dto;
            }
        }

        for (int i = 0; i < array_common.length; i++) {
            if (array_common[i] == null){
                QuerySaleContractsBy_contractTypeDto dto = new QuerySaleContractsBy_contractTypeDto();
                dto.setSum_judge_money(BigDecimal.ZERO);
                array_common[i] = dto;
            }
        }

        if (importantContracts != null && importantContracts.size()>0) {
            for (QuerySaleContractsBy_contractTypeDto dto : importantContracts) {
                String month = dto.getMonth();
                Integer month_1 = Integer.parseInt(month);
                array_important[month_1 - 1] = dto;
            }
        }

        for (int i = 0; i < array_important.length; i++) {
            if (array_important[i] == null){
                QuerySaleContractsBy_contractTypeDto dto = new QuerySaleContractsBy_contractTypeDto();
                dto.setSum_judge_money(BigDecimal.ZERO);
                array_important[i] = dto;
            }
        }

        SaleContractsTypeModel model = new SaleContractsTypeModel();
        model.setCommon(array_common);
        model.setImportant(array_important);

        return model;
    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts_third_f")
    @ResponseBody
    public List<SaleContractDto> queryData_third(HttpServletRequest request, SaleContractCriteria criteria) {
        String contractType_begin_time = request.getParameter("contractType_begin_time");
        String contractType_end_time = request.getParameter("contractType_end_time");

        Date currentTime = new Date();
        int currentYear = currentTime.getYear() + 1900;
        if (!StringUtils.isNullOrWhiteSpace(contractType_begin_time)) {
            criteria.setBeginTime(DateHelper.parse(contractType_begin_time, "yyyy-MM-dd"));
        } else {
            String beginTime = String.valueOf(currentYear) + "-01-01";
            criteria.setBeginTime(DateHelper.parse(beginTime, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(contractType_end_time)) {
            criteria.setEndTime(DateHelper.parse(contractType_end_time, "yyyy-MM-dd"));
        } else {
            String endTime = String.valueOf(currentYear) + "-12-31";
            criteria.setEndTime(DateHelper.parse(endTime, "yyyy-MM-dd"));
        }

        criteria.setPageSize(1000000000);
        PagedResult<SaleContractDto> pageResult = saleContractService.findPagesBySaleContract_echarts(criteria);
        List<SaleContractDto> allContracts = pageResult.getItems();

        List<SaleContractDto> standardContracts = new ArrayList<>();//标准合同
        List<SaleContractDto> importantContracts = new ArrayList<>();//重要合同
        for (SaleContractDto dto : allContracts) {
            if (dto.getContractType().equals("标准合同")) {
                standardContracts.add(dto);
            } else {
                importantContracts.add(dto);
            }
        }

        for (int i = 0; i < standardContracts.size(); i++) {
            Date beginTime_1 = standardContracts.get(i).getContractBeginTime();
            int month_1 = beginTime_1.getMonth() + 1;
            int year_1 = beginTime_1.getYear() + 1900;
            BigDecimal money_1 = standardContracts.get(i).getJudgeMoney();
            for (int j = 0; j < standardContracts.size(); j++) {
                Date beginTime_2 = standardContracts.get(j).getContractBeginTime();
                int month_2 = beginTime_2.getMonth() + 1;
                int year_2 = beginTime_2.getYear() + 1900;
                BigDecimal money_2 = standardContracts.get(j).getJudgeMoney();
                if (year_1 == year_2 && month_1 == month_2 && beginTime_1 != beginTime_2) {

                    money_1 = money_1.add(money_2);
                    standardContracts.get(i).setJudgeMoney(money_1);
                    standardContracts.remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < importantContracts.size(); i++) {
            Date beginTime_1 = importantContracts.get(i).getContractBeginTime();
            int month_1 = beginTime_1.getMonth() + 1;
            int year_1 = beginTime_1.getYear() + 1900;
            BigDecimal money_1 = importantContracts.get(i).getJudgeMoney();
            for (int j = 0; j < importantContracts.size(); j++) {
                Date beginTime_2 = importantContracts.get(j).getContractBeginTime();
                int month_2 = beginTime_2.getMonth() + 1;
                int year_2 = beginTime_2.getYear() + 1900;
                BigDecimal money_2 = importantContracts.get(j).getJudgeMoney();
                if (year_1 == year_2 && month_1 == month_2 && beginTime_1 != beginTime_2) {
                    if (money_1 != null && money_2 != null) {
                        money_1 = money_1.add(money_2);
                        importantContracts.get(i).setJudgeMoney(money_1);
                    }
                    importantContracts.remove(j);
                    j--;
                }
            }
        }

        SaleContractDto[] array_standard = new SaleContractDto[12];
        SaleContractDto[] array_important = new SaleContractDto[12];

        for (SaleContractDto dto : standardContracts) {
            Date beginTime = dto.getContractBeginTime();
            int month = beginTime.getMonth() + 1;
            array_standard[month - 1] = dto;
        }

        for (SaleContractDto dto : importantContracts) {
            Date beginTime = dto.getContractBeginTime();
            int month = beginTime.getMonth() + 1;
            array_important[month - 1] = dto;
        }

        for (int i = 0; i < array_standard.length; i++) {
            if (array_standard[i] == null) {
                SaleContractDto dto = new SaleContractDto();
                dto.setJudgeMoney(BigDecimal.ZERO);
                array_standard[i] = dto;
            }
        }

        for (int i = 0; i < array_important.length; i++) {
            if (array_important[i] == null) {
                SaleContractDto dto = new SaleContractDto();
                dto.setJudgeMoney(BigDecimal.ZERO);
                array_important[i] = dto;
            }
        }

        List arrayList = new ArrayList();
        arrayList.add(array_standard);
        arrayList.add(array_important);
        return arrayList;
    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts_fourth")
    @ResponseBody
    public List<QuerySaleContractsBy_project_majorDto> queryData_fourth(HttpServletRequest request, SaleContractCriteria criteria) {
        String projectMajor_begin_time = request.getParameter("projectMajor_begin_time");
        String projectMajor_end_time = request.getParameter("projectMajor_end_time");

        if (!StringUtils.isNullOrWhiteSpace(projectMajor_begin_time)) {
            criteria.setBeginTime(DateHelper.parse(projectMajor_begin_time, "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(projectMajor_end_time)) {
            criteria.setEndTime(DateHelper.parse(projectMajor_end_time, "yyyy-MM-dd"));
        }

        criteria.setPageSize(1000000000);
        List<QuerySaleContractsBy_project_majorDto> list=billingService.QuerySaleContractsBy_project_major(criteria);

        return list;
    }

    @RequestMapping(value = "ajax_query_echarts_saleContracts_first")
    @ResponseBody
    public QuerySaleContractsBy_yearDto[] queryData_first(HttpServletRequest request,SaleContractCriteria criteria){
        String sale_countTime = request.getParameter("sale_countTime");
        String sale_branchCompany = request.getParameter("sale_branchCompany");//已经改为分公司id
        String sale_jiafang = request.getParameter("sale_jiafang");

        if (!StringUtils.isNullOrWhiteSpace(sale_countTime))
            criteria.setYear(Integer.parseInt(sale_countTime));
        if (!StringUtils.isNullOrWhiteSpace(sale_branchCompany))
            criteria.setCompanyId(Long.parseLong(sale_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(sale_jiafang))
            criteria.setJiaFangName(URI.deURI(sale_jiafang));

        criteria.setPageSize(1000000000);

        QuerySaleContractsBy_yearDto[] array = new QuerySaleContractsBy_yearDto[12];

        List<QuerySaleContractsBy_yearDto> list=billingService.QuerySaleContractsBy_year(criteria);

        for (QuerySaleContractsBy_yearDto dto:list){
            Long month = dto.getMonth();
            String month_1 = month.toString();
            Integer month_2 = Integer.parseInt(month_1);
            array[month_2-1] = dto;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null){
                QuerySaleContractsBy_yearDto dto = new QuerySaleContractsBy_yearDto();
                dto.setSum_judge_money(BigDecimal.ZERO);
                array[i] = dto;
            }
        }

        return array;
    }

}
