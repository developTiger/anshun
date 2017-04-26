package com.sunesoft.ancon.core.inContract.domain;


import com.sunesoft.ancon.core.inContract.application.criteria.PayCriteria;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by xiazl on 2016/11/22.
 */
public interface PayRepository {
    /**
     * add or update
     * @param pay
     * @return
     */
    public Long save(Pay pay);

    /**
     * get by id
     * @param id
     * @return
     */
    public Pay get(Long id);

    /**
     * get by num
     * @param num
     * @return
     */
    public Pay getByNum(String num);

    /**
     * delete by id
     * @param id
     * @return
     */
    public void delete(Long id);


    /**
     * 分页查询
     * @param criteria
     * @return
     */
    public PagedResult<Pay> paged(PayCriteria criteria);

    /**
     * 查出某个进项合同的所有有效的付款记录
     * @param inId
     * @return
     */
    public List<Pay> getList(Long inId);



}
