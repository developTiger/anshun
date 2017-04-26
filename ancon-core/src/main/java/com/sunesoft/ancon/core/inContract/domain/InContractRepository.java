package com.sunesoft.ancon.core.inContract.domain;


import com.sunesoft.ancon.core.inContract.application.criteria.InContractCriteria;
import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by xiazl on 2016/11/22.
 */
public interface InContractRepository  {
    /**
     * add or update
     * @param inContract
     * @return
     */
    public Long save(InContract inContract);

    /**
     * get by id
     * @param id
     * @return
     */
    public InContract get(Long id);

    /**
     * get by num
     * @param num
     * @return
     */
    public InContract getByNum(String num);

    /**
     * delete by id
     * @param id
     * @return
     */
    public void delete(Long id);

    /**
     * 根据合同名称查询
     * @param name
     * @return
     */
    public List<InContract> getByName(String name);

    /**
     * 分页查询
     * @param criteria
     * @return
     */
    public PagedResult<InContract> paged(InContractCriteria criteria);


    /**
     * 根据乙方公司查找进项合同列表
     * @return
     */
    public List<InContract> getAll();

}
