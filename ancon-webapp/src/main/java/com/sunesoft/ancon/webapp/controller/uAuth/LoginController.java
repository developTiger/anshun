package com.sunesoft.ancon.webapp.controller.uAuth;

import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.webapp.function.ResouceFactory;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.utils.Helper;
import com.sunesoft.ancon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhouz on 2016/5/26.
 */
@Controller
public class LoginController {
    @Autowired
    UserSession us;
    private String loginTitle = "登录 - 业务系统";

    @Autowired
    ResouceFactory resouceFactory;

    @RequestMapping(value = "")
    private String login_index(Model model, HttpServletRequest request){
        UserSessionDto userInfo = us.getCurrentUser(request);
        if(userInfo != null){
                return "redirect:sra_index";
        }
        return "redirect:login";
    }

    @RequestMapping(value = "login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserSessionDto userInfo = us.getCurrentUser(request);
        model.addAttribute("title", loginTitle);
        model.addAttribute("helper", Helper.class);
        String redirectUrl = StringUtils.isNullOrWhiteSpace(request.getParameter("rurl")) ? "" : request.getParameter("rurl");
        String userId = us.getUserIdFromCookie(request);
        if (userId != null) {
            if(StringUtils.isNullOrWhiteSpace(redirectUrl)) {
                try {
                    response.sendRedirect("sra_index");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                String url = null;
                if (redirectUrl != null && !redirectUrl.equals("")) {
                    try {
                        url = URI.deURI(redirectUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        response.sendRedirect(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            if (!StringUtils.isNullOrWhiteSpace(redirectUrl))
                model.addAttribute("rurl", redirectUrl);
        }
        ModelAndView mv = new ModelAndView("login/login");
        return mv;
    }


    @RequestMapping(value = "loginForm", method = RequestMethod.POST)
    private void login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        UniqueResult uniqueResult = us.login(request, response, userName, password);
        String result = "";
        if (uniqueResult.getIsSuccess()) {
//        if (true) {
            result = "success";
        } else {
            result = uniqueResult.getMsg();
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 注销
     * @param request
     * @param response
     */
    @RequestMapping(value = "logOut")
    private String logOut(HttpServletRequest request,HttpServletResponse response){
        us.removeUserCookie(request,response);
      //  request.getSession().setMaxInactiveInterval(0);
        resouceFactory.clear();
        return "redirect:";
    }
}



