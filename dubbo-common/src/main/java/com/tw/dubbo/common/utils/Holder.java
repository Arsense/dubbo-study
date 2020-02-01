package com.tw.dubbo.common.utils;

/**
 * <pre>
 *     临时存放数据的类
 * </pre>
 *
 * @author clay
 * @date 2018/11/29 15:02
 */
public class Holder<T> {

    private  T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
