package com.sunesoft.ancon.core.uAuth.domain;

import com.sunesoft.ancon.fr.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */

@Entity
@Table(name="ancon_syy_hr_organizations")
public class Deptment extends BaseEntity {

    @Column(name = "dept_name", nullable = false,columnDefinition = "varchar(50) COMMENT '机构名称'")
    private String deptName;

    /**
     * 机构编号
     */
    @Column(name = "dept_no",columnDefinition = "varchar(50) COMMENT '机构编号'")
    private String deptNo;


    @ManyToOne
    @JoinColumn(name = "parent_dept_id",columnDefinition = "bigint COMMENT '上级标识'")
    private Deptment parentDept;

    @Column(name ="dept_status",columnDefinition = "int(2) COMMENT '用户状态：0代表未禁用,1代表禁用'")
    private Integer status;

    @Column(columnDefinition = "varchar(200) COMMENT '机构介绍'")
    private String brief;

    @Column(columnDefinition = "int(2) COMMENT '机构类型： 1:部门,2:独立分公司,3：非独立分公司'")
    private Integer type; // 1:部门  2:独立分公司  3：非独立分公司


    public Deptment() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setStatus(1);
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIdCard() {
        return deptNo;
    }

    public void setIdCard(String idCard) {
        this.deptNo = idCard;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }


    public Deptment getParentDept() {
        return parentDept;
    }

    public void setParentDept(Deptment parentDept) {
        this.parentDept = parentDept;
    }

}


