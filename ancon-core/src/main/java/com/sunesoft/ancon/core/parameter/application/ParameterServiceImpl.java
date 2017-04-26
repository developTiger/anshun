package com.sunesoft.ancon.core.parameter.application;

import com.sunesoft.ancon.core.AnconDbSupport;
import com.sunesoft.ancon.core.parameter.application.criteria.ParameterCriteria;
import com.sunesoft.ancon.core.parameter.application.dtos.ParameterDto;
import com.sunesoft.ancon.core.parameter.entity.Parameter;
import com.sunesoft.ancon.core.parameter.entity.ParameterType;
import com.sunesoft.ancon.fr.results.PagedResult;
import com.sunesoft.ancon.fr.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2016/6/2.
 */
@Service("parameterservice")
public class ParameterServiceImpl implements ParameterService {

    @Autowired
    AnconDbSupport dbSupport;

    /**
     * 添加数据
     * @param dto
     * @return
     */
    @Override
    public Long addParameter(ParameterDto dto) {
        return (Long) dbSupport.getParameter().add(convertFromDto(dto));
    }

    /**
     * 群删
     * @param parameterId
     * @return
     */
    @Override
    public boolean deleteParameter(Long[] parameterId) {
        Criteria criterion = dbSupport.getSession().createCriteria(Parameter.class);
        if (parameterId != null && parameterId.length > 0) {
            criterion.add(Restrictions.in("id", parameterId));
        }
        List<Parameter> list = criterion.list();
        for (Parameter parameter : list) {
            dbSupport.getParameter().delete(parameter);
        }

        return true;
    }
    /**
     * 更新数据
     * @param dto
     * @return
     */    @Override
    public void updateParameter(ParameterDto dto) {
        Parameter parameter= dbSupport.getParameter().get(dto.getId());
        if(parameter==null) {
            dbSupport.getParameter().update(convertFromDto(dto));
            return;
        }
        else
        {
           dbSupport.getParameter().update(convertFromDto(dto));
            return ;
        }
    }
    /**
     * 获得所有数据
     * isActive为true
     * @return
     */
    @Override
    public List<ParameterDto> getAllparameter() {
        Criteria criterion = dbSupport.getSession().createCriteria(Parameter.class);
        criterion.add(Restrictions.eq("isActive",true));
        List<Parameter> list1 = criterion.list();
        List<ParameterDto> list = new ArrayList<ParameterDto>();
        for (Parameter p : list1) {
            list.add(convertToDto(p));
        }
        return list;
    }

    /**
     * 获取数据
     * @param id
     * @return
     */
    @Override
    public ParameterDto getById(Long id) {
        return convertToDto(dbSupport.getParameter().get(id));
    }

    @Override
    public PagedResult<ParameterDto> FindParam(ParameterCriteria criteria) {
        Criteria criterion = dbSupport.getSession().createCriteria(Parameter.class);
        criterion.add(Restrictions.eq("isActive",true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getParamName())) {
            criterion.add(Restrictions.like("param_name", "%" + criteria.getParamName() + "%"));
        }
        //获取总记录数
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());

        List<ParameterDto> parameterListDto = new ArrayList<ParameterDto>();
        List<Parameter> parameterList = criterion.list();
        for (Parameter param : parameterList ) {
            if(param.getIsActive()!=null && param.getIsActive()!=false){
                parameterListDto.add(convertToDto(param));
            }
        }
        return new PagedResult<ParameterDto>(parameterListDto, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<ParameterDto> getAllParameterType(String parameterName) {
        Criteria criterion = dbSupport.getSession().createCriteria(Parameter.class);
        criterion.add(Restrictions.eq("isActive",true));
        if(!StringUtils.isNullOrWhiteSpace(parameterName)){
            criterion.add(Restrictions.like("parameterName", "%" +parameterName + "%"));
        }
        List<Parameter> list1 = criterion.list();
        List<ParameterDto> list = new ArrayList<ParameterDto>();
        for (Parameter paramter : list1) {
            list.add(convertToDto(paramter));
        }
        return list;
    }

    //调用方法1 dto转实体
    private Parameter convertFromDto(ParameterDto dto) {
        Parameter parameter = new Parameter();
        if (dto.getId() != null && dto.getId() > 0) {
            parameter = dbSupport.getParameter().get(dto.getId());
        }
        if(dto.getParamTypeId() != null && dto.getParamTypeId() > 0) {
            ParameterType par = dbSupport.getParameterType().get(dto.getParamTypeId());   //转换
            if(par!=null)
                parameter.setParameterType(par);
        }
        parameter.setParamName(dto.getParamName());
        parameter.setParamDesc(dto.getParamDesc());
        parameter.setParamValue(dto.getParamValue());
        parameter.setRemark(dto.getRemark());
        parameter.setAttrbute1(dto.getAttrbute1());
        parameter.setAttrbute2(dto.getAttrbute2());
        parameter.setAttrbute3(dto.getAttrbute3());
        parameter.setAttrbute4(dto.getAttrbute4());
        parameter.setAttrbute5(dto.getAttrbute5());
        return parameter;
    }

    //调用方法2 实体转dto
    private ParameterDto convertToDto(Parameter param) {
        ParameterDto dto = new ParameterDto();
        dto.setId(param.getId());
        dto.setParamName(param.getParamName());
        dto.setParamDesc(param.getParamDesc());
        dto.setRemark(param.getRemark());
        dto.setParamValue(param.getParamValue());
        dto.setAttrbute1(param.getAttrbute1());
        dto.setAttrbute2(param.getAttrbute2());
        dto.setAttrbute3(param.getAttrbute3());
        dto.setAttrbute4(param.getAttrbute4());
        dto.setAttrbute5(param.getAttrbute5());

        ParameterType parameterType=param.getParameterType();
       // dto.getParamTypeId(parameterType.);
        if(parameterType!=null) {
            dto.setParamTypeName(parameterType.getParamTypeName());
            dto.setParamTypeId(parameterType.getId());
        }
        return dto;
    }
}
