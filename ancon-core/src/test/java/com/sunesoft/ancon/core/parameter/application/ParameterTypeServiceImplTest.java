package com.sunesoft.ancon.core.parameter.application;

import com.sunesoft.ancon.core.parameter.application.dtos.ParameterTypeDto;
import com.sunesoft.ancon.core.uAuth.application.DeptmentService;
import com.sunesoft.ancon.core.uAuth.application.dtos.DeptmentDto;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ParameterTypeServiceImplTest extends TestCase {
    @Autowired
    ParameterTypeService parameterTypeService;

    @Autowired
    DeptmentService deptmentService;
    @Test
    public void testAddParameterType() throws Exception {

        ParameterTypeDto typeDto = new ParameterTypeDto();
        typeDto.setParamDesc("ceshi0");
        typeDto.setParamTypeName("111111");

        parameterTypeService.addParameterType(typeDto);
        parameterTypeService.addParameterType(typeDto);
    }

    @Test
    public void dfds() throws Exception {
        List<DeptmentDto> list=deptmentService.getAllDeptCommpany();
    }

    public void testDeleteParameterType() throws Exception {

    }

    public void testUpdateParameterType() throws Exception {

    }

    public void testGetAllparametertype() throws Exception {

    }

    public void testGetById() throws Exception {

    }

    public void testGetAllParameterType() throws Exception {

    }

    public void testFindParam() throws Exception {

    }
}