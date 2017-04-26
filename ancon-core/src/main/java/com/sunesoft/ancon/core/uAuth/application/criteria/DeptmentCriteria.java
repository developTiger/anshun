package com.sunesoft.ancon.core.uAuth.application.criteria;

import com.sunesoft.ancon.fr.results.PagedCriteria;

/**
 * Created by zhouz on 2016/5/19.
 */

public class DeptmentCriteria extends PagedCriteria {

    private String deptNo;
    private String deptName;

    private String deptType;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}
