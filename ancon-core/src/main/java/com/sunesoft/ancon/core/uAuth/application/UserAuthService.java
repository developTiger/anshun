package com.sunesoft.ancon.core.uAuth.application;

import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.core.uAuth.domain.SysUserAndRole;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.UniqueResult;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/7/14.
 */
public interface UserAuthService {

    Map<Long, List<ResourceDto>> getAllAuthInfoByRole();


    UniqueResult<UserSessionDto>  getUserInfoByLogin(String userName,String password);

    SysUserAndRole getUserRole(Long userId);


    CommonResult setUserRole(Long userIdpId,Long roleId);

    UniqueResult<UserSessionDto> GetUserSessionDtoById(Long userId);


}
