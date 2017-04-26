package com.sunesoft.ancon.core.companys.application;


import com.sunesoft.ancon.core.companys.application.criteria.SecondPartyCriteria;
import com.sunesoft.ancon.core.companys.application.dto.ContractorPartyDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public interface SecondPartyService {

    /**
     * 新增合作公司
     * @param dto
     * @return
     */
    public CommonResult addFirstParty(ContractorPartyDto dto);

    /**
     * 删除合作公司
     *
     * @param id
     * @return
     */
    public boolean deleteFirstParty(Long[] id);

    /**
     * 更新合作公司
     *
     * @param dto
     * @return
     */

    public CommonResult updateFirstParty(ContractorPartyDto dto);

    /**
     * 得到合作公司
     * @param id
     * @return
     */
    public ContractorPartyDto getById(Long id);

    /**
     * 分页查询
     * @param contractCriteria
     * @return
     */
    public PagedResult<ContractorPartyDto> findPages(SecondPartyCriteria contractCriteria);

    /**
     * 删除合作公司
     *
     * @param longs
     * @return
     */
    Boolean deletePartyB(Long[] longs);

    /**
     * 得到所有合作公司
     * @return
     */
    List<ContractorPartyDto> getAll();

    /**
     *  1 : 接包方  2 ：放包方  3 : 既是接包方又是放包方
     */
    List<ContractorPartyDto> getByTypeAll(Integer number);

    ContractorPartyDto getByTypeAndName(Integer number,String name);
}
