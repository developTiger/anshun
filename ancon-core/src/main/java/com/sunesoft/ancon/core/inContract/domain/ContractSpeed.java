package com.sunesoft.ancon.core.inContract.domain;

/**
 * 合同状态（合同进度）
 * Created by xiazl on 2016/11/22.
 */
public enum ContractSpeed {

    Run("施工中",1),
    Finish("已完结",2);

    private String name;

    private int index;


    ContractSpeed(String name,int index) {
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
     * @param index
     * @return
     */
    public String getNameByIndex(int index){
        for(ContractSpeed speed:ContractSpeed.values()){
            if(speed.getIndex()==index)return speed.getName();
        }
        return null;
    }

    /**
     * 根据名得到索引
     * @param name
     * @return
     */
    public Integer getIndexByName(String name){
        for(ContractSpeed speed:ContractSpeed.values()){
            if(speed.getName().equals(name))return speed.getIndex();
        }
        return null;
    }

//    @Override
//    public String toString() {
//        return this.name+"_"+this.index;
//    }

}
