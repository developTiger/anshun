package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.application.dto.InvoiceDto;
import com.sunesoft.ancon.core.inContract.domain.ContractSpeed;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InContractServiceTest extends TestCase {

    @Autowired
    InContractService service;

    @Test
    public void testCreate() throws Exception {
//        for(int i=0;i<10;i++) {
//            InContractDto dto = new InContractDto();
//            dto.setNum("xx2"+i);
//            dto.setName("xx2" + i);
//            dto.setSbillDate((new Date()).toString());
//            dto.setStatus(ContractSpeed.Run);
////            dto.setBillMoney(BigDecimal.valueOf(88f));
//            dto.setValue(BigDecimal.TEN);
//            dto.setPartyB("y3");
//            CommonResult r = service.create(dto);
//            if (r.getIsSuccess()) {
//                System.out.println(r.getIsSuccess());
//            } else {
//                System.out.println("shibai ");
//            }
//        }

    }

    @Test
    public void testEdit() throws Exception {
//        InContractDto dto = new InContractDto();
//        dto.setId(1l);
//        dto.setNum("xx");
//        dto.setName("xx");
//        dto.setOptionName("tt");
//        dto.setRemark("你好");
//        dto.setValue(BigDecimal.valueOf(1000f));
//        dto.setPayMoney(BigDecimal.valueOf(50f));
//        dto.setPartyB("pratyB");
//        dto.setSbillDate((new Date()).toString());
//        dto.setStatus(ContractSpeed.Run);
//        dto.setBillMoney(BigDecimal.valueOf(88f));
//        CommonResult r = service.edit(dto);
//        if (r.getIsSuccess()) {
//            System.out.println(r.getIsSuccess());
//        } else {
//            System.out.println("shibao ");
//        }
    }

    @Test
    public void testGetById() throws Exception {
//        InContractDto dto=service.getById(1l);
//        if(dto!=null){
//            System.out.println("cg");
//        }else
//            System.out.println("sb");

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
    public void testGetByName() throws Exception {

    }

    @Test
    public void testPg() throws Exception {

    }
    @Test
    public void testgetMap() throws Exception {
//        String d2= DateHelper.formatDate(new Date(),"yyyy-MM-dd");
//        String d1= DateHelper.formatDate(DateHelper.addMonth(new Date(),-2),"yyyy-MM-dd");
//
//        List<Map<String,Object>> map= service.getCount(d1, d2);
//        System.out.println(map.size());


    }

}