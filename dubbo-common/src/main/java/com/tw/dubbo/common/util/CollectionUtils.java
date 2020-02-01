package com.tw.dubbo.common.util;

import java.util.Collection;

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
}