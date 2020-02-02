package com.tw.dubbo.common.utils;

/**
 * @author clay
 * @date 2018/11/28 19:33
 */
public final class Version {

    private static final String DEFAULT_DUBBO_PROTOCOL_VERSION = "1.0.0";

    private static final String VERSION = getVersion(Version.class, "");

    public static String getProtocolVersion(){
        return DEFAULT_DUBBO_PROTOCOL_VERSION;
    }

    public static String getVersion() {
        return VERSION;
    }


    public static String getVersion(Class<?> cls, String defaultVersion) {

        //TODO
        return "";
    }

}
