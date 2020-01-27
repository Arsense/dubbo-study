package com.tw.dubbo.common.logger.support;

import com.tw.dubbo.common.logger.Logger;

/**
 * 默认的日志打印支持类
 * @author clay
 * @date 2020/1/24 19:59
 */
public class FailsafeLogger implements Logger {
    private Logger logger;


    @Override
    public void info(String msg) {

    }

    @Override
    public void info(Throwable e) {

    }
}
