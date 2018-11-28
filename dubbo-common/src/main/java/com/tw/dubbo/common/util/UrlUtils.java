package com.tw.dubbo.common.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/11/28 19:37
 */
public class UrlUtils {

    public static List<URL> parseURLs(String address, Map<String, String> params) {
        if (address == null || address.length() == 0) {
            return null;
        }
        List<URL> registries = new ArrayList<URL>();
        registries.add(parseURL(address, params));
        return registries;

    }

    public static URL parseURL(String address, Map<String, String> defaults) {
        if (address == null || address.length() == 0) {
            return null;
        }
        String url;
        return null;
    }
}
