package com.sunesoft.ancon.core.uAuth.application;

import com.sunesoft.ancon.core.uAuth.application.criteria.DeptmentCriteria;
import com.sunesoft.ancon.core.uAuth.application.criteria.ResourceCriteria;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.ResourceDto;
import com.sunesoft.ancon.core.uAuth.domain.SysResource;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by zhouz on 2016/5/25.
 */
public interface DeptmentService {
    /**
     * 增加机构
     * @param dto
     * @return
     */
    public CommonResult addDept(DeptmentDto dto);

    /**
     * 删除机构
     * @param ids
     * @return
     */
    public CommonResult deleteDept(List<Long> ids);

    /**
     * 设置机构状态
     * @param ids
     * @param status
     * @return
     */
    public CommonResult setStatus(List<Long> ids,Integer status);
    /**
     * 获取 机构
     * @param ids
     * @return
     */
    public List<DeptmentDto> getByDeptsIds(List<Long> ids);

    /**
     * 获取 机构
     * @return
     */
    public List<DeptmentDto> getByDeptsIds();


    /**
     * 获取 机构
     * @param id
     * @return
     */
    public DeptmentDto getByDeptId(Long id);

    /**
     * 修改机构
     * @param dto
     * @return
     */
    public CommonResult updateDept(DeptmentDto dto);

    /**
     * 获取所有机构根据用户名，这里考虑到用户名不重复
     *
     * @return
     * @ param loginName
     */
    public List<DeptmentDto> getDeptsByName(String name);

    /**
     * 获取所有可查询的机构
     * @return
     */
    public List<DeptmentDto>getAllDept();

    /**
     * 获取所有分公司信息
     */
    public List<DeptmentDto> getAllDeptCommpany();



    /**
     * 查询机构
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<DeptmentDto> findDeptsPaged(DeptmentCriteria criteria);



}
