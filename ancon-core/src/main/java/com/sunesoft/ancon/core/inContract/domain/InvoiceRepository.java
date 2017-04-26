package com.sunesoft.ancon.core.inContract.domain;

import com.sunesoft.ancon.core.inContract.application.criteria.InvoiceCriteria;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;


/**
 * Created by xiazl on 2016/11/23.
 */
public interface InvoiceRepository {
    /**
     * add or update
     * @param Invoice
     * @return
     */
    Long save(Invoice Invoice);

    /**
     * get by id
     * @param id
     * @return
     */
    Invoice get(Long id);

//    /**
//     * get by num
//     * @param num
//     * @return
//     */
//    Invoice getByNum(String num);

    /**
     * delete by id
     * @param id
     * @return
     */
    void delete(Long id);

    /**
     * 查询某个进项合同所有发票
     * @param inId
     * @return
     */
    public List<Invoice> getList(Long inId);


    /**
     * 分页查询
     * @param criteria
     * @return
     */
    PagedResult<Invoice> paged(InvoiceCriteria criteria);
}
