package com.sunesoft.ancon.core.inContract.application.criteria;

import com.sunesoft.ancon.core.inContract.domain.InvoiceType;
import com.sunesoft.ancon.fr.results.PagedCriteria;
import com.sunesoft.ancon.fr.utils.DateHelper;

import java.util.Date;

/**
 * Created by xiazl on 2016/11/23.
 */
public class InvoiceCriteria extends PagedCriteria {

    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 发票类型
     */
    private InvoiceType type;
    /**
     * 进项合同id
     */
    private Long id;

    private Long partyBId;

    private String contract;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = DateHelper.parse(beginTime,"yyyy-MM-dd");
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime =  DateHelper.parse(endTime,"yyyy-MM-dd");
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }
}
