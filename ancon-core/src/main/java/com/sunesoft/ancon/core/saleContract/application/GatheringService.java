package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.saleContract.application.criteria.GatheringCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.GatheringDto;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;

/**
 * Created by admin on 2016/11/25.
 */
public interface GatheringService {

    public Long add(GatheringDto dto);

    public boolean delete(Long[] ids);

    public void update(GatheringDto dto);

    public GatheringDto getById(Long id);

    public List<GatheringDto> getAll();

    public PagedResult<GatheringDto> fingPages(GatheringCriteria gatheringCriteria);

    public List<GatheringDto> getBysaleContractId(Long id);

}
