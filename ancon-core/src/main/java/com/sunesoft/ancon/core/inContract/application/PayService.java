package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.inContract.application.criteria.PayCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.PayDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;
import java.util.Map;

/**
 * Created by xiazl on 2016/11/23.
 */
public interface PayService {

    /**
     * 增加新开票
     *
     * @param dto
     * @return
     */
    public CommonResult create(PayDto dto);

    /**
     * 修改开票
     *
     * @param dto
     * @return
     */
    public CommonResult edit(PayDto dto);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public PayDto getById(Long id);


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
     * 开票分页查询
     *
     * @param criteria
     * @return
     */
    public PagedResult<PayDto> pg(PayCriteria criteria);

    /**
     * 付款统计  从数据库查找
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String,Object>> getCount(String beginTime,String endTime);

}
