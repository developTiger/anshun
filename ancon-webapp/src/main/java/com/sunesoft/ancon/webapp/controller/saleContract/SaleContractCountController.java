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
import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDtoModel;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.results.ListResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.DtoFactory;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.fr.utils.excel.ExpotExcel;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.model.*;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import com.sunesoft.ancon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * 销售合同图表统计
 * Created by admin on 2016/11/24.
 */
@Controller
public class SaleContractCountController extends Layout {

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    BillingService billingService;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    ConstructionValueService constructionValueService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    SecondPartyService secondPartyService;

    /**
     * 销售合同 统计
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_s_c")
    public ModelAndView saleContractCount(Model model,HttpServletRequest request){

        String sale_countTime = request.getParameter("sale_countTime");
        if (!StringUtils.isNullOrWhiteSpace(sale_countTime)){
            String beginTime = sale_countTime+"-01-01";
            String endTime = sale_countTime+"-12-31";
            model.addAttribute("begin_time",beginTime);
            model.addAttribute("end_time",endTime);
        }
        String sale_branchCompany = request.getParameter("sale_branchCompany");//已经改为分公司id
        if (!StringUtils.isNullOrWhiteSpace(sale_branchCompany))
            model.addAttribute("sale_branchCompany",sale_branchCompany);
        String sale_jiafang = request.getParameter("sale_jiafang");
        if (!StringUtils.isNullOrWhiteSpace(sale_jiafang))
            model.addAttribute("sale_jiafang",URI.deURI(sale_jiafang));

        String contractType_begin_time = request.getParameter("contractType_begin_time");
        String contractType_end_time = request.getParameter("contractType_end_time");
        if (!StringUtils.isNullOrWhiteSpace(contractType_begin_time))
            model.addAttribute("contractType_begin_time",contractType_begin_time);
        if (!StringUtils.isNullOrWhiteSpace(contractType_end_time))
            model.addAttribute("contractType_end_time",contractType_end_time);

        String branchCompany_begin_time = request.getParameter("branchCompany_begin_time");
        String branchCompany_end_time = request.getParameter("branchCompany_end_time");
        if (!StringUtils.isNullOrWhiteSpace(branchCompany_begin_time))
            model.addAttribute("branchCompany_begin_time",branchCompany_begin_time);
        if (!StringUtils.isNullOrWhiteSpace(branchCompany_end_time))
            model.addAttribute("branchCompany_end_time",branchCompany_end_time);

        String projectMajor_begin_time = request.getParameter("projectMajor_begin_time");
        String projectMajor_end_time = request.getParameter("projectMajor_end_time");
        if (!StringUtils.isNullOrWhiteSpace(projectMajor_begin_time))
            model.addAttribute("projectMajor_begin_time",projectMajor_begin_time);
        if (!StringUtils.isNullOrWhiteSpace(projectMajor_end_time))
            model.addAttribute("projectMajor_end_time",projectMajor_end_time);

        //所属分公司
        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        //甲方名称
        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos",contractorPartyDtos);


        String saleContract_left_1 = "1";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount",model);
    }

    /**
     * 销售合同 查询
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_saleContract_info")
    public void querySaleContractInfo(HttpServletRequest request,SaleContractCriteria criteria,HttpServletResponse response){
        String begin_time = request.getParameter("begin_time");
        String end_time = request.getParameter("end_time");
        String branch_company = request.getParameter("branch_company");//已经改为分公司id
        String jia_fangName = request.getParameter("jia_fangName");
        String contract_type = request.getParameter("contract_type");
        String project_major =request.getParameter("project_major");
        String contract_status = request.getParameter("contract_status");


        if (!StringUtils.isNullOrWhiteSpace(begin_time))
            criteria.setBeginTime(DateHelper.parse(begin_time,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(end_time))
            criteria.setEndTime(DateHelper.parse(end_time,"yyyy-MM-dd"));

        if (!StringUtils.isNullOrWhiteSpace(branch_company))
            criteria.setCompanyId(Long.parseLong(branch_company));
        if (!StringUtils.isNullOrWhiteSpace(jia_fangName))
            criteria.setJiaFangName(URI.deURI(jia_fangName));
        if (!StringUtils.isNullOrWhiteSpace(contract_type))
            criteria.setContractType(URI.deURI(contract_type));
        if (!StringUtils.isNullOrWhiteSpace(project_major))
            criteria.setProjectMajor(URI.deURI(project_major));
        if (!StringUtils.isNullOrWhiteSpace(contract_status))
            criteria.setContractStatus(URI.deURI(contract_status));

        //四个排序 销售合同统计表
        String fourSorts = request.getParameter("fourSorts");
        if (!StringUtils.isNullOrWhiteSpace(fourSorts)){
            if (fourSorts.equals("a_bidNotice")){
                criteria.setBidNotice("a_bidNotice");
            }
            if (fourSorts.equals("d_bidNotice")){
                criteria.setBidNotice("d_bidNotice");
            }
            if (fourSorts.equals("a_constrctLicense")){
                criteria.setConstructLicense("a_constrctLicense");
            }
            if (fourSorts.equals("d_constrctLicense")){
                criteria.setConstructLicense("d_constrctLicense");
            }
            if (fourSorts.equals("a_finishCheck")){
                criteria.setFinishCheck("a_finishCheck");
            }
            if (fourSorts.equals("d_finishCheck")){
                criteria.setFinishCheck("d_finishCheck");
            }
            if (fourSorts.equals("a_projectSettlement")){
                criteria.setProjectSettlement("a_projectSettlement");
            }
            if (fourSorts.equals("d_projectSettlement")){
                criteria.setProjectSettlement("d_projectSettlement");
            }

            if (fourSorts.equals("a_contractIsReturn")){
                criteria.setContractIsReturn("a_contractIsReturn");
            }
            if (fourSorts.equals("d_contractIsReturn")){
                criteria.setContractIsReturn("d_contractIsReturn");
            }
            if (fourSorts.equals("a_number")){
                criteria.setNumber("a_number");
            }
            if (fourSorts.equals("d_number")){
                criteria.setNumber("d_number");
            }
        }


        PagedResult page = saleContractService.findPages(criteria);
//        List<SaleContractDto> list = page.getItems();
//        for (SaleContractDto dto:list){
//
//            List<BillingDto> billingDtos=billingService.getBySaleContractId(dto.getId());
//            BigDecimal billingMoney = BigDecimal.ZERO;
//            for (BillingDto dto1:billingDtos){
//                billingMoney = billingMoney.add(dto1.getBillingMoney());
//            }
//            dto.setBillingCount(billingMoney);
//
//            List<GatheringDto> gatheringDtos = gatheringService.getBysaleContractId(dto.getId());
//            BigDecimal gatheringMoney = BigDecimal.ZERO;
//            for(GatheringDto dto1:gatheringDtos){
//                gatheringMoney = gatheringMoney.add(dto1.getGatheringMoney());
//            }
//            dto.setReceivablesCount(gatheringMoney);
//
//            List<ConstructionValueDto> constructionValueDtos = constructionValueService.getBySaleContractId(dto.getId());
//            BigDecimal conValueMoney = BigDecimal.ZERO;
//            for (ConstructionValueDto dto1:constructionValueDtos){
//                conValueMoney = conValueMoney.add(dto1.getOutputValue());
//            }
//            dto.setConstructionValue(conValueMoney);
//
//        }
//

        String json = JsonHelper.toJson(page);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "sra_s_b")
    public ModelAndView saleContractCount_billing(Model model){

        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos",contractorPartyDtos);

        String saleContract_left_1 = "3";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount_billing",model);
    }

    @RequestMapping(value = "sra_s_g")
    public ModelAndView saleContractCount_gathering(Model model){

        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos",contractorPartyDtos);

        String saleContract_left_1 = "4";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount_gathering",model);
    }

    @RequestMapping(value = "sra_s_v")
    public ModelAndView salContractCount_conValue(Model model){

        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos",contractorPartyDtos);

        String saleContract_left_1 = "5";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount_conValue",model);
    }

    @RequestMapping(value = "sra_s_t")
    public ModelAndView saleContractCount_chart(Model model){

        //分公司
        List<DeptmentDto> list= deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        //甲方名称
        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("dtos",contractorPartyDtos);

        String saleContract_left_1 = "2";
        model.addAttribute("saleContract_left",saleContract_left_1);

        Date currentTime = new Date();
        model.addAttribute("currentTime",DateHelper.formatDate(currentTime,"yyyy"));

        return view(layout,"saleContract/saleContractCount_chart",model);
    }

    @RequestMapping(value = "sra_s_rf")
    public ModelAndView saleContractCount_reportForm(Model model){

        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
        model.addAttribute("list",list);

        List<ContractorPartyDto> contractorPartyDtos=secondPartyService.getByTypeAll(2);
        model.addAttribute("partyDtos",contractorPartyDtos);

        String saleContract_left_1 = "6";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount_reportForm",model);
    }

    @RequestMapping(value = "sra_s_trf")
    public ModelAndView saleContractCount_totalReportForm(Model model){

        String saleContract_left_1 = "7";
        model.addAttribute("saleContract_left",saleContract_left_1);

        return view(layout,"saleContract/saleContractCount_totalReportForm",model);
    }

    /**
     * 开票统计 下载
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax__billing_down")
    public void billingDown(HttpServletRequest request,HttpServletResponse response,BillingCriteria criteria){

        String beginTime = request.getParameter("billing_beginTime");
        String endTime = request.getParameter("billing_endTime");
        String taxType = request.getParameter("billing_taxType");
        String name = request.getParameter("billing_name");
        String branchCompany = request.getParameter("billing_branchCompany");
        String jiafangName = request.getParameter("billing_jiafangName");

        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            criteria.setBe_time(DateHelper.parse(beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(endTime))
            criteria.setEnd_time(DateHelper.parse(endTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(taxType))
            criteria.setTaxType(URI.deURI(taxType));
        if (!StringUtils.isNullOrWhiteSpace(name))
            criteria.setName(URI.deURI(name));
        if (!StringUtils.isNullOrWhiteSpace(branchCompany))
            criteria.setBranchCompany(URI.deURI(branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(jiafangName))
            criteria.setJiaFangName(URI.deURI(jiafangName));

        PagedResult<BillingDto> pagedResult = billingService.fingPages(criteria);

        List<BillingDto> list=pagedResult.getItems();
        List<BillingDtoModel> billingDtos = new ArrayList<>();
        for (BillingDto billingDto:list){
            BillingDtoModel dto = new BillingDtoModel();
            dto.setBillingMoney(billingDto.getBillingMoney());
            dto.setBillingDate(billingDto.getBillingDate());
            dto.setTaxType(billingDto.getTaxType());
            dto.setNum(billingDto.getSaleContractDto().getNum());
            dto.setName(billingDto.getSaleContractDto().getName());
            dto.setBranchCompany(billingDto.getSaleContractDto().getBranchCompany());
            dto.setBillingPerson(billingDto.getBillingPerson());
            dto.setJiafangName(billingDto.getJiafangName());
            dto.setRemark(billingDto.getRemark());

            billingDtos.add(dto);
        }


        ExpotExcel<BillingDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同名称","所属合同编号","所属分公司","开票金额","开票日期","计税方式","开票人","甲方名称","备注"};
        expotExcel.doExportExcel("销售合同开票统计表",header,billingDtos,"yyyy-MM-dd",response);
    }

    /**
     * 销售合同统计表 下载
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax__saleContract_down")
    public void saleContractDown(HttpServletRequest request,HttpServletResponse response,SaleContractCriteria criteria){

        String saleContract_beginTime = request.getParameter("saleContract_beginTime");
        String saleContract_endTime = request.getParameter("saleContract_endTime");
        String saleContract_branchCompany = request.getParameter("saleContract_branchCompany");//已经改为分公司id
        String saleContract_jiafangName = request.getParameter("saleContract_jiafangName");
        String saleContract_contractType = request.getParameter("saleContract_contractType");
        String saleContract_projectMajor = request.getParameter("saleContract_projectMajor");
        String saleContract_contractStatus = request.getParameter("saleContract_contractStatus");

        if (!StringUtils.isNullOrWhiteSpace(saleContract_beginTime))
            criteria.setBeginTime(DateHelper.parse(saleContract_beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_endTime))
            criteria.setEndTime(DateHelper.parse(saleContract_endTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_branchCompany))
            criteria.setCompanyId(Long.parseLong(saleContract_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_jiafangName))
            criteria.setJiaFangName(URI.deURI(saleContract_jiafangName));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_contractType))
            criteria.setContractType(URI.deURI(saleContract_contractType));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_projectMajor))
            criteria.setProjectMajor(URI.deURI(saleContract_projectMajor));
        if (!StringUtils.isNullOrWhiteSpace(saleContract_contractStatus))
            criteria.setContractStatus(URI.deURI(saleContract_contractStatus));

        criteria.setPageSize(1000000000);
        PagedResult<SaleContractDtoModel> pagedResult = saleContractService.findPages(criteria);
        List<SaleContractDtoModel> list = pagedResult.getItems();
        List<SaleContractDownModel> saleContractDtoModels = new ArrayList<>();

        for (SaleContractDtoModel model:list){
            SaleContractDownModel dtoModel = DtoFactory.convert(model,new SaleContractDownModel());
            saleContractDtoModels.add(dtoModel);
        }


        ExpotExcel<SaleContractDownModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同编号","合同名称","合同金额","定审金额","所属分公司","甲方","工程专业","合同类型","中标通知书",
                                        "施工许可证","竣工验收单","工程结算单","合同签订日期","合同结束日期","剩余天数提醒","合同状态",
                                        "开票统计","收款统计","施工产值","登记人","备注"};
        expotExcel.doExportExcel("销售合同统计表",header,saleContractDtoModels,"yyyy-MM-dd",response);
    }

    /**
     * 销售合同收款统计表 下载
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax__gathering_down")
    public void gatheringDown(HttpServletRequest request,HttpServletResponse response,GatheringCriteria criteria){
        String beTime = request.getParameter("beTime");
        String enTime = request.getParameter("enTime");

        if (!StringUtils.isNullOrWhiteSpace(beTime))
            criteria.setBeginTime(DateHelper.parse(beTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(enTime))
            criteria.setEndTime(DateHelper.parse(enTime,"yyyy-MM-dd"));

        String gathering_name = request.getParameter("gathering_name");
        String gathering_branchCompany = request.getParameter("gathering_branchCompany");
        String gathering_jiaFangName = request.getParameter("gathering_jiaFangName");

        if (!StringUtils.isNullOrWhiteSpace(gathering_name))
            criteria.setName(URI.deURI(gathering_name));
        if (!StringUtils.isNullOrWhiteSpace(gathering_branchCompany))
            criteria.setBranchCompany(URI.deURI(gathering_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(gathering_jiaFangName))
            criteria.setJiaFangName(URI.deURI(gathering_jiaFangName));

        PagedResult<GatheringDto> pageResult = gatheringService.fingPages(criteria);

        List<GatheringDto> list = pageResult.getItems();
        List<GatheringDtoModel> models = new ArrayList<>();
        for (GatheringDto dto:list){
            GatheringDtoModel model = new GatheringDtoModel();
            model.setGatheringMoney(dto.getGatheringMoney());
            model.setGatheringTime(dto.getGatheringTime());
            model.setNum(dto.getSaleContractDto().getNum());
            model.setName(dto.getSaleContractDto().getName());
            model.setBranchCompany(dto.getSaleContractDto().getBranchCompany());
            model.setGatheringType(dto.getGatheringType());
            model.setJiafangName(dto.getJiafangName());
            model.setGatheringPerson(dto.getGatheringPerson());
            model.setRemark(dto.getRemark());

            models.add(model);
        }


        ExpotExcel<GatheringDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同名称","所属合同编号","所属分公司","收款金额","收款日期","收款方式","甲方名称","收款人","备注"};
        expotExcel.doExportExcel("销售合同收款统计表",header,models,"yyyy-MM-dd",response);
    }

    /**
     * 销售合同施工产值统计表 下载
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_conValue_down")
    public void conValueDown(HttpServletRequest request,HttpServletResponse response,ConstructionValueCriteria criteria){
        String be_time = request.getParameter("be_time");
        String en_time = request.getParameter("en_time");

        if (!StringUtils.isNullOrWhiteSpace(be_time))
            criteria.setBeginTime(DateHelper.parse(be_time,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(en_time))
            criteria.setEndTime(DateHelper.parse(en_time,"yyyy-MM-dd"));

        String conValue_name = request.getParameter("conValue_name");
        String conValue_branchCompany = request.getParameter("conValue_branchCompany");
        String conValue_jiaFangName = request.getParameter("conValue_jiaFangName");
        if (!StringUtils.isNullOrWhiteSpace(conValue_name))
            criteria.setName(URI.deURI(conValue_name));
        if (!StringUtils.isNullOrWhiteSpace(conValue_branchCompany))
            criteria.setBranchCompany(URI.deURI(conValue_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(conValue_jiaFangName))
            criteria.setJiafangName(URI.deURI(conValue_jiaFangName));

        PagedResult<ConstructionValueDto> pageResult = constructionValueService.fingPages(criteria);

        List<ConstructionValueDto> list = pageResult.getItems();
        List<ConValueDtoModel> conValueDtoModels = new ArrayList<>();
        for (ConstructionValueDto dto:list){
            ConValueDtoModel model = new ConValueDtoModel();
            model.setBeginTime(dto.getBeginTime());
            model.setEndTime(dto.getEndTime());
            model.setNum(dto.getSaleContractDto().getNum());
            model.setName(dto.getSaleContractDto().getName());
            model.setBranchCompany(dto.getSaleContractDto().getBranchCompany());
            model.setOutputValue(dto.getOutputValue());
            model.setWirteTime(dto.getWriteDate());
            model.setWirtePerson(dto.getWritePerson());
            model.setJiafangName(dto.getSaleContractDto().getJiaFangName());
            model.setRemark(dto.getRemark());

            conValueDtoModels.add(model);
        }

        ExpotExcel<ConValueDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同名称","所属合同编号","所属分公司","开始日期","结束日期","产值","填写日期","填写人","甲方名称","备注"};
        expotExcel.doExportExcel("销售合同施工产值统计表",header,conValueDtoModels,"yyyy-MM-dd",response);
    }

    /**
     * 销售合同报表 下载
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_reportForm_down")
    public void reportFormDown(HttpServletRequest request,HttpServletResponse response,SaleContractCriteria criteria){
        String report_beginTime = request.getParameter("report_beginTime");
        String report_endTime = request.getParameter("report_endTime");
        String report_branchCompany = request.getParameter("report_branchCompany");
        String report_contractType = request.getParameter("report_contractType");
        String reprot_jiafangName = request.getParameter("reprot_jiafangName");

        if (!StringUtils.isNullOrWhiteSpace(report_beginTime))
            criteria.setBeginTime(DateHelper.parse(report_beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(report_endTime))
            criteria.setEndTime(DateHelper.parse(report_endTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(report_branchCompany))
            criteria.setBranchCompany(URI.deURI(report_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(report_contractType))
            criteria.setContractType(URI.deURI(report_contractType));
        if (!StringUtils.isNullOrWhiteSpace(reprot_jiafangName))
            criteria.setJiaFangName(URI.deURI(reprot_jiafangName));

        List<ContractReportFormDto> list = saleContractService.getReportFormByBranchCompany(criteria);

        ExpotExcel<ContractReportFormDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"所属分公司","合同总额","开票总额","收款统计","施工产值统计"};
        expotExcel.doExportExcel("销售合同施工产值统计表",header,list,"yyyy-MM-dd",response);
    }

    /**
     * 销售合同动态总报表 下载
     * @param response
     */
    @RequestMapping(value = "ajax_totalReportForm_down")
    public void totalReportFormDown(HttpServletResponse response){

        List<DynamicTotalReportFormDto> dynamicTotalReportForm = saleContractService.getDynamicTotalReportForm();
        List<TotalReportFormDtoModel> list = new ArrayList<>();

        for (DynamicTotalReportFormDto dto:dynamicTotalReportForm){
            TotalReportFormDtoModel model = new TotalReportFormDtoModel();
            model.setBranchCompany(dto.getBranch_company());

            BigDecimal thisMonthMoney = dto.getSum_month_judge_money();
            BigDecimal lastMonthMoney = dto.getSum_month_past_judge_money();


                model.setMonthMoney(thisMonthMoney);



            if (thisMonthMoney != null && lastMonthMoney != null){
                BigDecimal judge = thisMonthMoney.divide(lastMonthMoney, 4, BigDecimal.ROUND_HALF_EVEN);

                //获取格式化对象
                NumberFormat num = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                num.setMinimumFractionDigits(2);
                String money = num.format(judge);

                model.setMonthRate(money);//月增长率

            }else{
                model.setMonthRate("/");
            }

            BigDecimal thisYearMoney = dto.getSum_year_judge_money();//开票率 收款率 施工产值率 比的是年定审金额
            BigDecimal lastYearMoney = dto.getSum_year_past_judge_money();
            BigDecimal billingMoney = dto.getSum_billing_money();
            BigDecimal gatheringMoney = dto.getSum_gathering_money();
            BigDecimal conValueMoney = dto.getSum_conValue_money();


                model.setYearMoney(thisYearMoney);



            if (thisYearMoney != null && lastYearMoney != null){
                BigDecimal judge = thisYearMoney.divide(lastYearMoney, 4, BigDecimal.ROUND_HALF_EVEN);//年增长幅度

                //获取格式化对象
                NumberFormat num = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                num.setMinimumFractionDigits(2);
                String money = num.format(judge);

                model.setYearRate(money);//年增长率
            }else{
                model.setYearRate("/");
            }


                model.setBillingMoney(billingMoney);



            if (thisYearMoney != null && billingMoney != null){
                BigDecimal judge = billingMoney.divide(thisYearMoney, 4, BigDecimal.ROUND_HALF_EVEN);//开票率

                //获取格式化对象
                NumberFormat num = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                num.setMinimumFractionDigits(2);
                String money = num.format(judge);

                model.setBillingRate(money);//开票率
            }else {
                model.setBillingRate("/");
            }


                model.setGatheringMoney(gatheringMoney);


            if (thisYearMoney != null && gatheringMoney != null){
                BigDecimal judge = gatheringMoney.divide(thisYearMoney, 4, BigDecimal.ROUND_HALF_EVEN);//收款率

                //获取格式化对象
                NumberFormat num = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                num.setMinimumFractionDigits(2);
                String money = num.format(judge);

                model.setGatheringRate(money);//收款率
            }else{
                model.setGatheringRate("/");
            }


                model.setConValueMone(conValueMoney);


            if (thisYearMoney != null && conValueMoney != null){
                BigDecimal judge = conValueMoney.divide(thisYearMoney, 4, BigDecimal.ROUND_HALF_EVEN);//施工产值率

                //获取格式化对象
                NumberFormat num = NumberFormat.getPercentInstance();
                //设置百分数精确度2即保留两位小数
                num.setMinimumFractionDigits(2);
                String money = num.format(judge);

                model.setConValueRate(money);//施工产值率
            }else{
                model.setConValueRate("/");
            }

            list.add(model);

        }


        ExpotExcel<TotalReportFormDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"所属分公司","本月累计销售合同额  /万元","当月同期增长幅度","今年累计销售合同额  /万元","年度同期增长幅度","开票统计  /万元",
                                        "开票率","收款统计  /万元","收款率","施工产值  /万元","施工产值率"};
        expotExcel.doExportExcel("销售合同动态总报表",header,list,"yyyy-MM-dd",response);

    }

    /**
     * 销售合同报表 查询
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_sc_index_reportForm")
    @ResponseBody
    public ListResult queryContractReportForm(HttpServletRequest request,SaleContractCriteria criteria){

        String report_beginTime = request.getParameter("report_beginTime");
        String report_endTime = request.getParameter("report_endTime");
        String report_branchCompany = request.getParameter("report_branchCompany");//此处改为公司id
        String report_contractType = request.getParameter("report_contractType");
        String reprot_jiafangName = request.getParameter("reprot_jiafangName");

        if (!StringUtils.isNullOrWhiteSpace(report_beginTime))
            criteria.setBeginTime(DateHelper.parse(report_beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(report_endTime))
            criteria.setEndTime(DateHelper.parse(report_endTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(report_branchCompany))
            criteria.setCompanyId(Long.parseLong(report_branchCompany));
        if (!StringUtils.isNullOrWhiteSpace(report_contractType))
            criteria.setContractType(URI.deURI(report_contractType));
        if (!StringUtils.isNullOrWhiteSpace(reprot_jiafangName))
            criteria.setJiaFangName(URI.deURI(reprot_jiafangName));

        List<ContractReportFormDto> contractReportFormDtos = saleContractService.getReportFormByBranchCompany(criteria);


        return new ListResult(contractReportFormDtos);
    }

    /**
     * 销售合同动态总报表 数据量比较大
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_sc_index_totalReportForm")
    @ResponseBody
    public ListResult queryContractTotalReportForm(HttpServletRequest request){


        List<DynamicTotalReportFormDto> mapList=saleContractService.getDynamicTotalReportForm();



        return new ListResult(mapList);
    }



}
