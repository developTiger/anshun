package com.sunesoft.ancon.webapp.controller.inContract;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.inContract.application.InContractService;
import com.sunesoft.ancon.core.inContract.application.InvoiceService;
import com.sunesoft.ancon.core.inContract.application.criteria.InvoiceCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.application.dto.InvoiceDto;
import com.sunesoft.ancon.core.inContract.domain.InvoiceType;
import com.sunesoft.ancon.core.inContract.domain.RateType;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.SysRoleService;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.fr.utils.excel.ExpotExcel;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.model.InvoiceDtomodel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 开票的交互层
 * Created by xiazl on 2016/11/24.
 */
@Controller
public class InvoiceController extends Layout {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    InContractService inContractService;
    @Autowired
    UserSession us;
    @Autowired
    SecondPartyService secondPartyService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    DataResource deptInt;

    /**
     * 增加或者修改开票的操作
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_invoice")
    @ResponseBody
    public CommonResult updateInvoice(InvoiceDto dto, HttpServletRequest request, HttpServletResponse response) {
        //todo 判断是否登录 否则跳转到登录页面
        CommonResult result = null;
        UserSessionDto user = us.getCurrentUser(request);
        String rateType1 = request.getParameter("rateType");
        RateType rateType = RateType.valueOf(rateType1);
        dto.setRateType(rateType);
        //发票类型
        String type1 = request.getParameter("type");
        InvoiceType type = InvoiceType.valueOf(type1);
        dto.setType(type);
        String id = request.getParameter("id");
        if (StringUtils.isNullOrWhiteSpace(id)) {
            //是增加
            dto.setOptionName(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            dto.setEdtitor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            result = invoiceService.create(dto);
        } else {
            //修改
            dto.setEdtitor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            result = invoiceService.edit(dto);
        }
        return result;
    }

    /**
     * 用于删除开票
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteInvoice")
    @ResponseBody
    public CommonResult deleteInvoice(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isNullOrWhiteSpace(id)) {
            return new CommonResult(false, "请选择发票");
        }
        return invoiceService.delete(Long.parseLong(id));

    }

    /**
     * 发票开票查询列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_query_invoice_page")
    public void getInvoicePage(InvoiceCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String end = request.getParameter("endTime1");
        String begin = request.getParameter("beginTime1");
        String type = request.getParameter("invoiceType");
        String partyBId = request.getParameter("partyBId");
        String contract = request.getParameter("contract");
        String id = request.getParameter("inContractId");
        Boolean b = false;
        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            criteria.setPartyBId(Long.parseLong(partyBId));
        }
        if (!StringUtils.isNullOrWhiteSpace(contract)) {
            criteria.setContract(URI.deURI(contract));
        }
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            criteria.setId(Long.parseLong(id));
            InContractDto  dto = inContractService.getById(Long.parseLong(id));
            Map<String, ResourceDto> map = deptInt.resources("sra_in_index", dto, request);
            if(map.size()>0) {
                for (Map.Entry<String, ResourceDto> entry : map.entrySet()) {
                    ResourceDto resourceDto = entry.getValue();
                    if (resourceDto.getUrl().equals("login_Billing") && resourceDto.getResource_operate().equals("edit")) {
                        b = true;
                    }
                }
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBeginTime(begin);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(type)) {
            criteria.setType(InvoiceType.valueOf(type.trim()));
        }
        PagedResult<InvoiceDto> pg = invoiceService.pg(criteria);
        if (b) {
            pg.setOprator("operate");
        }
        String json = JsonHelper.toJson(pg);
        AjaxResponse.write(response, json);
    }

    /**
     * 打开开票的增加或者修改的弹窗
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "addOrUpdate_invoice_modal")
    public ModelAndView getInvoiceModal(HttpServletRequest request, Model model) {
        String id = request.getParameter("inContractId");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            InContractDto dto = inContractService.getById(Long.parseLong(id));
            model.addAttribute("inContract", dto);
            model.addAttribute("nowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd").trim());
        }
        String inVoiceId = request.getParameter("invoice_id");
        if (!StringUtils.isNullOrWhiteSpace(inVoiceId)) {
            InvoiceDto invoiceDto = invoiceService.getById(Long.parseLong(inVoiceId));
            model.addAttribute("been", invoiceDto);
        }
        return view("inContract/_addOrUpdateInvoice", model);

    }

    /**
     * 查询开票统计列表的页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "sra_q_v")
    public ModelAndView query_invoiceList(Model model, HttpServletRequest request) {
        List<ContractorPartyDto> secondPartyDtos = secondPartyService.getByTypeAll(1);
        model.addAttribute("secondList", secondPartyDtos);

        //数据报表 左侧菜单
        String saleContract_left_1 = "11";
        model.addAttribute("saleContract_left", saleContract_left_1);
        String beginTime = request.getParameter("beginTime");
        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            model.addAttribute("beginTime", beginTime);
        String endTime = request.getParameter("endTime");
        if (!StringUtils.isNullOrWhiteSpace(endTime))
            model.addAttribute("endTime", endTime);
        return view(layout, "inContract/invoiceList", model);

    }

    /**
     * 开票统计的柱状图
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "query_invoice_count")
    public void query_invoice_count(HttpServletRequest request, HttpServletResponse response) {
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
//        if(StringUtils.isNullOrWhiteSpace(beginTime)){
//            beginTime="1900-01-01";
//        }
//        if(StringUtils.isNullOrWhiteSpace(endTime)){
//            Date date=DateHelper.addYear(new Date(),100);
//            endTime=DateHelper.formatDate(date);
//        }
        List<Map<String, Object>> list = invoiceService.getCount(beginTime, endTime);
        AjaxResponse.write(response, JsonHelper.toJson(list));

    }

    @RequestMapping(value = "ajax_downLoad_invoice")
    public void downLoad_invoice(HttpServletResponse response, HttpServletRequest request, InvoiceCriteria criteria) {

        String end = request.getParameter("endTime1");
        String begin = request.getParameter("beginTime1");
        String type = request.getParameter("invoiceType");
        String partyBId = request.getParameter("partyBId");
        String contract = request.getParameter("contract");

        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            criteria.setPartyBId(Long.parseLong(partyBId));
        }
        if (!StringUtils.isNullOrWhiteSpace(contract)) {
            criteria.setContract(contract);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBeginTime(begin);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(type)) {
            criteria.setType(InvoiceType.valueOf(type.trim()));
        }
        List<InvoiceDto> invoiceDtos = invoiceService.pg(criteria).getItems();
        List<InvoiceDtomodel> list = new ArrayList<>();
        if (invoiceDtos != null && invoiceDtos.size() > 0) {
            for (InvoiceDto dto : invoiceDtos) {
                InvoiceDtomodel model = new InvoiceDtomodel();
                model.setMoney(dto.getMoney());
                model.setRemark(dto.getRemark());
                model.setInName(dto.getInName());
                model.setInNum(dto.getInNum());
//                model.setTaxPoint(dto.getRate());
                model.setBillType(dto.getTypeName());
                model.setPartyB(dto.getPartyB());
                model.setBooker(dto.getOptionName());
                model.setBillDate(dto.getSinvoiceDate());
                list.add(model);
            }
        }
        ExpotExcel<InvoiceDtomodel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{ "所属合同名称","所属合同编号", "发票金额（万元）", "发票类型",  "乙方名称","开票日期", "发票登记人", "备注"};
        expotExcel.doExportExcel("进项合同开票统计表", header, list, "yyyy-MM-dd", response);


    }


}
