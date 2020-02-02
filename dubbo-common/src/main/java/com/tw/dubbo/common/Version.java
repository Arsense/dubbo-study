package com.tw.dubbo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author clay
 * @date 2020/2/2 2:06
 */
public final class Version {

    private static final Logger logger = LoggerFactory.getLogger(Version.class);


    public static final String DEFAULT_DUBBO_PROTOCOL_VERSION = "2.0.2";

    public static String getProtocolVersion() {
        return DEFAULT_DUBBO_PROTOCOL_VERSION;
    }

}
