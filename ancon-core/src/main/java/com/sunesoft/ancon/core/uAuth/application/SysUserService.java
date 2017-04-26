package com.sunesoft.ancon.core.uAuth.application;

import com.sunesoft.ancon.core.uAuth.application.criteria.UserCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserDto;
import com.sunesoft.ancon.core.uAuth.domain.SysUser;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
public interface SysUserService {

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return Boolean
     */
    public Long addUser(UserDto user);


    public SysUser login(String userName,String password);


    public SysUser getBykey(Long id);

    /**
     * @param id       修改用户的ID
     * @param Password 新密码
     * @return Boolean
     */
    public CommonResult changePassword(Long id, String Password);

    public CommonResult checkPassword(Long id,String Password);



    /**
     * 设置用户状态
     *状态修改就是删除，若是不考虑找回功能，就是将状态设置为false
     * @param ids
     * @param status
     * @return
     */
    public Boolean setUserStatus(List<Long> ids, int status);


    /**
     * 查询实例
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<UserDto> FindUser(UserCriteria criteria);


    /**
     * @param dto
     * @return
     */
    public Long addOrUpdate(UserDto dto);


    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    public boolean delete(Long[] ids);


    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    public boolean updateUser(UserDto dto);

    /**
     * 获取所用用户
     *
     * @return
     */
    public List<UserDto> getAllUser();

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    public UserDto getById(Long id);

    public Long save(UserDto dto);

    /**
     * 获取所用用户
     * @ param userName
     * @return
     */
    public List<UserDto> getAllUser(String userName);

    /**
     * 设置用户状态
     * @param id    用户标识
     * @param status 用户状态
     * @return
     */
    public CommonResult setStatus(Long id, Integer status);

    /**
     * 删除用户
     * @param userId    用户标识
     * @return
     */
    CommonResult removeUser(Long userId);
}
