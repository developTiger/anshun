package com.sunesoft.ancon.core.uAuth.application;

import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.domain.*;
import com.sunesoft.ancon.fr.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.ResultFactory;
import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.fr.utils.BeanUtils;
import com.sunesoft.ancon.core.uAuth.application.dtos.AuthResDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhouz on 2016/7/14.
 */
@Service("empAuthService")
public class UserAuthServiceImpl extends GenericHibernateFinder implements UserAuthService {
    @Autowired
    SysEmpAndRoleRepository sysEmpAndRoleRepository;

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    SysRoleRepository roleRepository;

    @Override
    public Map<Long, List<ResourceDto>> getAllAuthInfoByRole() {

        System.out.println("- - - - - - - - - 初始化权限 - - - - - - - -- - - - - - - ");
        Map<Long, List<AuthResDto>> mapResult = new HashMap<>();
        Map<Long, List<ResourceDto>> result = new HashMap<>();
        //数据重复需处理
        String sql = "select distinct r.id roleId,s.id,s.icon_name iconName,s.id_code idCode,s.isRoot,s.sort,s.url,s.parent_res_id parentId,s.res_name resName ,s.resource_type resType," +
                " s.resource_operate,s.operateFlag,s.target from" +
                " ancon_sys_role r join ancon_role_pmgroup rg on r.id=rg.user_role_id join ancon_sys_permit_group g on rg.sys_permitgroup_id=g.id join ancon_pmgroup_res " +
                "gs on g.id = gs.group_id join  ancon_sys_resource s on gs.res_id=s.id where r.is_active=1 and g.is_active=1 and s.is_active=1 order by s.sort  ";
        List<AuthResDto> dtos = queryForObjects(AuthResDto.class, sql, null);
        for (AuthResDto dto : dtos) {
            if (mapResult.get(dto.getRoleId()) != null) {
                mapResult.get(dto.getRoleId()).add(dto);
            } else {
                List<AuthResDto> dtoList = new ArrayList<>();
                dtoList.add(dto);
                mapResult.put(dto.getRoleId(), dtoList);
            }
        }
        for (Long key : mapResult.keySet()) {
            List<AuthResDto> reslist = mapResult.get(key);
            List<ResourceDto> resourceDtos = new ArrayList<>();
            for (AuthResDto dto : reslist) {
                if (dto.getParentId() == null) {
                    ResourceDto r = new ResourceDto();
                    BeanUtils.copyProperties(dto, r);
                    r.setName(dto.getResName());
                    r.setResource_operate(dto.getResource_operate());
                    resourceDtos.add(r);
                }
            }
            for (AuthResDto dto : reslist) {
                if (dto.getParentId() != null) {
                    for (ResourceDto ddd : resourceDtos) {
                        if (dto.getParentId().equals(ddd.getId())) {
                            if (ddd.getChild() == null) {
                                ddd.setChild(new ArrayList<ResourceDto>());
                            }
                            ResourceDto r = new ResourceDto();
                            BeanUtils.copyProperties(dto, r);
                            r.setName(dto.getResName());
                            r.setResource_operate(dto.getResource_operate());
                            ddd.getChild().add(r);
                        }
                    }
                }
            }

            result.put(key, resourceDtos);
        }
        System.out.println(" - - - - - - - - - - -初始化权限结束- - - - - - - -- - - ");
        return result;
    }

    @Override
    public UniqueResult<UserSessionDto> getUserInfoByLogin(String userName, String password) {

        UserSessionDto sessionDto = new UserSessionDto();
        Criteria criterion = getSession().createCriteria(SysUserAndRole.class);
        criterion.createAlias("sysUser", "sysUser");
        criterion.add(Restrictions.eq("sysUser.isActive", true));
        criterion.add(Restrictions.eq("sysUser.loginName", userName));
        List<SysUserAndRole> list = criterion.list();
        if (list.size() > 0) {
            SysUserAndRole result = list.get(0);
            SysUser e = result.getSysUser();
            if (e.getStatus()==1) {
                return new UniqueResult<>("该用户已被禁用！");
            }
            if (e.getPassword().equals(password)) {
                e.setLastLoginTime(new Date());
                int i = e.getLoginCount();
                e.setLoginCount(++i);

                BeanUtils.copyProperties(e, sessionDto);

                if(e.getDept()!=null){
                    sessionDto.setCompanyId(e.getDept().getId());
                    sessionDto.setCompanyName(e.getDept().getDeptName());
                    sessionDto.setType(e.getDept().getType());
                    sessionDto.setDeptNo(e.getDept().getDeptNo());
                    sessionDto.setName(e.getName());
                }
                if (result.getRole() != null)
                    sessionDto.setRoleId(result.getRole().getId());
               // sysUserRepository.save(e);
            } else {
                return new UniqueResult<>("用户名或密码错误！");
            }
        } else {
            return new UniqueResult<>("用户名或密码错误！");
        }
        return new UniqueResult<>(sessionDto);
    }

    @Override
    public SysUserAndRole getUserRole(Long userId) {
        Criteria criterion = getSession().createCriteria(SysUserAndRole.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.createAlias("sysUser", "sysUser");
        criterion.add(Restrictions.eq("sysUser.id", userId));
        List<SysUserAndRole> sysUserAndRoles = criterion.list();
        if (sysUserAndRoles != null && sysUserAndRoles.size() > 0)
            return sysUserAndRoles.get(0);
        return null;
    }

    @Override
    public CommonResult setUserRole(Long userId, Long roleId) {

        Criteria criterion = getSession().createCriteria(SysUserAndRole.class);
        criterion.createAlias("sysUser", "sysUser");
        criterion.add(Restrictions.eq("sysUser.id", userId));
        List<SysUserAndRole> sysUserAndRoles = criterion.list();
        SysUserAndRole sysUserAndRole = null;
        if (sysUserAndRoles != null && sysUserAndRoles.size() > 0)
            sysUserAndRole = sysUserAndRoles.get(0);
        SysRole sysRole = roleRepository.get(roleId);
        SysUser user = new SysUser();
        if (sysUserAndRole == null) {
            sysUserAndRole = new SysUserAndRole();
            user = sysUserRepository.get(userId);
            sysUserAndRole.setSysUser(user);
            sysUserAndRole.setRole(sysRole);
        } else {
            user = sysUserAndRole.getSysUser();
            sysUserAndRole.setRole(sysRole);
        }
        sysEmpAndRoleRepository.save(sysUserAndRole);
        sysUserRepository.save(user);
        return ResultFactory.commonSuccess();
    }

    @Override
    public UniqueResult<UserSessionDto> GetUserSessionDtoById(Long userId) {
        Criteria criterion = getSession().createCriteria(SysUserAndRole.class);
        criterion.createAlias("sysUser", "sysUser");
        criterion.add(Restrictions.eq("sysUser.isActive", true));
        /*criterion.add(Restrictions.eq("sysUser.status", 1));*/
        criterion.add(Restrictions.eq("sysUser.id", userId));
        List<SysUserAndRole> sysUserAndRoles = criterion.list();
        SysUserAndRole sysUserAndRole =null;
        if (sysUserAndRoles != null && sysUserAndRoles.size() > 0)
            sysUserAndRole = sysUserAndRoles.get(0);
        if (sysUserAndRole == null) new UniqueResult<>("用户不存在或已经被禁用");

            SysUser e = sysUserAndRole.getSysUser();
            UserSessionDto sessionDto = new UserSessionDto();
            BeanUtils.copyProperties(e, sessionDto);
//        if(e.getDept()!=null){
//            sessionDto.setDeptId(e.getDept().getId());
//            sessionDto.setDeptName(e.getDept().getDeptName());
//        }
            if (sysUserAndRole.getRole() != null)
                sessionDto.setRoleId(sysUserAndRole.getRole().getId());
            if (sysUserAndRole.getSysUser() != null) {
                if(sysUserAndRole.getSysUser().getDept()!=null) {
                    sessionDto.setCompanyId(sysUserAndRole.getSysUser().getDept().getId());
                    sessionDto.setCompanyName(sysUserAndRole.getSysUser().getDept().getDeptName());
                    sessionDto.setType(sysUserAndRole.getSysUser().getDept().getType());
                    sessionDto.setDeptNo(e.getDept().getDeptNo());
                }
                sessionDto.setName(e.getName());
            }

        return new UniqueResult<UserSessionDto>(sessionDto);
    }
}
