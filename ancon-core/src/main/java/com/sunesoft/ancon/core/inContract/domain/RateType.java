package com.sunesoft.ancon.core.inContract.domain;

/**
 * Created by xiazl on 2016/11/30.
 */
public enum RateType {

    General("一般计税",1),
    Simple("简易征税",2);

    private String name;
    private int index;

    RateType(String name,int index) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    /**
     * 根据索引得到名称
     *
     * @param index
     * @return
     */
    public String getNameByIndex(Integer index) {
        for (InvoiceType type : InvoiceType.values()) {
            if (type.getIndex() == index) return type.getName();
        }
        return null;
    }

    public InvoiceType getType(int index){
        for (InvoiceType type : InvoiceType.values()) {
            if (type.getIndex() == index) return type;
        }
        return null;
    }
    /**
     * 根据名得到索引
     *
     * @param name
     * @return
     */
    public Integer getIndexByName(String name) {
        for (InvoiceType type : InvoiceType.values()) {
            if (type.getName().equals(name)) return type.getIndex();
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name+"_"+this.index;
    }
}
