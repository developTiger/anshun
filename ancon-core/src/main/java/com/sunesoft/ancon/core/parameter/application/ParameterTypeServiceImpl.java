package com.sunesoft.ancon.core.parameter.application;

import com.sunesoft.ancon.core.parameter.application.criteria.ParameterTypeCriteria;
import com.sunesoft.ancon.core.parameter.application.dtos.ParameterTypeDto;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.parameter.application.dtos.ParameterDto;
import com.sunesoft.ancon.core.parameter.entity.Parameter;
import com.sunesoft.ancon.core.parameter.entity.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by zy on 2016/6/2.
 */

@Service("parameterTypeService")
public class ParameterTypeServiceImpl implements ParameterTypeService {

    @Autowired
    AnconDbSupport support;

    /**
     * 添加数据service
     *
     * @param dto
     * @return
     */
    @Override
    public Long addParameterType(ParameterTypeDto dto) {

        return (long)support.getParameterType().add(convertFromDto(dto));
    }


    /**
     * 群删
     *
     * @param parameterTypeId
     * @return
     */
    @Override
    public boolean deleteParameterType(Long[] parameterTypeId) {
        Criteria criterion =support.getSession().createCriteria(ParameterType.class);
        if (parameterTypeId != null && parameterTypeId.length > 0) {
            criterion.add(Restrictions.in("id", parameterTypeId));
        }
        List<ParameterType> list = criterion.list();
        for (ParameterType parameterType : list) {
            parameterType.setIsActive(false);
            support.getParameterType().update(parameterType);
        }
        return true;
    }

    /**
     * 更新数据
     *
     *
     *
     * @param dto
     * @return
     */
    @Override
    public void updateParameterType(ParameterTypeDto dto) {
        ParameterType parameterType = support.getParameterType().get(dto.getId());
        if (parameterType == null) {

             support.getParameterType().update(convertFromDto(dto));
            return;
        } else {
            if (dto.getParamTypeName() != null) {
                parameterType.setParamTypeName(dto.getParamTypeName());
            }
            if (dto.getParamDesc() != null) {
                parameterType.setParamDesc(dto.getParamDesc());
            }
            support.getParameterType().update(parameterType);

            return ;
        }
    }

    /**
     * 获得所有数据
     * isActive为true
     *
     * @return
     */
    @Override
    public List<ParameterTypeDto> getAllparametertype() {
        Criteria criterion = support.getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<ParameterType> list1 = criterion.list();
        List<ParameterTypeDto> list = new ArrayList<ParameterTypeDto>();
        for (ParameterType pt : list1) {
            list.add(convertToDto(pt));
        }
        return list;
    }

    /**
     * 根据id'
     *
     * @param id
     * @return
     */
    @Override
    public ParameterTypeDto getById(Long id) {
        return convertToDto(support.getParameterType().get(id));
    }

    @Override
    public List<ParameterTypeDto> getAllParameterType(String parameterTypeName) {
        Criteria criterion = support.getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(parameterTypeName)) {
            criterion.add(Restrictions.like("param_type_name", "%" + parameterTypeName + "%"));
        }
        List<ParameterType> list1 = criterion.list();
        List<ParameterTypeDto> list = new ArrayList<ParameterTypeDto>();
        for (ParameterType paramterType : list1) {
            list.add(convertToDto(paramterType));
        }
        return list;
    }

    /**
     * 分页查询
     *
     * @param criteria 查询条件
     * @return
     */
    @Override
    public PagedResult<ParameterTypeDto> FindParam(ParameterTypeCriteria criteria) {
        Criteria criterion = support.getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getParamTypeName())) {
            criterion.add(Restrictions.like("param_name", "%" + criteria.getParamTypeName() + "%"));
        }
        //获取总记录数
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());

        List<ParameterTypeDto> parameterTypeListDto = new ArrayList<ParameterTypeDto>();
        List<ParameterType> parameterTypeList = criterion.list();
        for (ParameterType paramtype : parameterTypeList) {
            if (paramtype.getIsActive() != null && paramtype.getIsActive() != false) {
                parameterTypeListDto.add(convertToDto(paramtype));
            }
        }
        return new PagedResult<ParameterTypeDto>(parameterTypeListDto, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }


    //调用方法1 dto转实体
    private ParameterType convertFromDto(ParameterTypeDto dto) {
        ParameterType parameterType = new ParameterType();
        if (dto.getId() != null && dto.getId() > 0) {
            parameterType = support.getParameterType().get(dto.getId());
        }
        //将dto里的参数设置到原实体类里
        parameterType.setParamTypeName(dto.getParamTypeName());
        parameterType.setParamDesc(dto.getParamDesc());
        return parameterType;
    }

    //调用方法2 实体转dto
    private ParameterTypeDto convertToDto(ParameterType paramtype) {
        ParameterTypeDto dto = new ParameterTypeDto();
        dto.setId(paramtype.getId());

        dto.setParamTypeName(paramtype.getParamTypeName());
        dto.setParamDesc(paramtype.getParamDesc());
        return dto;
    }
}
