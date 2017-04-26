package com.sunesoft.ancon.fr.results;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by zhouz on 2016/6/4.
 */

public class ResultFactory {

    public static PagedResult pagedResultEmpty(){
        return  new PagedResult(1,10);
    }


    public static CommonResult commonError(String message){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return  new CommonResult(false,message);
    }

    public static  CommonResult commonSuccess(){
        return new CommonResult(true);
    }

    public static  CommonResult commonSuccess(Long id){
        return new CommonResult(true,"",id);
    }
}
