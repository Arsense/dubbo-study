package com.remote.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangwei
 * @date 2018/8/30 16:19
 */
public class LogBackTest {
    private static final Logger logger = LoggerFactory.getLogger(LogBackTest.class);

    public static void main(String[] args) {

        logger.debug("DEBUG TEST 这个地方输出DEBUG级别的日志");
        logger.info("INFO test 这个地方输出INFO级别的日志");
        logger.error("ERROR test 这个地方输出ERROR级别的日志");
    }
}
