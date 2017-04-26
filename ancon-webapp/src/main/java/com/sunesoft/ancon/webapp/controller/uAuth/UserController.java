package com.sunesoft.ancon.webapp.controller.uAuth;

import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.criteria.UserCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.core.uAuth.domain.SysRole;
import com.sunesoft.ancon.fr.loggers.Logger;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.webapp.controller.Layout;
import com.sunesoft.ancon.webapp.function.ResouceFactory;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import com.sunesoft.ancon.core.uAuth.application.SysRoleService;
import com.sunesoft.ancon.core.uAuth.application.SysUserService;
import com.sunesoft.ancon.core.uAuth.application.UserAuthService;
import com.sunesoft.ancon.core.uAuth.application.dtos.RoleDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserDto;
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
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */

@Controller
public class UserController extends Layout {

    @Autowired
    Logger logger;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    UserSession us;

    @Autowired
    ResouceFactory resouceFactory;



    @RequestMapping(value = "sra_u_userInfo")
    public ModelAndView userInfo(Model model, HttpServletRequest request, HttpServletResponse response) {

        UserSessionDto userSessionDto=us.getCurrentUser(request);
        String id = request.getParameter("id");
        if (id == null) {
            id = "";
        }
        model.addAttribute("ucheck","1");
        model.addAttribute("empId", id);

        return view(layout, "/uAuth/userInfo", model);
    }

    @RequestMapping(value = "resetPwd")
    @ResponseBody
    public CommonResult resetPwd(Model model, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        return sysUserService.changePassword(id, "123456");
    }

    @RequestMapping(value = "ajax_userQuery_list")
    public void getEmpInFos(UserCriteria criteria, HttpServletRequest request, HttpServletResponse response) {

        PagedResult result = sysUserService.FindUser(criteria);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "sra_u_organizations")
    public ModelAndView organizations(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (id == null) {
            id = "";
        }
        model.addAttribute("empId", id);
        return view(layout, "/uAuth/organizations", model);
    }


    @RequestMapping(value = "_addUserForm",method=RequestMethod.POST)
    public ModelAndView addUserForm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            model.addAttribute("id", id);
            UserDto emp = sysUserService.getById(Long.parseLong(id));
            model.addAttribute("uname", emp);
        }

        List<DeptmentDto> list=deptmentService.getByDeptsIds();
        model.addAttribute("beans",list);
        return view("/uAuth/_addUserForm", model);
    }

    @RequestMapping(value = "_addSetUserRoleForm",method=RequestMethod.POST)
    public ModelAndView _addSetUserRoleForm(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        model.addAttribute("id", id);
        UserDto emp = sysUserService.getById(Long.parseLong(id));
        model.addAttribute("uname", emp.getLoginName());
        model.addAttribute("roleName", emp.getRoleName());
        List<RoleDto> list = sysRoleService.getAllRole();
        model.addAttribute("beansRole", list);
        return view("/uAuth/_addSetUserRoleForm", model);
    }

    @RequestMapping(value = "ajax_update_user_role", method = RequestMethod.POST)
    public void ajax_update_user_role(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Long roleid = null;
        String role = request.getParameter("roleId");
        if (!StringUtils.isNullOrWhiteSpace(role)) {
            roleid = Long.parseLong(role);
        }
        CommonResult result = sysRoleService.setuserRole(Long.parseLong(id), roleid);
        if(result.getIsSuccess())
            us.updateUserSession(request,response,String.valueOf(us.getCurrentUser(request).getId()));
        //  CommonResult result=new CommonResult(true,"",9L);
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_update_user", method = RequestMethod.POST)
    public void updateUserRole(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        UserDto userDto=new UserDto();
        String loginName=request.getParameter("loginName");
        String mobile=request.getParameter("mobile");
        String deptId=request.getParameter("deptId");
        String name=request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            userDto.setId(Long.parseLong(id));

        }
        userDto.setName(name);
        userDto.setLoginName(loginName);
        userDto.setMobile(mobile);
        userDto.setDeptId(Long.parseLong(deptId));
        userDto.setStatus(1);
        Long ids=sysUserService.addOrUpdate(userDto);

        CommonResult result = new CommonResult(ids>0?true:false,"用户名已存在");
        if(result.getIsSuccess())
            us.updateUserSession(request,response,String.valueOf(us.getCurrentUser(request).getId()));
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "sra_editPassword")
    public ModelAndView sra_editPassword(Model model, HttpServletRequest request, HttpServletResponse response) {

        return view(layout, "/login/editPassword", model);
    }

    @RequestMapping(value="ajax_sure_password",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult ajax_sure_password(HttpServletRequest request, HttpServletResponse response){
        UserSessionDto userSessionDto=us.getCurrentUser(request);
        String password=request.getParameter("old_password");
        CommonResult commonResult=sysUserService.checkPassword(userSessionDto.getId(), password);
        return commonResult;
    }

    @RequestMapping(value="ajax_change_Password")
    @ResponseBody
    public CommonResult change_Password(HttpServletRequest request, HttpServletResponse response){
        UserSessionDto userSessionDto=us.getCurrentUser(request);
        String password=request.getParameter("new_password");
        CommonResult commonResult=sysUserService.changePassword(userSessionDto.getId(), password);
        if(commonResult.getIsSuccess()){
            us.removeUserCookie(request,response);
        }
        return commonResult;
    }

    @RequestMapping(value="ajax_set_status")
    @ResponseBody
    public CommonResult ajax_set_status(HttpServletRequest request, HttpServletResponse response){

        String id=request.getParameter("id");
        String status=request.getParameter("status");
        if(!StringUtils.isNullOrWhiteSpace(id) && !StringUtils.isNullOrWhiteSpace(status)) {
            return sysUserService.setStatus(Long.parseLong(id), Integer.valueOf(status));
        }else {
            return new CommonResult(false,"操作失败");
        }
    }

    @RequestMapping(value="ajax_remove_user")
    @ResponseBody
    public CommonResult ajax_remove_user(HttpServletRequest request){

        String id=request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id) ) {
            return sysUserService.removeUser(Long.parseLong(id));
        }else {
            return new CommonResult(false,"操作失败");
        }
    }


    @RequestMapping(value="ajax_clear_cache")
    @ResponseBody
    public CommonResult ajax_clear(HttpServletRequest request, HttpServletResponse response){
        resouceFactory.clear();
        return new CommonResult(true);
    }



}
