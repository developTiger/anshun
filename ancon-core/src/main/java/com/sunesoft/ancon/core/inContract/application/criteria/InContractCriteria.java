package com.sunesoft.ancon.core.inContract.application.criteria;

import com.sunesoft.ancon.core.inContract.domain.ContractSpeed;
import com.sunesoft.ancon.fr.results.PagedCriteria;
import com.sunesoft.ancon.fr.utils.DateHelper;

import java.util.Date;


/**
 * Created by xiazl on 2016/11/22.
 */
public class InContractCriteria extends PagedCriteria{


    /**
     * 合同名称
     */
    private String name;
    /**
     * 乙方名称(yongdao)
     */
    private Long partyBId;

    /**
     * 开始时间(yongdao )
     */
    private Date beginTime;
    /**
     * 结束时间(yongdao)
     */
    private Date endTime;
    /**
     * 登记人
     */
    private String optionName;

    public InContractCriteria() {
    }


    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 转换日期格式就是在这里
     * @param beginTime
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = DateHelper.parse(beginTime,"yyyy-MM-dd");
    }

    public Date getEndTime() {
        return endTime;
    }
    /**
     * 转换日期格式就是在这里
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = DateHelper.parse(endTime,"yyyy-MM-dd");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getPartyBId() {
        return partyBId;
    }

    public void setPartyBId(Long partyBId) {
        this.partyBId = partyBId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
