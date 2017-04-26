package com.sunesoft.ancon.webapp.controller.parameter;

import com.sunesoft.ancon.core.parameter.application.ParameterService;
import com.sunesoft.ancon.core.parameter.application.ParameterTypeService;
import com.sunesoft.ancon.core.parameter.application.criteria.ParameterCriteria;
import com.sunesoft.ancon.core.parameter.application.criteria.ParameterTypeCriteria;
import com.sunesoft.ancon.core.parameter.application.dtos.ParameterDto;
import com.sunesoft.ancon.core.parameter.application.dtos.ParameterTypeDto;
import com.sunesoft.ancon.core.uAuth.application.criteria.DeptmentCriteria;
import com.sunesoft.ancon.core.uAuth.application.criteria.RoleCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import com.sunesoft.ancon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ006 on 2016/6/1.
 */
@Controller
public class ParameterController extends Layout {

    @Autowired
    ParameterService parameterService;

    @Autowired
    ParameterTypeService parameterTypeService;

    @RequestMapping(value = "sra_parameter")
    public ModelAndView sra_parameter(Model modal, HttpServletRequest request, HttpServletResponse response) {
        modal.addAttribute("ptcheck",true);
        return view(layout, "uAuth/paramtype", modal);
    }

    @RequestMapping(value = "ajax_parameter_query_list")
    public void ajax_parameter_query_list(ParameterCriteria criteria, HttpServletRequest request, HttpServletResponse response) {

        PagedResult PagedResult = parameterService.FindParam(criteria);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value="_addParameterForm",method=RequestMethod.POST)
    public ModelAndView _addParameterForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("ids");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            ParameterDto parameterDto=parameterService.getById(Long.parseLong(id));
            model.addAttribute("bean",parameterDto);
        }
        List<ParameterTypeDto> typeDtoList=parameterTypeService.getAllParameterType("");
        model.addAttribute("parameterTypeDto",typeDtoList);
        return view("uAuth/_addParameterForm", model);
    }

    @RequestMapping(value = "ajax_add_update_parameter", method = RequestMethod.POST)
    public void ajax_add_update_parameter(ParameterDto parameterDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            parameterDto.setId(Long.parseLong(id));
            parameterService.updateParameter(parameterDto);
            commonResult=new CommonResult(true,"更新成功");

        }else {
            commonResult =new CommonResult(parameterService.addParameter(parameterDto)>0?true:false,"添加成功");
        }
        if(commonResult==null){
            commonResult=new CommonResult(true,"ddd");
        }
        String json=JsonHelper.toJson(commonResult);

        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_delete_parameter")
    @ResponseBody
    public CommonResult ajax_delete_parameter(HttpServletRequest request){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        parameterService.deleteParameter(listId.toArray(new Long[listId.size()]));
        return new CommonResult(true);
    }

    @RequestMapping(value = "sra_parameterType")
    public ModelAndView sra_parameterType(Model modal, HttpServletRequest request, HttpServletResponse response) {
        modal.addAttribute("ptecheck",true);
        return view(layout, "uAuth/parameterTypeManage", modal);
    }

    @RequestMapping(value = "ajax_TypeManage_query_list")
    public void ajax_TypeManage_query_list(ParameterTypeCriteria criteria, HttpServletRequest request, HttpServletResponse response) {

        PagedResult PagedResult = parameterTypeService.FindParam(criteria);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value="_addTypeManageForm",method=RequestMethod.POST)
    public ModelAndView _addTypeManageForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            ParameterTypeDto parameterTypeDto=parameterTypeService.getById(Long.parseLong(id));
            model.addAttribute("bean",parameterTypeDto);
        }
        return view("uAuth/_addParameterTypeForm", model);
    }

    @RequestMapping(value = "ajax_add_update_typeManage", method = RequestMethod.POST)
    public void ajax_add_update_typeManage(ParameterTypeDto parameterDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            parameterDto.setId(Long.parseLong(id));
            parameterTypeService.updateParameterType(parameterDto);
            commonResult=new CommonResult(true,"更新成功");
        }else {
            commonResult =new CommonResult(parameterTypeService.addParameterType(parameterDto)>0?true:false,"添加成功");
        }
        if(commonResult==null){
            commonResult=new CommonResult(true,"ddd");
        }
        String json=JsonHelper.toJson(commonResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_delete_parameterTypeManage")
    @ResponseBody
    public CommonResult ajax_delete_parameterTypeManage(HttpServletRequest request){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        parameterTypeService.deleteParameterType(listId.toArray(new Long[listId.size()]));
        return new CommonResult(true);
    }


}
