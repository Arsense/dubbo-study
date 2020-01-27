package com.tw.dubbo.common.logger.log4j;

import com.tw.dubbo.common.logger.Logger;
import com.tw.dubbo.common.logger.support.FailsafeLogger;

/**
 * @author clay
 * @date 2020/1/24 19:58
 */
public class Log4jLogger  implements Logger {
    private final org.apache.log4j.Logger logger;


    private static final String FQCN = FailsafeLogger.class.getName();


    public Log4jLogger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }


    @Override
    public void info(String msg) {

    }

    @Override
    public void info(Throwable e) {

    }
}
