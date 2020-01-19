package com.tw.dubbo.rpc;

/**
 * @author clay
 * @date 2018/11/30 14:58
 */
public interface Result {

    Object getValue();

    /**
     * Get exception.
     *
     * @return exception. if no exception return null.
     */
    Throwable getException();

    /**
     * Has exception.
     *
     * @return has exception.
     */
    boolean hasException();
}
