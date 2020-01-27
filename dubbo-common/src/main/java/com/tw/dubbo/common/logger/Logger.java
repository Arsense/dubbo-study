package com.tw.dubbo.common.logger;

/**
 * @author clay
 * @date 2020/1/24 19:57
 */
public interface Logger {

    /**
     * Logs a message with info log level.
     *
     * @param msg log this message
     */
    void info(String msg);

    /**
     * Logs an error with info log level.
     *
     * @param e log this cause
     */
    void info(Throwable e);



}
