package com.tw.dubbo.rpc;

/**
 * @author tangwei
 * @date 2018/12/3 9:45
 */
public class ProtocolUtils {



    public static String serviceKey(int port, String serviceName, String serviceVersion, String serviceGroup) {
        StringBuilder buffer = new StringBuilder();

        if (serviceGroup != null && serviceGroup.length() > 0) {
            buffer.append(serviceGroup);
            buffer.append("/");
        }
        buffer.append(serviceName);
        if (serviceVersion != null && serviceVersion.length() > 0 && !"0.0.0".equals(serviceVersion)) {
            buffer.append(":");
            buffer.append(serviceVersion);
        }
        buffer.append(":");
        buffer.append(port);
        return buffer.toString();


    }
}
