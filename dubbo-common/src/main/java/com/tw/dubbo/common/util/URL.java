package com.tw.dubbo.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/11/29 10:32
 */
public class URL implements Serializable {


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

    private volatile transient String full;

    private volatile transient String identity;

    private volatile transient String parameter;

    private volatile transient String string;

    protected URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
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
            parameters = new HashMap<String, String>();
        } else {
            parameters = new HashMap<String, String>(parameters);
        }
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static URL valueOf(String url) {
        return new URL();
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
}