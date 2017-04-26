package com.sunesoft.ancon.webapp.controller.uAuth;

import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.SysPermissionGroupService;
import com.sunesoft.ancon.core.uAuth.application.SysRoleService;
import com.sunesoft.ancon.core.uAuth.application.criteria.DeptmentCriteria;
import com.sunesoft.ancon.core.uAuth.application.criteria.RoleCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.PermissionGroupDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.RoleDto;
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
 * Created by zhouz on 2016/5/19.
 */
@Controller
public class DeptController extends Layout {

    @Autowired
    SysPermissionGroupService sysPermissionGroupService;
    @Autowired
    SysRoleService sysRoleService;


    @Autowired
    DeptmentService deptmentService;

    @RequestMapping(value = "sra_u_dept")
    public ModelAndView userInfos(Model model) {

        model.addAttribute("ucheck",3);
        return view(layout, "uAuth/dept", model);
    }

    @RequestMapping(value = "ajax_dept_query_list")
    public void deptQuery_PageList(DeptmentCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("deptName");
        if (!StringUtils.isNullOrWhiteSpace(name))
            criteria.setDeptName(URI.deURI(name));
        PagedResult PagedResult =deptmentService.findDeptsPaged(criteria);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value="_addDeptForm")
    public ModelAndView addMenuForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            List<Long> ids=new ArrayList<>();
            ids.add(Long.parseLong(id));
            List<DeptmentDto> deptmentDtos = deptmentService.getByDeptsIds(ids);

            model.addAttribute("bean", deptmentDtos.get(0));
        }
        DeptmentCriteria deptmentCriteria=new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        deptmentCriteria.setPageSize(100);
        PagedResult<DeptmentDto> pagedResult=deptmentService.findDeptsPaged(deptmentCriteria);
        if(pagedResult.getItems().size()>0) {
            model.addAttribute("permGroup", pagedResult.getItems());
        }else{
            model.addAttribute("permGroup", null);
        }
        return view("uAuth/_addDeptForm", model);
    }

    @RequestMapping(value = "ajax_add_update_dept", method = RequestMethod.POST)
    public void addOrUpdateUser(DeptmentDto deptmentDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String hidName = request.getParameter("hidName");
        if (!StringUtils.isNullOrWhiteSpace(hidName))
            deptmentDto.setChargeLeaderName(hidName);

        CommonResult commonResult=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            deptmentDto.setId(Long.parseLong(id));
            commonResult=deptmentService.updateDept(deptmentDto);

        }else {
            commonResult = deptmentService.addDept(deptmentDto);
        }
        if(commonResult==null){
            commonResult=new CommonResult(true,"ddd");
        }
        String json=JsonHelper.toJson(commonResult);

        AjaxResponse.write(response,json);
    }

    @RequestMapping(value="ajax_deleteDept")
    @ResponseBody
    public CommonResult ajax_deleteMenu(HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        if (listId.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的用户");
            return new CommonResult(false,"请选择要操作的用户");
        } else {
            CommonResult commonResult = deptmentService.deleteDept(listId);
            return commonResult;
        }
    }

}
