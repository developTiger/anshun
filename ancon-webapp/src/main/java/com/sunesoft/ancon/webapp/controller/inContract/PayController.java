
package com.sunesoft.ancon.webapp.controller.inContract;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.inContract.application.InContractService;
import com.sunesoft.ancon.core.inContract.application.PayService;
import com.sunesoft.ancon.core.inContract.application.criteria.PayCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.application.dto.PayDto;
import com.sunesoft.ancon.core.inContract.domain.PayStyle;
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
import com.sunesoft.ancon.webapp.model.PayDtoModel;
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
 * 付款的交互层
 * Created by xiazl on 2016/11/24.
 */
@Controller
public class PayController extends Layout {

    @Autowired
    PayService payService;
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
     * 增加或者修改付款的操作
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_pay")
    @ResponseBody
    public CommonResult ajax_addOrUpdate_pay(PayDto dto, HttpServletRequest request) {
        UserSessionDto user = us.getCurrentUser(request);
        CommonResult commonResult = null;
        String payStyle = request.getParameter("payStyle");
        PayStyle style = PayStyle.valueOf(payStyle);
        dto.setPayStyle(style);
        String id = request.getParameter("id");
        if (StringUtils.isNullOrWhiteSpace(id)) {
            //add
            dto.setOptionName(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            dto.setEditor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            commonResult = payService.create(dto);
        } else {
            //update
            dto.setEditor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
            commonResult = payService.edit(dto);
        }
        return commonResult;
    }

    /**
     * 删除付款的操作
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deletePay")
    @ResponseBody
    public CommonResult deleteInvoice(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isNullOrWhiteSpace(id))
            return new CommonResult(false, "请选择付款记录");
        return payService.delete(Long.parseLong(id));

    }

    /**
     * 付款查询列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_query_pay_page")
    public void getPayPage(PayCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String end = request.getParameter("endTime2");
        String begin = request.getParameter("beginTime2");
        String id = request.getParameter("inContractId");
        Boolean result = false;
        String partyBId = request.getParameter("partyBId");

        String contract = request.getParameter("contract");

        if (!StringUtils.isNullOrWhiteSpace(contract)) {
            criteria.setContractName(URI.deURI(contract));
        }
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            criteria.setId(Long.parseLong(id));
            InContractDto dto = inContractService.getById(Long.parseLong(id));
            Map<String, ResourceDto> map = deptInt.resources("sra_in_index", dto, request);
            if(map.size()>0) {
                for (Map.Entry<String, ResourceDto> entry : map.entrySet()) {
                    ResourceDto resourceDto = entry.getValue();
                    if (resourceDto.getUrl().equals("login_payable") && resourceDto.getResource_operate().equals("edit")) {
                        result = true;
                    }
                }
            }
        }
        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            //todo partyB
            criteria.setPartyBId(Long.parseLong(partyBId));
        }
        String payStyle = request.getParameter("payStyle");
        if (!StringUtils.isNullOrWhiteSpace(payStyle)) {
            //todo payStyle
            criteria.setPayStyle(PayStyle.valueOf(payStyle));
        }
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            criteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBeginTime(begin);
        }
        PagedResult<PayDto> pg = payService.pg(criteria);
        if (result) {
            pg.setOprator("operate");
        }
        String json = JsonHelper.toJson(pg);
        AjaxResponse.write(response, json);
    }

    /**
     * 打开付款的增加与修改的页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "addOrUpdate_pay_modal")
    public ModelAndView get_invoice_modal(HttpServletRequest request, Model model) {
        String id = request.getParameter("inContractId");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            InContractDto dto = inContractService.getById(Long.parseLong(id));
            model.addAttribute("inContract", dto);
            model.addAttribute("nowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd").trim());
        }
        String payId = request.getParameter("pay_id");
        if (!StringUtils.isNullOrWhiteSpace(payId)) {
            PayDto payDto = payService.getById(Long.parseLong(payId));
            model.addAttribute("been", payDto);
        }
        return view("inContract/_addOrUpdatePay", model);
    }

    /**
     * 付款的统计列表
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "sra_q_p")
    public ModelAndView query_payList(Model model, HttpServletRequest request) {
        List<ContractorPartyDto> secondPartyDtos = secondPartyService.getByTypeAll(1);
        model.addAttribute("secondList", secondPartyDtos);

        //数据报表 左侧菜单
        String saleContract_left_1 = "10";
        model.addAttribute("saleContract_left", saleContract_left_1);
        String beginTime = request.getParameter("beginTime");
        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            model.addAttribute("beginTime", beginTime);
        String endTime = request.getParameter("endTime");
        if (!StringUtils.isNullOrWhiteSpace(endTime))
            model.addAttribute("endTime", endTime);
        return view(layout, "inContract/payList", model);

    }

    /**
     * 付款的柱状图统计
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "query_pay_count")
    public void query_invoice_count(HttpServletRequest request, HttpServletResponse response) {
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        List<Map<String, Object>> list = payService.getCount(beginTime, endTime);
        AjaxResponse.write(response, JsonHelper.toJson(list));
    }

    /**
     * 下载付款记录，excel格式
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_downLoad_pay")
    public void downLoad_pay(PayCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String end = request.getParameter("endTime2");
        String begin = request.getParameter("beginTime2");
        String partyBId = request.getParameter("partyBId");

        String contract = request.getParameter("contract");

        if (!StringUtils.isNullOrWhiteSpace(contract)) {
            criteria.setContractName(contract);
        }
        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            criteria.setPartyBId(Long.parseLong(partyBId));
        }
        String payStyle = request.getParameter("payStyle");
        if (!StringUtils.isNullOrWhiteSpace(payStyle)) {
            criteria.setPayStyle(PayStyle.valueOf(payStyle));
        }
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            criteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBeginTime(begin);
        }
        List<PayDto> payDtos = payService.pg(criteria).getItems();
        List<PayDtoModel> list = new ArrayList<>();
        if (payDtos != null && payDtos.size() > 0) {
            for (PayDto dto : payDtos) {
                PayDtoModel model = new PayDtoModel();
                model.setInName(dto.getInName());
                model.setInNum(dto.getInNum());
                model.setMoney(dto.getMoney());
                model.setPayStyle(dto.getPayStyleName());
                model.setPartyB(dto.getPartyB());
                model.setPayTime(dto.getSpayTime());
                model.setBooker(dto.getOptionName());
                model.setRemark(dto.getRemark());
                list.add(model);
            }
        }
        ExpotExcel<PayDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"所属合同名称","所属合同编号", "付款金额（万元）", "付款方式", "乙方", "付款日期", "登记人", "备注"};
        expotExcel.doExportExcel("进项合同付款统计表", header, list, "yyyy-MM-dd", response);

    }
}
