package com.sunesoft.ancon.webapp.controller;


import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.core.uAuth.application.SysResourceService;
import com.sunesoft.ancon.core.uAuth.application.criteria.ResourceCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.utils.Helper;
import com.sunesoft.ancon.webapp.utils.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/15.
 */
public class Layout {
    public static String layout = "layout/~layout";
    public static String formLayout = "layout/~formLayout";
    public static String applyLayout="layout/~formApply";
    public static String formViewLayout="layout/~formView";

    @Autowired
    UserSession us;

    @Autowired
    SysResourceService menuService;

    public ModelAndView view(String layout,String viewName,Model model){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        model.addAttribute("view", viewName + ".html");
        model.addAttribute("ui",UI.class);
        model.addAttribute("helper", Helper.class);
        UserSessionDto userInfo = us.getCurrentUser(request);
        List<ResourceDto> resourceDtos  = new ArrayList<>();
        String currentUrl = request.getRequestURI().substring(1);
        String url=us.getUrl(request);
        model.addAttribute("checked",url);
        model.addAttribute("currentUrl",currentUrl);
        if(layout.equals("layout/~layout")) {

            if ("1".equals(userInfo.getLoginName()) || "admin".equals(userInfo.getLoginName())) {
                ResourceCriteria criteria = new ResourceCriteria();
                criteria.setPageSize(100);
                resourceDtos = menuService.getResourceList(criteria);

            } else {
                resourceDtos = us.getUserResource(userInfo.getRoleId());
            }


            model.addAttribute("menu",resourceDtos);



           /* if(!StringUtils.isNullOrWhiteSpace(currentUrl)&&resourceDtos!=null) {
                Long  currentID=0L;
                for (ResourceDto dto : resourceDtos) {
                    Boolean hasFoundParent = false;
                    if (dto.getChild() != null && dto.getChild().size() > 0) {
                        for (ResourceDto cc : dto.getChild()) {
                            if (cc.getUrl()!=null&&cc.getUrl().equals(currentUrl)) {
                                currentID = dto.getId();
                                hasFoundParent = true;
                                break;
                            }

                        }
                    }
                    if(hasFoundParent) {
                        model.addAttribute("parentMenu", currentID);
                        break;
                    }
                }
            }*/
        }

       model.addAttribute("userInfo",userInfo);
        ModelAndView mv = new ModelAndView(layout);
        return mv;
    }

    public ModelAndView view(String viewName,Model model){
        model.addAttribute("helper", Helper.class);
        model.addAttribute("ui",UI.class);
        ModelAndView mv = new ModelAndView(viewName);
        return mv;
    }


}
