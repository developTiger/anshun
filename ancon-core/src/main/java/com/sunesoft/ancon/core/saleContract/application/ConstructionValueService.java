package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.saleContract.application.criteria.ConstructionValueCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.ConstructionValueDto;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/11/25.
 */
public interface ConstructionValueService {

    public Long add(ConstructionValueDto dto);

    public boolean delete(Long[] id);

    public void update(ConstructionValueDto dto);

    public ConstructionValueDto getById(Long id);

    public List<ConstructionValueDto> getAll();

    public PagedResult<ConstructionValueDto> fingPages(ConstructionValueCriteria valueCriteria);

    public List<ConstructionValueDto> getBySaleContractId(Long id);


}
