package com.tw.dubbo.common.utils;

import java.util.*;

/**
 * @author clay
 * @date 2020/1/23 18:25
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmptyMap(Map map) {
        return map == null || map.size() == 0;
    }

}
