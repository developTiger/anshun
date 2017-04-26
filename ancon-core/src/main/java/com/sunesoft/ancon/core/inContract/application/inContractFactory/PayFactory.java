package com.sunesoft.ancon.core.inContract.application.inContractFactory;

import com.sunesoft.ancon.core.inContract.application.dto.PayDto;
import com.sunesoft.ancon.core.inContract.domain.Pay;
import com.sunesoft.ancon.fr.Factory;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2016/11/23.
 */
public class PayFactory extends Factory {

    /**
     * 实体类装变成dto
     *
     * @param pay
     * @return
     */
    public static PayDto convertToDto(Pay pay) {
        PayDto dto = new PayDto();
        dto = convert(pay, dto);
        if (pay.getCreateDateTime() != null)
            dto.setScreateDateTime(DateHelper.formatDate(pay.getCreateDateTime(), "yyyy-MM-dd"));
        if (pay.getLastUpdateTime() != null)
            dto.setSlastUpdateTime(DateHelper.formatDate(pay.getLastUpdateTime(), "yyyy-MM-dd"));
        if (pay.getPayTime() != null)
            dto.setSpayTime(DateHelper.formatDate(pay.getPayTime(), "yyyy-MM-dd"));
        if(pay.getPayStyle()!=null)
            dto.setPayStyleName(pay.getPayStyle().getName());
        return dto;
    }

    /**
     * 将dto 转变为实体类(这里差一个实体对象的赋值)
     *
     * @param dto
     * @param pay
     * @return
     */
    public static Pay convertFromDto(PayDto dto, Pay pay) {
        pay = convert(dto, pay);
        if (!StringUtils.isNullOrWhiteSpace(dto.getScreateDateTime()))
            pay.setCreateDateTime(DateHelper.parse(dto.getScreateDateTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getSlastUpdateTime()))
            pay.setLastUpdateTime(DateHelper.parse(dto.getSlastUpdateTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getSpayTime()))
            pay.setPayTime(DateHelper.parse(dto.getSpayTime(), "yyyy-MM-dd"));
        return pay;
    }

    /**
     * 将List<Pay>变为List<PayDto>
     *
     * @param pays
     * @return
     */
    public static List<PayDto> convertList(List<Pay> pays,List<Pay> list) {
        List<PayDto> dtos = new ArrayList<>();
        for (Pay pay : pays) {
            dtos.add(convertToDto(pay));
        }
        BigDecimal t=totalMoney(list);//只调用一遍
        for(PayDto dto:dtos){
            dto.setTotalMoney(t);
        }
        return dtos;
    }


    public static BigDecimal totalMoney(List<Pay> list){
        BigDecimal t=BigDecimal.ZERO;
        if(list!=null&&list.size()>0){
            for(Pay pay:list){
                t=t.add(pay.getMoney()!=null?pay.getMoney():BigDecimal.ZERO);
            }
        }
        return t;

    }
}
