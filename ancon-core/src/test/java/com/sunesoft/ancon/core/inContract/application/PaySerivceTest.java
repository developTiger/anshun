package com.sunesoft.ancon.core.inContract.application;

import com.sunesoft.ancon.core.inContract.application.dto.PayDto;
import com.sunesoft.ancon.core.inContract.domain.PayStyle;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PaySerivceTest extends TestCase {
    @Autowired
    PayService serivce;

    @Test
    public void testCreate() throws Exception {
//        for(int i=0;i<10;i++) {
//            PayDto dto = new PayDto();
//            dto.setPayStyle(PayStyle.Cash);
//            dto.setPartyB("y");
//            dto.setOptionName("x");
//            dto.setMoney(BigDecimal.TEN);
//            dto.setSpayTime("2016-10-14");
//            dto.setProof("photos");
//            dto.setInNum("xx");
//            serivce.create(dto);
//        }

    }
    @Test
    public void testEdit() throws Exception {

    }
    @Test
    public void testGetById() throws Exception {

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

    @Test
    public void dsf() throws Exception {
        String s1="ab"+"c";
        String s2="abc";
        System.out.println(s2 +":"+s1.equals(s2));
    }
}