package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.inContract.application.criteria.InContractCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by xiazl on 2016/11/22.
 */
public interface InContractService {
    /**
     * 增加新进项合同
     *
     * @param dto
     * @return
     */
    public CommonResult create(InContractDto dto);

    /**
     * 修改进项合同
     *
     * @param dto
     * @return
     */
    public CommonResult edit(InContractDto dto);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public InContractDto getById(Long id);

    /**
     * 根据num 的查询
     *
     * @param num
     * @return
     */
    public InContractDto getByNum(String num);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public CommonResult delete(Long id);

    /**
     * 根据ids删除
     *
     * @param ids
     * @return
     */
    public CommonResult delete(List<Long> ids);

    /**
     * 根据名称查询集合
     *
     * @param name
     * @return
     */
    public List<InContractDto> getByName(String name);

    /**
     * 进项合同分页查询
     *
     * @param inContractCriteria
     * @return
     */
    public PagedResult<InContractDto> pg(InContractCriteria inContractCriteria);

    /**
     * 获取进项合同额统计
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> getCount(String beginTime, String endTime);

    /**
     * 根据乙方公司查找进项合同列表
     *
     * @return
     */
    public List<InContractDto> getAll();

}
