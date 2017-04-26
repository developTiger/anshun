package com.sunesoft.ancon.core.inContract.application.criteria;

import com.sunesoft.ancon.core.inContract.domain.PayStyle;
import com.sunesoft.ancon.fr.results.PagedCriteria;
import com.sunesoft.ancon.fr.utils.DateHelper;

import java.util.Date;

/**
 * Created by xiazl on 2016/11/23.
 */
public class PayCriteria extends PagedCriteria {
    /**
     * 进项合同id
     */
    private Long id;

    private Date beginTime;

    private Date endTime;

    private Long partyBId;

    private PayStyle payStyle;

    private String contractName;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public PayStyle getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(PayStyle payStyle) {
        this.payStyle = payStyle;
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
        this.endTime = DateHelper.parse(endTime,"yyyy-MM-dd");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
