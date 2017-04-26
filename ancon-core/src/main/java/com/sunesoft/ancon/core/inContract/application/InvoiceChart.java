package com.sunesoft.ancon.core.inContract.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2016/12/9.
 */
public class InvoiceChart {
    /**
     * 乙方
     */
    private List<String> partyB;
    /**
     * 专票金额
     */
    private List<String> s;
    /**
     * 普票金额
     */
    private List<String> c;

    public List<String> getC() {
        return c==null?new ArrayList<String>():c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }

    public List<String> getPartyB() {
        return partyB==null?new ArrayList<String>():partyB;
    }

    public void setPartyB(List<String> partyB) {
        this.partyB = partyB;
    }

    public List<String> getS() {
        return s==null?new ArrayList<String>():s;
    }

    public void setS(List<String> s) {
        this.s = s;
    }
}
