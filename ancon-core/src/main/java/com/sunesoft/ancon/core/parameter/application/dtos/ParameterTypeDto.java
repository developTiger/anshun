package com.sunesoft.ancon.core.parameter.application.dtos;

import java.util.List;

/**
 * Created by zy on 2016/6/2.
 */

public class ParameterTypeDto {

    private Long id; //id值

    private String paramTypeName; //参数类型

    private String paramDesc;//参数描述

    //以下get,set方法
    public Long getId() { return id; }

    public void setId(Long id) {  this.id = id; }

    public String getParamTypeName() {
        return paramTypeName;
    }

    public void setParamTypeName(String paramTypeName) {
        this.paramTypeName = paramTypeName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }


    @Override
    public String toString() {
        return "ParameterTypeDto{" +
                "id=" + id +
                ", paramTypeName='" + paramTypeName + '\'' +
                ", paramDesc='" + paramDesc + '\'' +
                '}';
    }
}
