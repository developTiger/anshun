package com.sunesoft.ancon.webapp.function;


import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.webapp.utils.Des;
import com.sunesoft.ancon.core.uAuth.application.UserAuthService;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/13.
 */
@Component
public class UserSession {

    @Autowired
    UserAuthService userAuthService;
    @Autowired
    ResouceFactory resouceFactory;
    private int sessionTime = 30 * 60;
    private String sessionKey = "empDto_";
    private static String cookiePath = "";

    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    public UserSessionDto getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (getUserIdFromCookie(request) == null) {
            return null;
        }
        UserSessionDto auth = session.getAttribute(sessionKey + getUserIdFromCookie(request).toString()) == null ? null : (UserSessionDto) session.getAttribute(sessionKey + getUserIdFromCookie(request).toString());
        return auth;
    }

    /**
     * 设置登录信息
     *
     * @param request
     * @param response
     * @param userName
     * @param password
     * @return
     */
    public UniqueResult<UserSessionDto> login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        UniqueResult<UserSessionDto> uniqueResult = userAuthService.getUserInfoByLogin(userName, password);
        if (uniqueResult.getIsSuccess()) {
            setSystemUserCookie(uniqueResult.getT().getId().toString(), response);
            setUserSession(request, uniqueResult.getT());
        }
        return uniqueResult;
    }

    public UserSessionDto updateUserSession(HttpServletRequest request, HttpServletResponse response, String sysUserId) {
        UserSessionDto auth = null;
        if (auth == null) {
            UniqueResult<UserSessionDto> uniqueResult = userAuthService.GetUserSessionDtoById(Long.parseLong(sysUserId));
            if (uniqueResult.getIsSuccess()) {
                auth = uniqueResult.getT();
            } else {
                return null;
            }
        }
        setSystemUserCookie(auth.getId().toString(), response);
        setUserSession(request, auth);
        return auth;
    }

    //
    public void setSystemUserCookie(String userId, HttpServletResponse response) {
        Cookie cookie = null;
        try {
            cookie = new Cookie("sra_cun", Des.encode(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //cookie.setDomain(cookiePath);
        cookie.setPath("/");
        cookie.setMaxAge(sessionTime);
        response.addCookie(cookie);
    }

    //
//    /**
//     * 把用户ID保存到cookie中，周期为30分钟
//     *
//     * @param response
//     * @param userId
//     */
    public void setUserCookie(HttpServletRequest request, HttpServletResponse response, String userId) {

//        UserServiceDAO userService = new UserServiceDAO();
//        SimpleUserInfoDTO simpleUserInfoDTO = userService.getSimpleUser(userId);
//        try {
//            BtUserSessionDao.Login(simpleUserInfoDTO, response);
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    //
//
//    /**
//     * 设置用户缓存信息
//     * @param request
//     * @param userInfo
//     */
    public void setUserSession(HttpServletRequest request, UserSessionDto empSessionDto) {
        HttpSession session = request.getSession();
        String key = sessionKey + empSessionDto.getId();
        session.setAttribute(key, empSessionDto);
        session.setMaxInactiveInterval(sessionTime);
    }

    public String getSystemUserIdCookie(HttpServletRequest request) {
        String userId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if ("sra_cun".equals(c.getName())) {
                    try {
                        userId = Des.decode(c.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return userId;

    }

    //
//    /**
//     * 从cookie中获取到用户id
//     *
//     * @param request
//     * @return
//     */
    public String getUserIdFromCookie(HttpServletRequest request) {
        String userId = getSystemUserIdCookie(request);
        if (userId != null && !userId.equals("")) {
            return userId;
        }

        return null;
    }

    public String getUrl(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("url")==null){
            return "sra_index";
        }
        return  session.getAttribute("url").toString();
    }
    public void setUrl(final HttpServletRequest request){

        String currentUrl=request.getRequestURI().replace("/", "");
        if(getCurrentUser(request)!=null) {
            List<ResourceDto> list = this.getUserResource(getCurrentUser(request).getRoleId());
            if(list!=null) {
                if (list.stream().anyMatch(x -> x.getUrl().equals(currentUrl))) {
                    HttpSession session = request.getSession();
                    session.setAttribute("url", currentUrl);
                }
            }
        }


    }

    //
//    /**
//     * 删除用户cookie
//     *
//     * @param request
//     * @param response
//     * @param
//     */
    public Boolean removeUserCookie(HttpServletRequest request, HttpServletResponse response) {
        if(null!=getUserIdFromCookie(request)) {
            String key = sessionKey + getUserIdFromCookie(request).toString();
            Cookie cookie = new Cookie("sra_cun", "");
            //    cookie.setDomain(cookiePath);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            request.getSession().removeAttribute(key); //删除某个session
        }
        return true;
    }


    public List<ResourceDto> getUserResource(Long roleId) {
        if (roleId != null)
            return resouceFactory.getResourceMap().get(roleId);
        return new ArrayList<>();
    }


    public Map<String,ResourceDto> getPageReadonlyOrEditor(String page,HttpServletRequest request){
        List<ResourceDto> resourceDtoList= this.getUserResource(this.getCurrentUser(request).getRoleId());

        List<ResourceDto> temp=new ArrayList<>();
        for(ResourceDto resourceDto:resourceDtoList){
            if(resourceDto.getUrl().equals(page)){
                temp= resourceDto.getChild();
            }
        }

        Map<String,ResourceDto> map=new HashMap<>();
        if(temp!=null && temp.size()>0){
            for(ResourceDto resourceDto:temp){
               map.put(resourceDto.getUrl(),resourceDto);
            }
        }
        return map;
    }
}
