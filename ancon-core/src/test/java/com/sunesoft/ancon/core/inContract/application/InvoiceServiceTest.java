package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.inContract.application.dto.InvoiceDto;
import com.sunesoft.ancon.core.inContract.domain.Invoice;
import com.sunesoft.ancon.core.inContract.domain.InvoiceType;
import com.sunesoft.ancon.core.inContract.domain.RateType;
import com.sunesoft.ancon.fr.results.CommonResult;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InvoiceServiceTest extends TestCase {

    @Autowired
    InvoiceService service;
    @Test
    public void testCreate() throws Exception {
//        for(int i=0;i<6;i++){
//            InvoiceDto dto = new InvoiceDto();
//            dto.setNum("w5"+i+1);
//            dto.setType(InvoiceType.Special);
//            dto.setMoney(BigDecimal.valueOf(3));
//            dto.setRate(BigDecimal.ONE);
//            dto.setPartyB("sunee");
//            dto.setEdtitor("xx");
//            dto.setOptionName("xx");
//            dto.setRateType(RateType.General);
//            dto.setInNum("xx");
//            dto.setSinvoiceDate("2016-12-09");
//
//            CommonResult result=service.create(dto);
//            if(result!=null){
//                System.out.println(result.getIsSuccess());
//            }
//
//        }


    }
    @Test
    public void testEdit() throws Exception {

    }
    @Test
    public void testGetById() throws Exception {

    }
    @Test
    public void testGetByNum() throws Exception {

    }
    @Test
    public void testDelete() throws Exception {

    }
    @Test
    public void testDelete1() throws Exception {

    }
    @Test
    public void testPg() throws Exception {

    }
}