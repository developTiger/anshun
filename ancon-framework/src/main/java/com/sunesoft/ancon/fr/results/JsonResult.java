package com.sunesoft.ancon.fr.results;

import java.io.Serializable;

/**
 * Created by zhouz on 2016/7/22.
 */
public class JsonResult implements Serializable {
    private final String result ;

    private final Boolean isSuccess;

    private final String msg;


    public JsonResult(String  json,Boolean isSuccess) {
        this.result = json;
        this.isSuccess = true;
        this.msg = "";
    }

    public JsonResult(String errorMsg) {
        result= "";
        this.msg = errorMsg;
        this.isSuccess = false;
    }


    public String getResult() {
        return result;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public String getMsg() {
        return msg;
    }
}
