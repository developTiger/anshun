package com.sunesoft.ancon.core.companys.application.criteria;

import com.sunesoft.ancon.core.companys.domain.OwnerShip;
import com.sunesoft.ancon.fr.results.PagedCriteria;


/**
 * Created by xiazl on 2016/11/22.
 */
public class SecondPartyCriteria extends PagedCriteria{

    private String num;
    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 所有制性质
     */
    private OwnerShip ownerShip;

    /**
     *代表人
     */
    private String RepresentativePerson;

    public SecondPartyCriteria() {
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OwnerShip getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(OwnerShip ownerShip) {
        this.ownerShip = ownerShip;
    }

    public String getRepresentativePerson() {
        return RepresentativePerson;
    }

    public void setRepresentativePerson(String representativePerson) {
        RepresentativePerson = representativePerson;
    }
}
