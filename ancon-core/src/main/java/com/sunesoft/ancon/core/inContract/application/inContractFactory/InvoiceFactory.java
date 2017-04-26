package com.sunesoft.ancon.core.inContract.application.inContractFactory;

import com.sunesoft.ancon.core.inContract.application.dto.InvoiceDto;
import com.sunesoft.ancon.core.inContract.domain.Invoice;
import com.sunesoft.ancon.core.inContract.domain.InvoiceType;
import com.sunesoft.ancon.fr.Factory;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2016/11/23.
 */
public class InvoiceFactory extends Factory {
    /**
     * 将dto 转为实体类
     *
     * @param dto
     * @param invoice
     * @return
     */
    public static Invoice convertFromDto(InvoiceDto dto, Invoice invoice) {
        invoice = convert(dto, invoice);
        if (!StringUtils.isNullOrWhiteSpace(dto.getScreateDateTime()))
            invoice.setCreateDateTime(DateHelper.parse(dto.getScreateDateTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getSlastUpdateTime()))
            invoice.setLastUpdateTime(DateHelper.parse(dto.getSlastUpdateTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getSinvoiceDate()))
            invoice.setInvoiceDate(DateHelper.parse(dto.getSinvoiceDate(), "yyyy-MM-dd"));
        if (dto.getType().equals(InvoiceType.Special)) {
            invoice.setSmoney(dto.getMoney());
        } else {
            invoice.setCmoney(dto.getMoney());
        }
        return invoice;
    }

    /**
     * 将实体类转为 dto
     *
     * @param invoice
     * @return
     */
    public static InvoiceDto convertToDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        dto = convert(invoice, dto);
        if (invoice.getLastUpdateTime() != null)
            dto.setSlastUpdateTime(DateHelper.formatDate(invoice.getLastUpdateTime(), "yyyy-MM-dd"));
        if (invoice.getCreateDateTime() != null)
            dto.setScreateDateTime(DateHelper.formatDate(invoice.getCreateDateTime(), "yyyy-MM-dd"));
        if (invoice.getInvoiceDate() != null)
            dto.setSinvoiceDate(DateHelper.formatDate(invoice.getInvoiceDate(), "yyyy-MM-dd"));
        if (invoice.getRateType() != null)
            dto.setRateTypeName(invoice.getRateType().getName());
        if (invoice.getType() != null)
            dto.setTypeName(invoice.getType().getName());
        if (invoice.getSmoney() != null)
            dto.setMoney(invoice.getSmoney());
        else
            dto.setMoney(invoice.getCmoney());
        return dto;
    }

    /**
     * 实体类集合转化为dto 集合
     *
     * @param invoices
     * @return
     */
    public static List<InvoiceDto> convertList(List<Invoice> invoices,List<Invoice> list) {
        List<InvoiceDto> dtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDto dto = convertToDto(invoice);
            dtos.add(dto);
        }
        BigDecimal t=setTotalMoney(list);
        for(InvoiceDto dto:dtos){
            dto.setTotalMoney(t);
        }
        return dtos;
    }

    private static BigDecimal setTotalMoney(List<Invoice> list){
        BigDecimal totalMoney=BigDecimal.ZERO;
        if(list!=null&&list.size()>0){
            for(Invoice invoice:list){
                if(invoice.getCmoney()==null&&invoice.getSmoney()==null){
                    totalMoney=totalMoney.add(BigDecimal.ZERO);
                }else {
                    totalMoney=totalMoney.add(invoice.getCmoney()!=null?invoice.getCmoney():invoice.getSmoney());
                }
            }
        }
        return totalMoney;

    }
}


