package com.sunesoft.ancon.fr;


import com.sunesoft.ancon.fr.utils.BeanUtils;

/**
 * Created by xiazl on 2016/11/23.
 */
public class Factory {

    /**
     * 创建新对象的复制方法（target.newInstance()）
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S,T> T convert(S source,Class<T> target){
        T t=null;
        try {
            t=target.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source,t);
        return t;

    }

    /**
     * 不需要创建新对象的方法
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static  <S,T> T convert(S source,T target){
        BeanUtils.copyProperties(source,target);
        return target;
    }
}
