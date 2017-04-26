package com.sunesoft.ancon.webapp.function;

import com.sunesoft.ancon.core.uAuth.application.UserAuthService;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/7/14.
 */
@Component
public class ResouceFactory {


    @Autowired
    UserAuthService userAuthService;

    private static Map<Long, List<ResourceDto>> factory = new HashMap<>();


    public Map<Long, List<ResourceDto>> getResourceMap() {
        if (factory.size() == 0) {
            factory = userAuthService.getAllAuthInfoByRole();
        }
        return factory;
    }

    public void clear(){
        factory=new HashMap<>();
    }


    public String getFileAuth(Long roleId) {
        List<ResourceDto> resourceDtos = this.getResourceMap().get(roleId);
        String authInfo = null;
        if (resourceDtos != null && resourceDtos.size() > 0) {
            for (ResourceDto dto : resourceDtos) {
                if (dto.getResType().equals(3)) {
                    authInfo = dto.getUrl();
                    if (authInfo.equals("all"))
                        return authInfo;
                }
            }
        }
        return authInfo;

    }

    public List<ResourceDto> getFormAuth(Long roleId) {
        List<ResourceDto> resourceDtos =  this.getResourceMap().get(roleId);
        List<ResourceDto> formInfo = new ArrayList<>();
        String authInfo = null;
        if (resourceDtos != null && resourceDtos.size() > 0) {
            for (ResourceDto dto : resourceDtos) {
                if (dto.getResType().equals(2)) {
                    formInfo.add(dto);
                }
            }
        }
        return formInfo;
    }
}
