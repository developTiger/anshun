package com.sunesoft.ancon.webapp.controller.inContract;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.core.inContract.application.InContractService;
import com.sunesoft.ancon.core.inContract.application.criteria.InContractCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.dtos.InSimpleSaleContractDto;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.SysRoleService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
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
import com.sunesoft.ancon.webapp.model.InContractDtoModel;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 进项合同的交互层
 * Created by xiazl on 2016/11/24.
 */
@Controller
public class InContractController extends Layout {

    @Autowired
    InContractService inContractService;
    @Autowired
    UserSession us;

    @Autowired
    SecondPartyService secondPartyService;
    @Autowired
    SaleContractService saleContractService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    DataResource deptInt;

    /**
     * 进项合同的基本信息页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "sra_in_index")
    public ModelAndView inContrcat_index(Model model, HttpServletRequest request) {
        //todo 判断是否可以新建合同，新建发票，付款
        Map<String, ResourceDto> op=deptInt.resources("sra_in_index", null, request);

        if(op.size()>0) {
            for (Map.Entry<String, ResourceDto> item : op.entrySet()) {
                ResourceDto dto = item.getValue();
                if(dto.getUrl().equals("add_contract")&&dto.getResource_operate().equals("edit")){
                    model.addAttribute(dto.getUrl(), dto.getResource_operate());
                }

            }
        }
        List<ContractorPartyDto> secondPartyDtos = secondPartyService.getByTypeAll(1);
        model.addAttribute("secondPartyDtos", secondPartyDtos);
        String inContract_pid = request.getParameter("inContract_pid");
        if (!StringUtils.isNullOrWhiteSpace(inContract_pid)) {
            model.addAttribute("List_inContractPid", inContract_pid);
        }
        String inContract = request.getParameter("inContract_id");
        if (!StringUtils.isNullOrWhiteSpace(inContract))
            model.addAttribute("List_inContractId", inContract);


//todo new
        String inContract_id = request.getParameter("inContract_id");
        String inContract_companyId = request.getParameter("inContract_pid");
        if (!StringUtils.isNullOrWhiteSpace(inContract_id))
            model.addAttribute("inContract_add_id", inContract_id);
        if (!StringUtils.isNullOrWhiteSpace(inContract_companyId))
            model.addAttribute("inContract_add_companyId", inContract_companyId);
//cyn
        //新增 id和companyId 链接到当前新增的合同 进行展示
//        String inContract_add_id = request.getParameter("inContract_add_id");
//        String inContract_add_companyId = request.getParameter("inContract_add_companyId");
//        if (!StringUtils.isNullOrWhiteSpace(inContract_add_companyId))
//            model.addAttribute("inContract_add_id", inContract_add_id);
//        if (!StringUtils.isNullOrWhiteSpace(inContract_add_companyId))
//            model.addAttribute("inContract_add_companyId", inContract_add_companyId);


        return view(layout, "inContract/inContract_index", model);
    }

    /**
     * 进项合同的增加或者修改的弹窗 i_model
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "_editOrUpdate_contract")
    public ModelAndView edit(HttpServletRequest request, Model model) {
//        Long d1=(new Date()).getTime();
        //todo 获取所有乙方名称
        List<DeptmentDto> firstPartyDtos = deptmentService.getAllDeptCommpany();
        model.addAttribute("companys", firstPartyDtos);
        //todo 获取销售合同列表
        List<InSimpleSaleContractDto> saleContracts = saleContractService. getInSimpleAll();
        model.addAttribute("saleContracts", saleContracts);
        String id = request.getParameter("id");
        String companyId = request.getParameter("companyId");

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            InContractDto dto = inContractService.getById(Long.parseLong(id));
            model.addAttribute("been", dto);
        }else {
            if (!StringUtils.isNullOrWhiteSpace(companyId)) {
                ContractorPartyDto secondPartyDto = secondPartyService.getById(Long.parseLong(companyId));
                UserSessionDto user = us.getCurrentUser(request);
                InContractDto dto = new InContractDto();
                dto.setPartyB(secondPartyDto.getName());
                dto.setPartyBId(secondPartyDto.getId());
                dto.setSbillDate(DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
                dto.setCompanyId(user.getCompanyId());
                dto.setCompany(user.getCompanyName());
                model.addAttribute("been", dto);
            }
        }
//        Long d2=(new Date()).getTime();
//        Long dd=(d2-d1)/1000;
//        Long ddd=d2-d1;
        return view("inContract/_addOrUpdateInContract", model);

    }



    /**
     * 进项合同的增加或修改 的操作
     *
     * @param request
     * @param dto
     * @param response
     * @return
     */
    @RequestMapping(value = "ajax_addOrUpdate_inContract")
    @ResponseBody
    public CommonResult ajax_addOrUpdate_inContract(HttpServletRequest request, InContractDto dto, HttpServletResponse response) {
        UserSessionDto user = us.getCurrentUser(request);
        String id = request.getParameter("id");
        CommonResult result = null;
        //判断是否有该公司的权限
        DeptmentDto deptmentDto = deptmentService.getByDeptId(dto.getCompanyId());
        // 1:部门  2:独立分公司 3: 非独立分公司
        if(deptmentDto.getType()==2&& dto.getCompanyId()!=user.getCompanyId()){
            return new CommonResult(false,"您无该独立分公司权限");
        }else if(deptmentDto.getType()!=2&& user.getType()==2){
            return new CommonResult(false,"您只有本公司的权限!");
        }else {
            if (StringUtils.isNullOrWhiteSpace(id)) {
                //增加
                dto.setOptionName(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
                dto.setEditor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
                result = inContractService.create(dto);
            } else {
                //修改
                dto.setEditor(StringUtils.isNullOrWhiteSpace(user.getName()) ? user.getLoginName() : user.getName());
                result = inContractService.edit(dto);
            }
            return result;
        }
    }

    /**
     * 进项合同列表 异步查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_query_inContract_page")
    public void getIncontractPage(InContractCriteria contractCriteria, HttpServletRequest request, HttpServletResponse response) {
        String end = request.getParameter("endTime");
        String begin = request.getParameter("beginTime");
        String partyBId = request.getParameter("partyBId");
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            contractCriteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            contractCriteria.setBeginTime(begin);
        }
        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            contractCriteria.setPartyBId(Long.parseLong(partyBId));
        }

        PagedResult<InContractDto> pg = inContractService.pg(contractCriteria);

        String json = JsonHelper.toJson(pg);
        AjaxResponse.write(response, json);

    }


    /**
     * 获取进项合同统计列表的页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "sra_q_i")
    public ModelAndView query_inContractList(Model model, HttpServletRequest request) {
        List<ContractorPartyDto> secondPartyDtos = secondPartyService.getByTypeAll(1);
        model.addAttribute("secondList", secondPartyDtos);

        //数据报表 左侧菜单
//        String saleContract_left_1 = "9";
//        model.addAttribute("saleContract_left", saleContract_left_1);
//        String beginTime = request.getParameter("beginTime");
//        if (!StringUtils.isNullOrWhiteSpace(beginTime))
//            model.addAttribute("beginTime", beginTime);
//        String endTime = request.getParameter("endTime");
//        if (!StringUtils.isNullOrWhiteSpace(endTime))
//            model.addAttribute("endTime", endTime);
        model.addAttribute("check_q_i",true);
        return view(layout, "inContract/inContract_list", model);

    }

    /**
     * 获取单个进项合同
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_single_inContract")
    @ResponseBody
    public InContractDto signle_inContract(HttpServletRequest request) {
        String id = request.getParameter("inContractId");
        InContractDto dtot=inContractService.getById(Long.parseLong(id));

        Map<String, ResourceDto> op=deptInt.resources("sra_in_index", dtot, request);
        if(op.size()>0) {
            for (Map.Entry<String, ResourceDto> item : op.entrySet()) {
                ResourceDto dto = item.getValue();
                if (dto.getUrl().equals("edit_contract") && dto.getResource_operate().equals("edit")) {
                    dtot.setCanEdit(1);
                }
                if (dto.getUrl().equals("login_payable") && dto.getResource_operate().equals("edit")) {
                    dtot.setCanP(1);
                }
                if (dto.getUrl().equals("login_Billing") && dto.getResource_operate().equals("edit")) {
                    dtot.setCanV(1);
                }
            }
        }

        return dtot;
    }

    /**
     * 进项合同 柱状图用到
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "query_inContract_count")
    @ResponseBody
    public void query_inContract_count(HttpServletRequest request, HttpServletResponse response) {
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        List<Map<String, Object>> list = inContractService.getCount(beginTime, endTime);
        AjaxResponse.write(response, JsonHelper.toJson(list));

    }

    /**
     * 进项合同、付款记录和开票的统计（柱状图）
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_i_c")
    public ModelAndView inContract_pay_invoice_count(Model model) {
        Date d1 = DateHelper.addMonth(new Date(), -1);
        model.addAttribute("beforeTime", DateHelper.formatDate(d1, "yyyy-MM-dd"));
        model.addAttribute("nowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        //数据报表 左侧菜单
        String saleContract_left_1 = "8";
        model.addAttribute("saleContract_left", saleContract_left_1);
        model.addAttribute("check_i_c",true);

        return view(layout, "inContract/inContract_count_lineChart", model);
    }

    /**
     * 获取到所有进项合同
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_query_partyB_inContracts")
    @ResponseBody
    public void query_partyB_inContracts(HttpServletRequest request, HttpServletResponse response) {
        List<InContractDto> dtos = inContractService.getAll();
        AjaxResponse.write(response, JsonHelper.toJson(dtos));
    }

    /**
     * 进项合同统计列表导出
     *
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_downLoad_inContract")
    public void downLoad_inContract(HttpServletRequest request, HttpServletResponse response, InContractCriteria criteria) {
        String end = request.getParameter("endTime");
        String begin = request.getParameter("beginTime");
        String partyBId = request.getParameter("partyBId");
        if (!StringUtils.isNullOrWhiteSpace(end)) {
            criteria.setEndTime(end);
        }
        if (!StringUtils.isNullOrWhiteSpace(begin)) {
            criteria.setBeginTime(begin);
        }
        if (!StringUtils.isNullOrWhiteSpace(partyBId)) {
            criteria.setPartyBId(Long.parseLong(partyBId));
        }
        List<InContractDto> inContractDtos = inContractService.pg(criteria).getItems();
        List<InContractDtoModel> list = new ArrayList<>();
        if (inContractDtos != null && inContractDtos.size() > 0) {
            for (InContractDto dto : inContractDtos) {
                InContractDtoModel model = new InContractDtoModel();
                model.setBillDate(dto.getSbillDate());
                model.setMoney(dto.getValue());
                model.setPayMoney(dto.getPayMoney());
                model.setBillMoney(dto.getBillMoney());
                model.setPartyB(dto.getPartyB());
                model.setEndDate(dto.getSendDate());
                model.setName(dto.getName());
                model.setNum(dto.getNum());
                model.setBooker(dto.getOptionName());
                model.setRemark(dto.getRemark());
                if (dto.getNextDays() >= 0) {
                    model.setNewDays(String.valueOf(dto.getNextDays()));
                } else {
                    model.setNewDays("已逾期");
                }
                list.add(model);
            }
        }
        ExpotExcel<InContractDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同名称","合同编号", "合同金额（万元）", "已付款（万元）", "已开发票（万元）","乙方", "合同签订日期", "合同到期日期", "剩余天数", "登记人", "备注"};
        expotExcel.doExportExcel("进项合同统计表", header, list, "yyyy-MM-dd", response);
    }


}
