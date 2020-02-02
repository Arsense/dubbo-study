package com.tw.dubbo.common.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author clay
 * @date 2018/11/29 10:32
 */
public class URL implements Serializable {

    private String full;

    private final String protocol;

    private final String username;

    private final String password;

    // by default, host to registry
    private final String host;

    // by default, port to registry
    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    // ==== cache ====

    private volatile transient Map<String, Number> numbers;

    private volatile transient Map<String, URL> urls;

    private volatile transient String ip;


    private volatile transient String identity;

    private volatile transient String parameter;

    private volatile transient String string;
    private final Map<String, Map<String, String>> methodParameters ;

    public Map<String, Map<String, String>> getMethodParameters() {
        return methodParameters;
    }

    public URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
        this.methodParameters = null;

    }

    public String toFullString() {
        if (full != null) {
            return full;
        }
        return full = buildString(true, true);
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        if (value == null || value.length() == 0) {
            return null;
        }
        return value;
    }

    public int getPositiveParameter(String key, int defaultValue) {
        if (defaultValue <= 0) {
            throw new IllegalArgumentException("defaultValue <= 0");
        }
        int value = getParameter(key, defaultValue);
        if (value <= 0) {
            return defaultValue;
        }
        return value;
    }

    public String getParameter(String key, String defaultValue) {
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        return value;
    }

    public int getParameter(String key, int defaultValue) {
        Number n = getNumbers().get(key);
        if (n != null) {
            return n.intValue();
        }
        String value = getParameter(key);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        int i = Integer.parseInt(value);
        getNumbers().put(key, i);
        return i;
    }

    public URL addParameter(String key, String value) {
        if (StringUtils.isEmpty(key)
                || StringUtils.isEmpty(value)) {
            return this;
        }


        return null;

    }

    public URL setHost(String host) {
        return new URL(protocol, username, password, host, port, path, getParameters());
    }

    private String buildString(boolean appendUser, boolean appendParameter, String... parameters) {
        return buildString(appendUser, appendParameter, false, false, parameters);
    }

    public URL setProtocol(String protocol) {
        return new URL(protocol, username, password, host, port, path, getParameters());
    }

    public URL setPort(int port) {
        return new URL(protocol, username, password, host, port, path, getParameters());
    }

    //后面不定参的形式
    private String buildString(boolean appendUser, boolean appendParameter, boolean useIP, boolean useService, String... parameters) {
        StringBuilder buf = new StringBuilder();
        if (protocol != null && protocol.length() > 0) {
            buf.append(protocol);
            buf.append("://");
        }
        String host;
        if (useIP) {
            host = getIp();
        } else {
            host = getHost();
        }
        if (host != null && host.length() > 0) {
            buf.append(host);
            if (port > 0) {
                buf.append(":");
                buf.append(port);
            }
        }
        String path;
        if (useService) {
//            path = getServiceKey();
        } else {
            path = getPath();
        }
        return buf.toString();
    }
    public InetSocketAddress toInetSocketAddress() {
        return new InetSocketAddress(host, port);
    }



    public URL(String protocol, String username, String password, String host, int port, String path, Map<String, String> parameters) {
        if ((username == null || username.length() == 0)
                && password != null && password.length() > 0) {
            throw new IllegalArgumentException("Invalid url, password without username!");
        }
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = (port < 0 ? 0 : port);
        // trim the beginning "/"
        while (path != null && path.startsWith("/")) {
            path = path.substring(1);
        }
        this.path = path;
        if (parameters == null) {
            parameters = new HashMap<>();
        } else {
            parameters = new HashMap<>(parameters);
        }
        this.parameters = Collections.unmodifiableMap(parameters);
        this.methodParameters = new HashMap<>();

    }

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this(protocol, null, null, host, port, path, parameters);
    }

    public static URL valueOfUrl(String url) {
        //初始化构建URL
        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        // 有木有传参
        int i = url.indexOf("?");
        Map<String, String> parameters = null;
        if (i >= 0) {

        } else {
            i = url.indexOf("://");
            if (i == 0) {
                throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            }
            protocol = url.substring(0, i);
            //越过 ://
            url = url.substring(i + 3);
        }

        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if (url.length() > 0) {
            host = url;
        }

        return new URL(protocol, username, password, host, port, path, parameters);

    }

    public String getAddress() {
        return  host + ":" + port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(Map<String, Number> numbers) {
        this.numbers = numbers;
    }

    public Map<String, URL> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, URL> urls) {
        this.urls = urls;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public static String encode(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将字符串解析成URL
     * @param url
     * @return
     * @see URL
     */
    public static URL valueOf(String url) {

        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }

        //初始化url需要的变量
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;

        int i = url.indexOf('?');

        // 是否有额外参数需要解析
        if (i >= 0) {

        }
        //开始协议格式校验
        i = url.indexOf("://");
        if (i >= 0) {
            if (i == 0) {
                throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            }
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            //处理文件格式协议
            // case: file:/path/to/file.txt
            i = url.indexOf(":/");
            if (i >= 0) {
                if (i == 0) {
                    throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                }
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }

        }
        //injvm://127.0.0.1/TestService 解析 TestService 部分
        i = url.indexOf('/');
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.lastIndexOf('@');
        if (i >= 0) {
            username = url.substring(0, i);
            int j = username.indexOf(':');
            if (j >= 0) {
                password = username.substring(j + 1);
                username = username.substring(0, j);
            }
            url = url.substring(i + 1);
        }
        i = url.lastIndexOf(':');
        if (i >= 0 && i < url.length() - 1) {
            if (url.lastIndexOf('%') > i) {
                // ipv6 address with scope id
                // e.g. fe80:0:0:0:894:aeec:f37d:23e1%en0
                // see https://howdoesinternetwork.com/2013/ipv6-zone-id
                // ignore
            } else {
                port = Integer.parseInt(url.substring(i + 1));
                url = url.substring(0, i);
            }
        }
        if (url.length() > 0) {
            host = url;
        }

        return new URL(protocol, username, password, host, port, path, parameters);
    }
}
