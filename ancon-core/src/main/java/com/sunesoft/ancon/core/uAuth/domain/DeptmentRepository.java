package com.sunesoft.ancon.core.uAuth.domain;

import java.util.List;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface DeptmentRepository {

    /**
     * add or save
     * @param dept
     * @return
     */
    Long save(Deptment dept);

    /**
     * delete
     * @param deptId
     */
    void delete(Long deptId);

    /**
     * get
     * @param inventorId
     * @return
     */
    Deptment get(Long inventorId);

    /**
     * get list
     * @param ids
     * @return
     */
    List<Deptment> getByIds(List<Long> ids);
}
