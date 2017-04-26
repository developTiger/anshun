package com.sunesoft.ancon.core.inContract.domain;

/**
 * Created by xiazl on 2016/11/23.
 */
public enum PayStyle {


        Cash("现金",0),
        BankTransfer("银行转账",1),
        Remittance("汇票",2),
        Cheque("支票",3),
        RomissoryNote("本票",4),
        Other("其他",5);

        /**
         * 名称
         */
        private String name;

        /**
         * 索引
         */
        private int index;

    PayStyle(String name,int index) {
            this.index = index;
            this.name = name;
        }

        /**
         * 根据索引得到名称
         *
         * @param index
         * @return
         */
        private String getNameByIndex(int index) {
            for (PayStyle type : PayStyle.values()) {
                if (type.getIndex() == index) {
                    return type.getName();
                }
            }
            return null;
        }

        /**
         * 根据名称得到索引
         *
         * @param name
         * @return
         */
        private Integer getIndexByName(String name) {
            for (PayStyle type : PayStyle.values()) {
                if (type.getName().equals(name))
                    return type.getIndex();
            }
            return null;
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
         * 重写toString 方法
         *
         * @return
         */
        @Override
        public String toString() {
            return this.name + "_" + this.index;
        }

    }


