package com.sunesoft.ancon.webapp.controller.uAuth;

import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.core.uAuth.application.SysPermissionGroupService;
import com.sunesoft.ancon.core.uAuth.application.SysResourceService;
import com.sunesoft.ancon.core.uAuth.application.criteria.PermissionGroupCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.PermissionGroupDto;
import com.sunesoft.ancon.webapp.function.ResouceFactory;
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
 * Created by admin on 2016/5/30.
 */
@Controller
public class PermissionGroupController extends Layout {

    @Autowired
    SysPermissionGroupService sysPermissionGroupService;

    @Autowired
    SysResourceService resourceService;

    @Autowired
    ResouceFactory resouceFactory;


    @RequestMapping(value = "sra_group")
    public ModelAndView permissionGroupinfos(Model model) {
        model.addAttribute("ucheck",4);
        return view(layout, "uAuth/power", model);
    }


    @RequestMapping(value = "_addPermissionForm")
    public ModelAndView addUserForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            PermissionGroupDto a = sysPermissionGroupService.getDtoById(Long.parseLong(id));
            model.addAttribute("sysPermission", a);
        }

//        List<ResourceDto> lists = resourceService.getResources("");
//        model.addAttribute("beans", JsonHelper.toJson(lists));
//        model.addAttribute("beans",lists);
        return view("/uAuth/_addPermissionForm", model);
    }

    @RequestMapping(value = "ajax_permission_query_list")
    public void getPermissionInfos(PermissionGroupCriteria param, HttpServletRequest request, HttpServletResponse response) {

        /*int pageNo = 1;
        int pageSize = 10;
        if (p != null && !p.equals("")) {
            pageNo = Integer.parseInt(p);
        }
        if (ps != null && !ps.equals("")) {
            pageSize = Integer.parseInt(ps);
        }
        String permissionName = request.getParameter("permissionName");

        PermissionGroupCriteria criteria = new PermissionGroupCriteria();
        criteria.setPermissionGroupName(permissionName);
        criteria.setPageNumber(pageNo);
        criteria.setPageSize(pageSize);
        PagedResult result = sysPermissionGroupService.getPermissionGroupPaged(criteria);
        String json = JsonHelper.toJson(result);
        System.out.println(json);
        AjaxResponse.write(response, json);*/
        param.setPermissionGroupName(URI.deURI(param.getPermissionGroupName()));
        PagedResult pagedResult = sysPermissionGroupService.getPermissionGroupPaged(param);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_add_update_permission", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdatePermission(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("permissionName");
        String sort = request.getParameter("permissionSort");
        String menuIds = request.getParameter("permissionMenuIds");
        System.out.print("menuIds:"+menuIds);
        String id = request.getParameter("id");

        PermissionGroupDto permission = new PermissionGroupDto();
        permission.setName(name);
        permission.setSort(Integer.parseInt(sort));

        String [] pids = menuIds.split(",");
        List<Long> list=new ArrayList<>();

        for(String s :pids) {
            if(!StringUtils.isNullOrWhiteSpace(s)) {
                list.add(Long.parseLong(s));
            }
        }
        permission.setMenuIds(list);
        System.out.print(permission.getMenuIds());
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            permission.setId(Long.parseLong(id));
        }

        CommonResult commonResult=sysPermissionGroupService.addOrUpdate(permission) > 0 ? new CommonResult(true,"操作成功") :new CommonResult(false,"操作失败");
        if(commonResult.getIsSuccess()){
            resouceFactory.clear();
        }
        return commonResult;

    }

    @RequestMapping(value = "ajax_deletePermission")
    @ResponseBody
    public CommonResult deletePermission(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            return new CommonResult(false,"请选择要操作的菜单");
        } else {
            Boolean result = sysPermissionGroupService.delete(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            String msg=result ?"删除成功!!!":"删除失败!!!!";
            return new CommonResult(result,msg);

        }
    }

    @RequestMapping(value = "ajax_delete_permissionManager")
    @ResponseBody
    public String deletePermissionManager(HttpServletRequest request){
        String id = request.getParameter("id");
        String[] ids = id.split(",");
        List<Long> list = new ArrayList<>();
        for (String idPermission:ids){
            list.add(Long.parseLong(idPermission));
        }
        sysPermissionGroupService.delete(list.toArray(new Long[list.size()]));
        return "success";
    }


}
