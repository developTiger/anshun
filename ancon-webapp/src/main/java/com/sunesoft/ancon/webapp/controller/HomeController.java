package com.sunesoft.ancon.webapp.controller;

import com.sun.javafx.collections.MappingChange;
import com.sunesoft.ancon.core.saleContract.application.BillingService;
import com.sunesoft.ancon.core.saleContract.application.ConstructionValueService;
import com.sunesoft.ancon.core.saleContract.application.GatheringService;
import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.saleContract.domain.Gathering;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.UserAuthService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.ListResult;
import com.sunesoft.ancon.webapp.function.ResouceFactory;
import org.hibernate.mapping.*;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/26.
 */
@Controller
public class HomeController extends Layout {

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    ResouceFactory resouceFactory;

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    BillingService billingService;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    ConstructionValueService constructionValueService;

    @RequestMapping(value = "sra_index")
    public ModelAndView index(Model model, HttpServletRequest request) {

        List<IndexContractsTotalCountDto> list = saleContractService.getContractsTotalByMonthAndYear();
        if (list != null && list.size() > 0) {
            for (IndexContractsTotalCountDto dto : list) {
                BigDecimal thisMonth_money = dto.getSum_month_judge_money();
                BigDecimal lastMonth_money = dto.getSum_month_past_judge_money();

                if(null != thisMonth_money)
                model.addAttribute("thisMonth_money", thisMonth_money.setScale(2,BigDecimal.ROUND_HALF_UP));


                if (thisMonth_money != null && lastMonth_money != null) {
                    BigDecimal judge = thisMonth_money.divide(lastMonth_money, 4, BigDecimal.ROUND_HALF_EVEN);

                    //获取格式化对象
                    NumberFormat num = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    num.setMinimumFractionDigits(2);
                    String money = num.format(judge);

                    model.addAttribute("monthRate", money);//月增长率
                } else {
                    model.addAttribute("monthRate", "/");//月增长率
                }

                BigDecimal thisYear_money = dto.getSum_year_judge_money();
                BigDecimal lastYear_money = dto.getSum_year_past_judge_money();

                if(null!=thisYear_money)
                model.addAttribute("thisYear_money", thisYear_money.setScale(2,BigDecimal.ROUND_HALF_UP));


                if (thisYear_money != null && lastYear_money != null) {
                    BigDecimal judge = thisYear_money.divide(lastYear_money, 4, BigDecimal.ROUND_HALF_EVEN);

                    //获取格式化对象
                    NumberFormat num = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    num.setMinimumFractionDigits(2);
                    String money = num.format(judge);

                    model.addAttribute("yearRate", money);//年增长率
                } else {
                    model.addAttribute("yearRate", "/");//年增长率
                }
            }
        }


        return view(layout, "index", model);
    }

    @RequestMapping(value = "ajax_query_index_charts_thisYearSaleContracts")
    @ResponseBody
    public SaleContractDto[] queryIndex_thisYear() {

        String year = "";
        String company = "";
        String jiafangName = "";

        SaleContractDto[] array = new SaleContractDto[12];

        List<Map<String, Object>> mapList = saleContractService.getSaleContractsCountData(year, company, jiafangName);

        for (Map<String, Object> map : mapList) {
            SaleContractDto dto = new SaleContractDto();
            Integer month = null;
            BigDecimal money = BigDecimal.ZERO;
            for (Map.Entry<String, Object> entry : map.entrySet()) {

                String key = entry.getKey();

                if (key.equals("sale_month")) {
                    month = (Integer) entry.getValue();
                }
                if (key.equals("judge_money")) {
                    money =(BigDecimal) entry.getValue();

                }
            }

            dto.setJudgeMoney(money);
            array[month - 1] = dto;
        }

        for (int i = 0; i < 12; i++) {
            if (array[i] == null) {
                SaleContractDto dto = new SaleContractDto();
                dto.setJudgeMoney(BigDecimal.ZERO);
                array[i] = dto;
            }
        }

        return array;
    }

    @RequestMapping(value = "ajax_query_index_thisYearSaleContracts_number")
    @ResponseBody
    public SaleContractDto[] queryThisYearSaleContractsNumber() {

        SaleContractDto[] array = new SaleContractDto[12];

        List<Map<String, Object>> mapList = saleContractService.getSaleContracts_count_byNumber();
        for (Map<String, Object> map : mapList) {
            SaleContractDto dto = new SaleContractDto();
            Integer month = null;
            BigInteger count = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key.equals("sale_month")) {
                    month = (Integer) entry.getValue();
                }
                if (key.equals("count")) {
                    count = (BigInteger) entry.getValue();
                }
            }
            dto.setNumber(count);
            array[month - 1] = dto;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                SaleContractDto dto = new SaleContractDto();
                dto.setNumber(BigInteger.valueOf(0));
                array[i] = dto;
            }
        }

        return array;
    }

    @RequestMapping(value = "ajax_query_index_thisYearSaleContracts_billing")
    @ResponseBody
    public BillingDto[] queryThisYearSaleContractsBilling() {

        BillingDto[] array = new BillingDto[12];

        List<Map<String, Object>> mapList = saleContractService.getBilling_count();
        for (Map<String, Object> map : mapList) {
            BillingDto dto = new BillingDto();
            Integer month = null;
            BigDecimal count = BigDecimal.ZERO;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key.equals("month")) {
                    month = (Integer) entry.getValue();
                }
                if (key.equals("money")) {
                    count = (BigDecimal) entry.getValue();
                }
            }
            dto.setBillTotalMoney(count);
            array[month - 1] = dto;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                BillingDto dto = new BillingDto();
                dto.setBillTotalMoney(BigDecimal.valueOf(0));
                array[i] = dto;
            }
        }

        return array;
    }

    @RequestMapping(value = "ajax_query_index_thisYearSaleContracts_gathering")
    @ResponseBody
    public GatheringDto[] queryThisYearSaleContractsGathering() {

        GatheringDto[] array = new GatheringDto[12];

        List<Map<String, Object>> mapList = saleContractService.getGathering_count();
        for (Map<String, Object> map : mapList) {
            GatheringDto dto = new GatheringDto();
            Integer month = null;
            BigDecimal count = BigDecimal.ZERO;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key.equals("month")) {
                    month = (Integer) entry.getValue();
                }
                if (key.equals("money")) {
                    count = (BigDecimal) entry.getValue();
                }
            }
            dto.setGatheringMoney(count);
            array[month - 1] = dto;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                GatheringDto dto = new GatheringDto();
                dto.setGatheringMoney(BigDecimal.ZERO);
                array[i] = dto;
            }
        }

        return array;
    }

    @RequestMapping(value = "ajax_query_index_thisYearSaleContracts_conValue")
    @ResponseBody
    public ConstructionValueDto[] queryThisYearSaleContractsConValue() {

        //今年销售合同 数组 12个
        ConstructionValueDto[] array = new ConstructionValueDto[12];

        List<Map<String, Object>> mapList = saleContractService.getConValue_count();
        for (Map<String, Object> map : mapList) {
            ConstructionValueDto dto = new ConstructionValueDto();
            Integer month = null;
            BigDecimal count = BigDecimal.ZERO;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key.equals("month")) {
                    month = (Integer) entry.getValue();
                }
                if (key.equals("money")) {
                    count = (BigDecimal) entry.getValue();
                }
            }
            dto.setTotalMoney(count);
            array[month - 1] = dto;
        }


        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                ConstructionValueDto dto = new ConstructionValueDto();
                dto.setTotalMoney(BigDecimal.ZERO);
                array[i] = dto;
            }
        }

        return array;
    }

    @RequestMapping(value = "demon")
    public ModelAndView test111(Model model) {

        return view(layout, "demon", model);
    }

    @RequestMapping(value = "error")
    public ModelAndView error(Model model) {

        return view("/uAuth/error", model);
    }

    @RequestMapping(value = "alertTest")
    public ModelAndView alertTest(Model model) {
        return view("parameter/_addParameterForm", model);
    }
}
