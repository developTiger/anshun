package com.sunesoft.ancon.fr.utils;

import java.util.List;

/**
 * Created by zhouz on 2016/7/16.
 */
public class ArrayUtils {

    /**
     * 判断数组中是否存在某个元素。
     *
     * @param collections 集合。
     * @param element     元素。
     * @return true 表示存在，false 表示不存在。
     */
    public static <T> boolean isExist(T[] collections, T element) {
        for (T collection : collections) {
            if (collection.equals(element))
                return true;
        }
        return false;
    }

    /**
     * 判断数组中是否存在某个元素。
     *
     * @param collections 集合。
     * @param element     元素。
     * @return true 表示存在，false 表示不存在。
     */
    public static <T> boolean isExist(List<T> collections, T element) {
        for (T collection : collections) {
            if (collection.equals(element))
                return true;
        }
        return false;
    }
}
