package com.sunesoft.ancon.webapp.controller.saleContract;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.saleContract.application.BillingService;
import com.sunesoft.ancon.core.saleContract.application.ConstructionValueService;
import com.sunesoft.ancon.core.saleContract.application.GatheringService;
import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.criteria.BillingCriteria;
import com.sunesoft.ancon.core.saleContract.application.criteria.ConstructionValueCriteria;
import com.sunesoft.ancon.core.saleContract.application.criteria.GatheringCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import com.sunesoft.ancon.webapp.utils.URI;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * 销售合同 基本信息、开票管理、收款管理、施工产值 四个模块
 * Created by admin on 2016/11/24.
 */
@Controller
public class SaleContractInfoController extends Layout {

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    BillingService billingService;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    ConstructionValueService constructionValueService;

    @Autowired
    SecondPartyService secondPartyService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    UserSession userSession;

    /**
     * 销售合同(包括四个模块) 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_s_i")
    public ModelAndView saleCountractIndex(Model model, HttpServletRequest request) {

        //id companyId 销售合同统计链接判断
        String id = request.getParameter("id");
        String companyId = request.getParameter("companyId");
        if (!StringUtils.isNullOrWhiteSpace(id))
            model.addAttribute("count_saleContractId", id);
        if (!StringUtils.isNullOrWhiteSpace(companyId))
            model.addAttribute("count_companyId", companyId);


        //获取单个销售合同
        String saleContractId = request.getParameter("saleContractId");
        if (!StringUtils.isNullOrWhiteSpace(saleContractId)) {
            SaleContractDto dto = saleContractService.getById(Long.parseLong(saleContractId));
            model.addAttribute("bean", dto);
        }

        //新增 id和companyId 链接到当前新增的合同 进行展示
        String saleContract_add_id = request.getParameter("saleContract_add_id");
        String saleContract_add_companyId = request.getParameter("saleContract_add_companyId");
        if (!StringUtils.isNullOrWhiteSpace(saleContract_add_companyId))
            model.addAttribute("saleContract_add_id", saleContract_add_id);
        if (!StringUtils.isNullOrWhiteSpace(saleContract_add_companyId))
            model.addAttribute("saleContract_add_companyId", saleContract_add_companyId);

        //销售合同
        List<SaleContractDto> list = saleContractService.getAll();
        model.addAttribute("list", list);

        //分公司   左侧属性结构展示
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDeptCommpany();
        model.addAttribute("secondPartyDtos", deptmentDtos);

        //当前用户的信息
        UserSessionDto userSessionDto = userSession.getCurrentUser(request);
        model.addAttribute("userDto", userSessionDto);

        //获取用户权限 首次安魂新 需要有新建合同权限
        Map<String, ResourceDto> operate = userSession.getPageReadonlyOrEditor("sra_s_i", request);
        List<ResourceDto> dtos = new ArrayList<>();
        if (operate != null && operate.size() > 0) {
            for (Map.Entry<String, ResourceDto> entry : operate.entrySet()) {
                //判断该按钮 是否可编辑操作
                if (entry.getValue().getOperateFlag() != null) {
                    if (entry.getValue().getOperateFlag()) {
                        dtos.add(entry.getValue());
                    }
                }
            }
        }
        ResourceDto resourceDto = operate.get("add_contract");
        if (resourceDto != null) {
            model.addAttribute("current_power", resourceDto.getUrl());
        }

        return view(layout, "saleContract/saleContract_index", model);
    }

    /**
     * 定审页面 弹窗
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_saleContract_baseInfo_judge")
    public ModelAndView saleContractBaseInfoJudge(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        SaleContractDto dto = saleContractService.getById(Long.parseLong(id));
        model.addAttribute("bean", dto);

        return view("saleContract/_saleContract_baseInfo_judge", model);
    }

    /**
     * 定审页面 新增 无修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_add_fixedTrial_saleContract")
    @ResponseBody
    public Long addFixedTrial(HttpServletRequest request) {
        String id = request.getParameter("id");

        String judgeMoney = request.getParameter("judgeMoney");
        String judTime = request.getParameter("judTime");

        SaleContractDto dto = saleContractService.getById(Long.parseLong(id));

        BigDecimal contract_money = new BigDecimal(judgeMoney).setScale(4,BigDecimal.ROUND_HALF_UP);
        dto.setJudgeMoney(contract_money);
        if (!StringUtils.isNullOrWhiteSpace(judTime))
            dto.setJudgeTime(DateHelper.parse(judTime, "yyyy-MM-dd"));
        dto.setJudgeStatus("已定审");
        saleContractService.updateSaleContract(dto);
        return 1L;
    }

    /**
     * 销售合同 基本信息 修改
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_saleContract_baseInfo_update")
    public ModelAndView saleContractBaseInfoUpdate(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            SaleContractDto dto = saleContractService.getById(Long.parseLong(id));
            model.addAttribute("bean", dto);
        }

        //公司名称
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDeptCommpany();
        model.addAttribute("deptmentDtos", deptmentDtos);

        //甲方名称
        List<ContractorPartyDto> contractorPartyDtos = secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos", contractorPartyDtos);

        return view("saleContract/_addSaleContract", model);
    }

    /**
     * 开票管理 数据查询
     *
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_query_sc_index_billing")
    public void queryBillingData(HttpServletRequest request, HttpServletResponse response, BillingCriteria criteria) {
        String begignTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String billing_taxType = request.getParameter("billing_taxType");
        String billing_name = request.getParameter("billing_name");
        String billing_branchCompany = request.getParameter("billing_branchCompany");//已经改为分公司id
        String billing_jiaFangName = request.getParameter("billing_jiaFangName");

        String saleContractId = request.getParameter("saleContractId");

        if (!StringUtils.isNullOrWhiteSpace(begignTime))
            criteria.setBe_time(DateHelper.parse(begignTime, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(endTime))
            criteria.setEnd_time(DateHelper.parse(endTime, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(billing_taxType))
            criteria.setTaxType(URI.deURI(billing_taxType));
        if (!StringUtils.isNullOrWhiteSpace(billing_name))
            criteria.setName(URI.deURI(billing_name));
        if (!StringUtils.isNullOrWhiteSpace(billing_branchCompany))
            criteria.setCompanyId(Long.parseLong(billing_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(billing_jiaFangName))
            criteria.setJiaFangName(URI.deURI(billing_jiaFangName));

        if (!StringUtils.isNullOrWhiteSpace(saleContractId))
            criteria.setSaleContractId(Long.parseLong(saleContractId));


        PagedResult pageResult = billingService.fingPages(criteria);

        String json = JsonHelper.toJson(pageResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 收款管理 数据查询
     *
     * @param response
     * @param request
     * @param criteria
     */
    @RequestMapping(value = "ajax_query_sc_index_gathering")
    public void queryGatheringData(HttpServletResponse response, HttpServletRequest request, GatheringCriteria criteria) {
        String beTime = request.getParameter("beTime");
        String enTime = request.getParameter("enTime");

        String saleContractId = request.getParameter("saleContractId");

        if (!StringUtils.isNullOrWhiteSpace(saleContractId))
            criteria.setSaleContractId(Long.parseLong(saleContractId));


        if (!StringUtils.isNullOrWhiteSpace(beTime))
            criteria.setBeginTime(DateHelper.parse(beTime, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(enTime))
            criteria.setEndTime(DateHelper.parse(enTime, "yyyy-MM-dd"));

        String gathering_name = request.getParameter("gathering_name");
        String gathering_branchCompany = request.getParameter("gathering_branchCompany");//已经改为分公司id
        String gathering_jiaFangName = request.getParameter("gathering_jiaFangName");

        if (!StringUtils.isNullOrWhiteSpace(gathering_name))
            criteria.setName(URI.deURI(gathering_name));
        if (!StringUtils.isNullOrWhiteSpace(gathering_branchCompany))
            criteria.setCompanyId(Long.parseLong(gathering_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(gathering_jiaFangName))
            criteria.setJiaFangName(URI.deURI(gathering_jiaFangName));


        PagedResult pageResult = gatheringService.fingPages(criteria);


        String json = JsonHelper.toJson(pageResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 施工产值 数据查询
     *
     * @param response
     * @param criteria
     * @param request
     */
    @RequestMapping(value = "ajax_query_sc_index_constructionValue")
    public void queryConstructionValue(HttpServletResponse response, ConstructionValueCriteria criteria, HttpServletRequest request) {
        String be_time = request.getParameter("be_time");
        String en_time = request.getParameter("en_time");

        String saleContractId = request.getParameter("saleContractId");
        if (!StringUtils.isNullOrWhiteSpace(saleContractId))
            criteria.setSaleContractId(Long.parseLong(saleContractId));

        if (!StringUtils.isNullOrWhiteSpace(be_time))
            criteria.setBeginTime(DateHelper.parse(be_time, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(en_time))
            criteria.setEndTime(DateHelper.parse(en_time, "yyyy-MM-dd"));

        String conValue_name = request.getParameter("conValue_name");
        String conValue_branchCompany = request.getParameter("conValue_branchCompany");//已经改成分公司id查询
        String conValue_jiaFangName = request.getParameter("conValue_jiaFangName");
        if (!StringUtils.isNullOrWhiteSpace(conValue_name))
            criteria.setName(URI.deURI(conValue_name));
        if (!StringUtils.isNullOrWhiteSpace(conValue_branchCompany))
            criteria.setCompnayId(Long.parseLong(conValue_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(conValue_jiaFangName))
            criteria.setJiafangName(URI.deURI(conValue_jiaFangName));

        PagedResult pageResult = constructionValueService.fingPages(criteria);

     /*   String oprator=userSession.getPageReadonlyOrEditor("sra_s_i",request);
        pageResult.setOprator(oprator);
*/
        String json = JsonHelper.toJson(pageResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 销售合同 单个查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_single_saleContract")
    @ResponseBody
    public SaleContractDto querySingleSaleContract(HttpServletRequest request) {
        String saleContractId = request.getParameter("saleContractId");
        SaleContractDto dto = null;
        if (!StringUtils.isNullOrWhiteSpace(saleContractId)) {

            dto = saleContractService.getById(Long.parseLong(saleContractId));
            BigDecimal judgeMoney = dto.getJudgeMoney();

            List<Map<String, Object>> mapList = saleContractService.getAllCount_biilingAndGatheringAndConValue(Long.parseLong(saleContractId));
            BigDecimal billing_money = BigDecimal.ZERO;
            BigDecimal gathering_money = BigDecimal.ZERO;
            BigDecimal conValue_money = BigDecimal.ZERO;
            for (Map<String, Object> map : mapList) {

                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key.equals("sum_billing_money") && entry.getValue() != null) {
                        billing_money = (BigDecimal) entry.getValue();
                    }
                    if (key.equals("sum_gathering_money") && entry.getValue() != null) {
                        gathering_money = (BigDecimal) entry.getValue();
                    }
                    if (key.equals("sum_conValue_money") && entry.getValue() != null) {
                        conValue_money = (BigDecimal) entry.getValue();
                    }

                }
            }
            dto.setBillingCount(billing_money);
            dto.setReceivablesCount(gathering_money);
            dto.setConstructionValue(conValue_money);
//
//            //money
//            // 已开票 金额 统计

            if (judgeMoney != null) {
                if (billing_money != BigDecimal.ZERO) {
                    BigDecimal judge = billing_money.divide(judgeMoney, 4, BigDecimal.ROUND_HALF_UP);//四舍五入

                    //获取格式化对象
                    NumberFormat num = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    num.setMinimumFractionDigits(2);
                    String judge_money = num.format(judge);

                    dto.setBillingRate(judge_money);
                }
            }

//            // 已收款 金额 统计
//
            if (judgeMoney != null) {
                if (gathering_money != BigDecimal.ZERO) {
                    BigDecimal judge = gathering_money.divide(judgeMoney, 4, BigDecimal.ROUND_HALF_UP);

                    //获取格式化对象
                    NumberFormat num = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    num.setMinimumFractionDigits(2);
                    String judge_money = num.format(judge);

                    dto.setGatheringRate(judge_money);
                }
            }

//            //money_2
//            // 已收款 金额 统计
//
            if (judgeMoney != null) {
                if (conValue_money != BigDecimal.ZERO) {
                    BigDecimal judge = conValue_money.divide(judgeMoney, 4, BigDecimal.ROUND_HALF_UP);

                    //获取格式化对象
                    NumberFormat num = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    num.setMinimumFractionDigits(2);
                    String judge_money = num.format(judge);

                    dto.setConValueRate(judge_money);
                }
            }

        }


        List<ResourceDto> list = this.getUserAuthority(request, dto);
        dto.setAuthorityControl(list);

        //剩余天数计算 合同到期时间-当前时间
        Date beginTime = new Date();
        Date endTime = dto.getContractEndTime();
        long intervalMilli = endTime.getTime() - beginTime.getTime();

        int days = (int) (intervalMilli / (24 * 60 * 60 * 1000));
        dto.setSurplusDay(days);


        return dto;
    }

    /**
     * 页面左侧 树形结构 销售合同数据查询
     *
     * @return
     */
    @RequestMapping(value = "ajax_query_all_saleContracts")
    @ResponseBody
    public List<SaleContractDto> getAllContracts() {
        //TODO 此次性能优化
        //       List<SaleContractSimpleDto> list = saleContractService.getSimpleAll();
        List<SaleContractDto> list = saleContractService.getAll();

        return list;
    }

    /**
     * 页面左侧 某个单位树形结构 销售合同数据查询
     *
     * @return
     */
    @RequestMapping(value = "ajax_query_company_saleContracts")
    @ResponseBody
    public List<SaleContractDto> getDeptContracts(Long companyId) {
        //TODO 此次性能优化
        List<SaleContractDto> list = saleContractService.getByCompanyId(companyId);
        return list;
    }

    /**
     * 销售合同 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addSalecontract")
    public ModelAndView addSalecontract(Model model, HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String companyId = request.getParameter("companyId");
        if (!StringUtils.isNullOrWhiteSpace(companyId))
            model.addAttribute("companyId", companyId);
        String companyName = URI.deURI(request.getParameter("companyName"));
        if (!StringUtils.isNullOrWhiteSpace(companyName))
            model.addAttribute("companyName", companyName);
        model.addAttribute("parentId", parentId);

        //甲方名称
        List<ContractorPartyDto> contractorPartyDtos = secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos", contractorPartyDtos);



        return view("saleContract/_addSaleContract", model);
    }

    /**
     * 销售合同     新增和修改
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_saleContract", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateSaleContract(HttpServletRequest request, SaleContractDto dto) {
        String id = request.getParameter("id");

        String conBeginTime = request.getParameter("conBeginTime");
        String conEndTime = request.getParameter("conEndTime");
        String proStartTime = request.getParameter("proStartTime");
        String parent_id = request.getParameter("parent_id");
        String companyId = request.getParameter("companyId");
        String companyName = request.getParameter("companyName");

        String contractMoney = request.getParameter("contractMoney");
        BigDecimal contract_money = new BigDecimal(contractMoney).setScale(4,BigDecimal.ROUND_HALF_UP);
        dto.setContractMoney(contract_money);

        UserSessionDto userSessionDto = userSession.getCurrentUser(request);

        //所属分公司
        if (!StringUtils.isNullOrWhiteSpace(companyId))
            dto.setCompanyId(Long.parseLong(companyId));

        if (!StringUtils.isNullOrWhiteSpace(parent_id))
            dto.setParentId(Long.parseLong(parent_id));
        if (!StringUtils.isNullOrWhiteSpace(companyName))
            dto.setBranchCompany(companyName);

        if (!StringUtils.isNullOrWhiteSpace(conBeginTime))
            dto.setContractBeginTime(DateHelper.parse(conBeginTime, "yyyy-MM-dd"));

        if (!StringUtils.isNullOrWhiteSpace(conEndTime)) {
            dto.setContractEndTime(DateHelper.parse(conEndTime, "yyyy-MM-dd"));
        }

        if (!StringUtils.isNullOrWhiteSpace(proStartTime))
            dto.setProjectStartTime(DateHelper.parse(proStartTime, "yyyy-MM-dd"));

        //定审金额 新增和修改都默认是合同金额 一旦定审完之后 就确定定审金额 无发修改
        if (StringUtils.isNullOrWhiteSpace(id)){
            //新增
            if (dto.getContractMoney() != null) {
                dto.setJudgeMoney(dto.getContractMoney());
            }
            dto.setJudgeStatus("未定审");
        }else {

            //修改
            //已有数据 定审状态每次修改的时候都需要设置
            SaleContractDto saleContractDto = saleContractService.getById(Long.parseLong(id));
            if (!saleContractDto.getJudgeStatus().equals("已定审")) {
                if (saleContractDto.getContractMoney() != null) {
                    dto.setJudgeMoney(dto.getContractMoney());
                    dto.setJudgeStatus("未定审");
                }
            }else{
                dto.setJudgeStatus("已定审");
                dto.setJudgeMoney(saleContractDto.getJudgeMoney());
            }
            dto.setRegisterPerson(saleContractDto.getRegisterPerson());
        }


        CommonResult commonResult = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改

            //修改组织机构名称
            String edit_companyId = request.getParameter("edit_companyId");
            String edit_companyName = request.getParameter("edit_companyName");
            if (!StringUtils.isNullOrWhiteSpace(edit_companyId))
                dto.setCompanyId(Long.parseLong(edit_companyId));
            if (!StringUtils.isNullOrWhiteSpace(edit_companyName))
                dto.setBranchCompany(edit_companyName);

            commonResult = saleContractService.updateSaleContract(dto);

        } else {
            //新增
            dto.setRegisterPerson(userSessionDto.getName());

            commonResult = saleContractService.addSaleContract(dto);

        }
        return commonResult;
    }

    /**
     * 开票管理 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "_addBillingManagement")
    public ModelAndView billingManagement(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        String saleContractId = request.getParameter("saleContractId");

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            //计算开票金额 需要减掉自身的金额
            BillingDto dto = billingService.getById(Long.parseLong(id));
            model.addAttribute("bean", dto);

            SaleContractDto contractDto = saleContractService.getById(Long.parseLong(saleContractId));
            List<BillingDto> list = billingService.getBySaleContractId(Long.parseLong(saleContractId));

            List<BillingDto> billingDtos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() != Long.parseLong(id)) {
                    billingDtos.add(list.get(i));
                }

            }

            BigDecimal billingMoney = BigDecimal.ZERO;
            for (BillingDto dto1 : billingDtos) {
                billingMoney = billingMoney.add(dto1.getBillingMoney());
            }
            BigDecimal restMoney = BigDecimal.ZERO;
            if (contractDto.getJudgeMoney() != null) {
                restMoney = contractDto.getJudgeMoney().subtract(billingMoney);
            } else {
                restMoney = contractDto.getContractMoney().subtract(billingMoney);
            }
            model.addAttribute("restMoney", restMoney);

        } else {
            if (!StringUtils.isNullOrWhiteSpace(saleContractId)) {
                SaleContractDto dto = saleContractService.getById(Long.parseLong(saleContractId));
                model.addAttribute("dto", dto);

                List<BillingDto> list = billingService.getBySaleContractId(Long.parseLong(saleContractId));
                BigDecimal billingMoney = BigDecimal.ZERO;
                for (BillingDto dto1 : list) {
                    billingMoney = billingMoney.add(dto1.getBillingMoney());
                }
                BigDecimal restMoney = BigDecimal.ZERO;
                if (dto.getJudgeMoney() != null) {
                    restMoney = dto.getJudgeMoney().subtract(billingMoney);
                } else {
                    restMoney = dto.getContractMoney().subtract(billingMoney);
                }
                model.addAttribute("restMoney", restMoney);

            }
        }


        return view("saleContract/_addBillingManagement", model);
    }

    /**
     * 开票管理 新增和修改
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_billingManagement_saleContract", method = RequestMethod.POST)
    @ResponseBody
    public Long addOrUpdateBillingManagement(HttpServletRequest request, BillingDto dto) {
        String id = request.getParameter("id");
        String billing_saleContract_id = request.getParameter("billing_saleContract_id");

        UserSessionDto userSessionDto = userSession.getCurrentUser(request);

        dto.setLastEditPerson(userSessionDto.getName());

        String bilTime = request.getParameter("bilTime");
        if (!StringUtils.isNullOrWhiteSpace(bilTime))
            dto.setBillingDate(DateHelper.parse(bilTime, "yyyy-MM-dd"));

        if (!StringUtils.isNullOrWhiteSpace(billing_saleContract_id)) {
            SaleContractDto dto1 = new SaleContractDto();
            dto1.setId(Long.valueOf(billing_saleContract_id));
            dto.setSaleContractDto(dto1);
        }


        Long L = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            billingService.updateBilling(dto);
            L = 2L;
        } else {
            //新增
            dto.setBillingPerson(userSessionDto.getName());
            billingService.addBilling(dto);
            L = 1L;
        }
        return L;
    }

    /**
     * 开票管理 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteBillingManagement")
    @ResponseBody
    public boolean deleteBillingManagement(HttpServletRequest request) {
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        Long[] id_long = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            id_long[i] = Long.valueOf(ids[i]);
        }
        boolean boo = billingService.deleteBilling(id_long);
        return boo;
    }

    /**
     * 收款管理 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "_addGatheringManagement")
    public ModelAndView addGatheringManagement(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        String saleContractId = request.getParameter("saleContractId");

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            //计算收款金额 需要去掉自身金额
            GatheringDto dto = gatheringService.getById(Long.parseLong(id));
            model.addAttribute("bean", dto);

            SaleContractDto contractDto = saleContractService.getById(Long.parseLong(saleContractId));
            List<GatheringDto> list = gatheringService.getBysaleContractId(Long.parseLong(saleContractId));

            List<GatheringDto> gatheringDtos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() != Long.parseLong(id)) {
                    gatheringDtos.add(list.get(i));
                }
            }

            BigDecimal gatheringMoney = BigDecimal.ZERO;
            for (GatheringDto dto1 : gatheringDtos) {
                gatheringMoney = gatheringMoney.add(dto1.getGatheringMoney());
            }
            BigDecimal restMoney = BigDecimal.ZERO;
            if (contractDto.getJudgeMoney() != null) {
                restMoney = contractDto.getJudgeMoney().subtract(gatheringMoney);
            } else {
                restMoney = contractDto.getContractMoney().subtract(gatheringMoney);
            }
            model.addAttribute("restMoney", restMoney);


        } else {
            if (!StringUtils.isNullOrWhiteSpace(saleContractId)) {
                SaleContractDto dto = saleContractService.getById(Long.parseLong(saleContractId));
                model.addAttribute("dto", dto);

                List<GatheringDto> list = gatheringService.getBysaleContractId(Long.parseLong(saleContractId));
                BigDecimal gatheringMoney = BigDecimal.ZERO;
                for (GatheringDto dto1 : list) {
                    gatheringMoney = gatheringMoney.add(dto1.getGatheringMoney());
                }
                BigDecimal restMoney = BigDecimal.ZERO;
                if (dto.getJudgeMoney() != null) {
                    restMoney = dto.getJudgeMoney().subtract(gatheringMoney);
                } else {
                    restMoney = dto.getContractMoney().subtract(gatheringMoney);
                }
                model.addAttribute("restMoney", restMoney);
            }
        }


        model.addAttribute("date", new Date());
        return view("saleContract/_addGatheringManagement", model);
    }

    /**
     * 收款管理 新增和修改
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_gatheringManagement_saleContract", method = RequestMethod.POST)
    @ResponseBody
    public Long addOrUpdateGatheringManagement(HttpServletRequest request, GatheringDto dto) {
        String id = request.getParameter("id");
        String gathering_saleContract_id = request.getParameter("gathering_saleContract_id");

        UserSessionDto userSessionDto = userSession.getCurrentUser(request);
        dto.setLastUpdatePerson(userSessionDto.getName());

        String gatheringMoney = request.getParameter("gatheringMoney");
        BigDecimal gathering_money = new BigDecimal(gatheringMoney).setScale(4,BigDecimal.ROUND_HALF_UP);
        dto.setGatheringMoney(gathering_money);

        if (!StringUtils.isNullOrWhiteSpace(gathering_saleContract_id)) {
            SaleContractDto dto1 = new SaleContractDto();
            dto1.setId(Long.parseLong(gathering_saleContract_id));
            dto.setSaleContractDto(dto1);
        }


        String gatTime = request.getParameter("gatTime");
        if (!StringUtils.isNullOrWhiteSpace(gatTime))
            dto.setGatheringTime(DateHelper.parse(gatTime, "yyyy-MM-dd"));
        Long L = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            gatheringService.update(dto);
            L = 2L;
        } else {
            //新增
            dto.setGatheringPerson(userSessionDto.getName());
            gatheringService.add(dto);
            L = 1L;
        }
        return L;
    }

    /**
     * 收款管理 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteGatheringManagement")
    @ResponseBody
    public boolean deleteGatheringManagement(HttpServletRequest request) {
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        Long[] id_long = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            id_long[i] = Long.valueOf(ids[i]);
        }
        boolean boo = gatheringService.delete(id_long);
        return boo;
    }

    /**
     * 施工产值 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "_addProjectOutputValue")
    public ModelAndView addProjectOutputValue(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            ConstructionValueDto dto = constructionValueService.getById(Long.parseLong(id));
            model.addAttribute("bean", dto);
        }
        return view("saleContract/_addProjectOutputValue", model);
    }

    /**
     * 施工产值 新增和修改
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_constructionValue_saleContract", method = RequestMethod.POST)
    @ResponseBody
    public Long addOrUpdateConstructionValue(HttpServletRequest request, ConstructionValueDto dto) {
        String id = request.getParameter("id");
        String be_time = request.getParameter("be_time");
        String en_time = request.getParameter("en_time");
        String conValue_saleContract_id = request.getParameter("conValue_saleContract_id");

        UserSessionDto userSessionDto = userSession.getCurrentUser(request);
        dto.setLastUpdatePerson(userSessionDto.getName());

        String outputValue = request.getParameter("outputValue");
        BigDecimal conValue_money = new BigDecimal(outputValue).setScale(4,BigDecimal.ROUND_HALF_UP);
        dto.setOutputValue(conValue_money);

        if (!StringUtils.isNullOrWhiteSpace(conValue_saleContract_id)) {
            SaleContractDto dto1 = new SaleContractDto();
            dto1.setId(Long.parseLong(conValue_saleContract_id));
            dto.setSaleContractDto(dto1);
        }


        if (!StringUtils.isNullOrWhiteSpace(be_time))
            dto.setBeginTime(DateHelper.parse(be_time, "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(en_time))
            dto.setEndTime(DateHelper.parse(en_time, "yyyy-MM-dd"));
        dto.setWriteDate(new Date());
        Long L = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //修改
            constructionValueService.update(dto);
            L = 2L;
        } else {
            dto.setWritePerson(userSessionDto.getName());
            constructionValueService.add(dto);
            L = 1L;
        }
        return L;
    }

    /**
     * 施工产值 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteConValue")
    @ResponseBody
    public boolean deleteConValue(HttpServletRequest request) {
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        Long[] id_long = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            id_long[i] = Long.valueOf(ids[i]);
        }
        boolean boo = constructionValueService.delete(id_long);
        return boo;
    }


    public List<ResourceDto> getUserAuthority(HttpServletRequest request, SaleContractDto saleContractDto) {
        //获取用户权限
        Map<String, ResourceDto> operate = userSession.getPageReadonlyOrEditor("sra_s_i", request);
        List<ResourceDto> dtos = new ArrayList<>();
        if (operate != null && operate.size() > 0) {
            for (Map.Entry<String, ResourceDto> entry : operate.entrySet()) {
                //判断该按钮 是否可编辑操作
                if (entry.getValue().getOperateFlag() != null) {
                    if (entry.getValue().getOperateFlag()) {
                        dtos.add(entry.getValue());
                    }
                }

            }
        }

        //1. 先判断部门 还是分公司
        //2. 再判断是独立分公司，还是非独立分公司  如果是部门 需要再判断是哪个部门
        //3. 然后是用户权限判断
        //4. 最后是合同状态 定审状态

        UserSessionDto userSessionDto = userSession.getCurrentUser(request);
        Long currentUser_companyOrDept_id = userSessionDto.getCompanyId();//当前用户的  机构id
        Integer currentUser_companyOrDept_type = userSessionDto.getType();//机构类型    1:部门  2:独立分公司 3: 非独立分公司
        String currentUser_companyOrDept_no = userSessionDto.getDeptNo();//机构编号     部门判断标志

        //销售合同
        //当前用户最终的权限 没有权限的 就是readOnly
        List<ResourceDto> currentUser_authority = new ArrayList<>();
        if (saleContractDto.getCompanyId() != null) {
            Long companyId = saleContractDto.getCompanyId();
            DeptmentDto deptmentDto = deptmentService.getByDeptId(companyId);
            Integer companyType = deptmentDto.getType();
            String companyNo = deptmentDto.getDeptNo();


            //先判断部门
            if (currentUser_companyOrDept_type == 1) {

                    //判断具体部门
                    if (currentUser_companyOrDept_no.equals("1006")) {
                        //经营管理部 可以新增合同 修改 定审 和施工产值
                        for (int i = 0; i < dtos.size(); i++) {

                            //新增
                            if (dtos.get(i).getUrl().equals("add_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }

                            //修改
                            if (dtos.get(i).getUrl().equals("edit_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //定审
                            if (dtos.get(i).getUrl().equals("judge_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //登记产值
                            if (dtos.get(i).getUrl().equals("login_output")) {
                                currentUser_authority.add(dtos.get(i));
                            }



                        }

                    }
                    if (currentUser_companyOrDept_no.equals("1007")) {
                        //财务部   开票 收款
                        for (int i = 0; i < dtos.size(); i++) {

                                //登记开票
                                if (dtos.get(i).getUrl().equals("login_Billing")) {
                                    currentUser_authority.add(dtos.get(i));
                                }
                                //登记收款
                                if (dtos.get(i).getUrl().equals("login_payable")) {
                                    currentUser_authority.add(dtos.get(i));
                                }

                        }

                    }



            } else {
                if (currentUser_companyOrDept_type == 2) {
                    //独立分公司 且是本公司员工 都可以进行操作
                    if (currentUser_companyOrDept_id == companyId) {
                        for (int i = 0; i < dtos.size(); i++) {


                            //新增
                            if (dtos.get(i).getUrl().equals("add_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //修改
                            if (dtos.get(i).getUrl().equals("edit_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //定审
                            if (dtos.get(i).getUrl().equals("judge_contract")) {
                                currentUser_authority.add(dtos.get(i));
                            }

                            //登记开票
                            if (dtos.get(i).getUrl().equals("login_Billing")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //登记收款
                            if (dtos.get(i).getUrl().equals("login_payable")) {
                                currentUser_authority.add(dtos.get(i));
                            }
                            //登记产值
                            if (dtos.get(i).getUrl().equals("login_output")) {
                                currentUser_authority.add(dtos.get(i));
                            }


                        }
                    }

                }
                if (currentUser_companyOrDept_type == 3) {
                    //非独立分公司 本公司员工
                    if (currentUser_companyOrDept_id == companyId) {

                            ResourceDto dto = new ResourceDto();
                            dto.setUrl("login_output");
                            currentUser_authority.add(dto);

                    }
                }
            }
        }

        return currentUser_authority;
    }


}
