package com.sunesoft.ancon.core.inContract.application.inContractFactory;

import com.sunesoft.ancon.core.inContract.application.dto.InContractDto;
import com.sunesoft.ancon.core.inContract.domain.InContract;
import com.sunesoft.ancon.fr.Factory;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by xiazl on 2016/11/22.
 */
public class InContractFactory extends Factory{
    /**
     * 将dto 变为实体类
     * @param dto
     * @param contract
     * @return
     */
    public static InContract convertFromDto(InContractDto dto,InContract contract){
        contract=convert(dto,contract);
        if(!StringUtils.isNullOrWhiteSpace(dto.getScreateDateTime()))
        contract.setCreateDateTime(DateHelper.parse(dto.getScreateDateTime(),"yyyy-MM-dd"));
        if(!StringUtils.isNullOrWhiteSpace(dto.getSlastUpdateTime()))
        contract.setLastUpdateTime(DateHelper.parse(dto.getSlastUpdateTime(),"yyyy-MM-dd"));
        if(!StringUtils.isNullOrWhiteSpace(dto.getSbillDate()))
        contract.setBillDate(DateHelper.parse(dto.getSbillDate(),"yyyy-MM-dd"));
        if(!StringUtils.isNullOrWhiteSpace(dto.getSendDate()))
        contract.setEndDate(DateHelper.parse(dto.getSendDate(),"yyyy-MM-dd"));
        return contract;
    }

    /**
     * 将实体类转变成dto
     * @param contract
     * @return
     */
    public  static InContractDto convertToDto(InContract contract){
        InContractDto dto=new InContractDto();
        dto=convert(contract,dto);
        if(contract.getCreateDateTime()!=null)
        dto.setScreateDateTime(DateHelper.formatDate(contract.getCreateDateTime(),"yyyy-MM-dd"));
        if(contract.getLastUpdateTime()!=null)
        dto.setSlastUpdateTime(DateHelper.formatDate(contract.getLastUpdateTime(),"yyyy-MM-dd"));
        if(contract.getBillDate()!=null)
        dto.setSbillDate(DateHelper.formatDate(contract.getBillDate(),"yyyy-MM-dd"));
        if(contract.getEndDate()!=null)
        dto.setSendDate(DateHelper.formatDate(contract.getEndDate(),"yyyy-MM-dd"));
        dto.setDays(getDays(contract.getEndDate(),contract.getBillDate()));//计算工期，
        dto.setNextDays(getDays(contract.getEndDate(),new Date()));//计算剩余天数
        return dto;
    }

    /**
     * 将List<InContract>-》List<InContractDto>
     * @param list
     * @return
     */
    public static List<InContractDto> convertDtoList(List<InContract> list){
        List<InContractDto> dtos=new ArrayList<>();
        for(InContract contract:list){
            dtos.add(convertToDto(contract));
        }
        return dtos;
    }

    /**
     * 得到时间段
     * @param endTime
     * @param beginTime
     * @return
     */
    private static float getDays(Date endTime,Date beginTime){
        if(endTime==null||beginTime==null) return 0f;
        if(beginTime.after(endTime))return 0f;
        //将时间设置为yyyy-MM-dd 00:00:00
        String endString=DateHelper.formatDate(endTime,"yyyy-MM-dd");
        endTime=DateHelper.parse(endString,"yyyy-MM-dd");
        String beginString=DateHelper.formatDate(beginTime,"yyyy-MM-dd");
        beginTime=DateHelper.parse(beginString,"yyyy-MM-dd");

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(endTime);
        Long end=calendar.getTimeInMillis();
        calendar.setTime(beginTime);
        Long begin=calendar.getTimeInMillis();
        Long days=(end-begin)/(1000*3600*24);
        return Float.parseFloat(String.valueOf(days));
    }

}
