package com.sunesoft.ancon.fr.utils;

import org.apache.poi.ss.formula.functions.T;

/**
 * Created by admin on 2016/11/24.
 */
public class DtoFactory {

    public static <S,T> T convert(S source,T target){
        BeanUtils.copyProperties(source,target);
        return target;
    }

}
