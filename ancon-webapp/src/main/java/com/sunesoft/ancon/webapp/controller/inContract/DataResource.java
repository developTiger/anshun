package com.sunesoft.ancon.webapp.controller.inContract;

import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.domain.ContractSpeed;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.webapp.function.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiazl on 2016/12/19.
 */
@Component
public class DataResource {


    @Autowired
    UserSession userSession;

    /**
     * 数据权限
     * @param page
     * @param dto
     * @param request
     * @return
     */
    public Map<String, ResourceDto> resources(String page, InContractDto dto, HttpServletRequest request) {
        UserSessionDto user = userSession.getCurrentUser(request);
        Map<String, ResourceDto> map = userSession.getPageReadonlyOrEditor(page, request);//初始化的数据
         Map<String, ResourceDto> op = new HashMap<>();
        if(map==null||map.size()==0) return map;
        if (dto == null) {
            op = map;
        } else {
            if(dto.getStatus().equals(ContractSpeed.Finish)) return op;
            if (user.getType() == 2 && user.getCompanyId().equals(dto.getCompanyId())) {//独立分公司的人只能查看自己的
                op = map;
            }
            if(user.getType() == 2 && !user.getCompanyId().equals(dto.getCompanyId())){
                for (Map.Entry<String, ResourceDto> entry : map.entrySet()) {
                    if (entry.getValue().getUrl().equals("add_contract")) {
                        op.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (user.getType() != 2 && dto.getCompanyType() == 2) {// 独立公司的合同别人不能修改(合同的修改，付款开票的增加，修改，删除)

                for (Map.Entry<String, ResourceDto> entry : map.entrySet()) {
                    if (entry.getValue().getUrl().equals("add_contract")) {
                        op.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if(user.getType() != 2 && dto.getCompanyType() != 2){
                op=map;
            }


        }

//        if (dto != null && dto.getCompanyType() == 2) {//查看的是独立分公司的数据
//            if (dto.getCompanyId().equals(user.getCompanyId()))//判断当前用户是否为改公司人员
//            {
//                op = userSession.getPageReadonlyOrEditor(page, request);
//            }
//        } else {
//            if (!user.getType().equals(2))//当前用户不是独立分公司的
//            {
//                op = userSession.getPageReadonlyOrEditor(page, request);
//            }
//        }
        return op;

    }
}
