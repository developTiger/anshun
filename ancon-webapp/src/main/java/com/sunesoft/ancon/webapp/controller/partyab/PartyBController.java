package com.sunesoft.ancon.webapp.controller.partyab;

import com.sunesoft.ancon.core.companys.application.SecondPartyService;
import com.sunesoft.ancon.core.companys.application.criteria.SecondPartyCriteria;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.sunesoft.ancon.webapp.utils.URI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Controller
public class PartyBController extends Layout {


    @Autowired
    SecondPartyService secondPartyService;

  /*  @RequestMapping(value="_deleteRoleForm")
    public void deleteRoleForm(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        List<Long> listId = new ArrayList<>();
        String[]  roleIds = id.split(",");
        for(String d : roleIds){
            listId.add(Long.parseLong(d));
        }
        if(listId.size() == 0){
            AjaxResponse.write(response,"text","请选择要操作的菜单");
        }
        else{
            System.out.print("删除要选择的数据");
            Boolean result = sysRoleService.deleteRole(listId.toArray(new Long[listId.size()]));
            AjaxResponse.write(response,"text",true?"success":"error");
        }
//
//        model.addAttribute("idTemp", id);
//        return view("uAuth/_deleteRoleForm", model);
    }*/

    @RequestMapping(value="_addPartyBForm",method=RequestMethod.POST)
    public ModelAndView _addPartyBForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            ContractorPartyDto roleDto = secondPartyService.getById(Long.parseLong(id));
            model.addAttribute("bean", roleDto);
        }

        return view("uAuth/_addPartyBForm", model);
    }

    @RequestMapping(value = "sra_partyB")
    public ModelAndView sra_partyB(Model model) {
        model.addAttribute("ucheck",2);
        return view(layout, "uAuth/partyB", model);
    }

    @RequestMapping(value = "ajax_partyB_query_list")
    public void ajax_partyB_query_list(SecondPartyCriteria criteria, HttpServletRequest request, HttpServletResponse response) {

        if(!StringUtils.isNullOrWhiteSpace(criteria.getName())){
            criteria.setName(URI.deURI(criteria.getName()));
        }
        PagedResult PagedResult = secondPartyService.findPages(criteria);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_add_update_partyB", method = RequestMethod.POST)
    public void ajax_add_update_partyB(ContractorPartyDto contractorPartyDto,HttpServletRequest request, HttpServletResponse response) {
        CommonResult result;
        if(contractorPartyDto.getId()!=null && contractorPartyDto.getId()>0) {
            result= secondPartyService.updateFirstParty(contractorPartyDto);
        }else{
            result=secondPartyService.addFirstParty(contractorPartyDto);
        }
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "_deletePartyBForm")
    @ResponseBody
    public CommonResult _deletePartyBForm(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Long> listId = new ArrayList<>();
        String[]  roleIds = id.split(",");
        for(String d : roleIds){
            listId.add(Long.parseLong(d));
        }
        if(listId.size() == 0){
            return  new CommonResult(false,"请选择要操作的菜单");
        }
        else{
            System.out.print("删除要选择的数据");
            Boolean result = secondPartyService.deletePartyB(listId.toArray(new Long[listId.size()]));
            return  new CommonResult(result);
        }
    }


}
