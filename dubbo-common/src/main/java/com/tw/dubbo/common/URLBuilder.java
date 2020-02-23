package com.tw.dubbo.common;

import com.tw.dubbo.common.utils.CollectionUtils;
import com.tw.dubbo.common.utils.StringUtils;
import com.tw.dubbo.common.utils.URL;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * @author clay
 * @date 2020/2/2 11:50
 */
public class URLBuilder {

    private String protocol;

    private String username;

    private String password;

    /**
     *  by default, host to registry
     */
    private String host;

    /**
     * by default, port to registry
     */
    private int port;

    private String path;

    private Map<String, String> parameters;

    private Map<String, Map<String, String>> methodParameters;


    public URLBuilder() {
        protocol = null;
        username = null;
        password = null;
        host = null;
        port = 0;
        path = null;
        parameters = new HashMap<>();
        methodParameters = new HashMap<>();
    }

    public URLBuilder(String protocol,
                      String username,
                      String password,
                      String host,
                      int port,
                      String path, Map<String, String> parameters,
                      Map<String, Map<String, String>> methodParameters) {
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters != null ? parameters : new HashMap<>();
        this.methodParameters = (methodParameters != null ? methodParameters : new HashMap<>());
    }



    public static URLBuilder from(URL url) {
        String protocol = url.getProtocol();
        String username = url.getUsername();
        String password = url.getPassword();
        String host = url.getHost();
        int port = url.getPort();
        String path = url.getPath();
        Map<String, String> parameters = new HashMap<>(url.getParameters());
        Map<String, Map<String, String>> methodParameters = new HashMap<>(url.getMethodParameters());
        return new URLBuilder(
                protocol,
                username,
                password,
                host,
                port,
                path,
                parameters,
                methodParameters);
    }

    public URLBuilder setAddress(String address) {
        int i = address.lastIndexOf(':');
        String host;
        int port = this.port;
        if (i >= 0) {
            host = address.substring(0, i);
            port = Integer.parseInt(address.substring(i + 1));
        } else {
            host = address;
        }
        this.host = host;
        this.port = port;
        return this;
    }

    public URLBuilder clearParameters() {
        parameters.clear();
        return this;
    }

    public URLBuilder addParameter(String key, int value) {
        return addParameter(key, String.valueOf(value));
    }

    public URLBuilder addParameter(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }

        parameters.put(key, value);
        return this;
    }

    public URL build() {
        if (StringUtils.isEmpty(username) && StringUtils.isNotEmpty(password)) {
            throw new IllegalArgumentException("Invalid url, password without username!");
        }
        if (CollectionUtils.isEmptyMap(methodParameters)) {
            return new URL(protocol, username, password, host, port, path, parameters);
        } else {
            return new URL(protocol, username, password, host, port, path, parameters, methodParameters);
        }
    }

    public URLBuilder addParameterIfAbsent(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return this;
        }
        if (hasParameter(key)) {
            return this;
        }
        parameters.put(key, value);
        return this;
    }

    public boolean hasParameter(String key) {
        String value = getParameter(key);
        return StringUtils.isNotEmpty(value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public URLBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public URLBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public URLBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public URLBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public URLBuilder setPort(int port) {
        this.port = port;
        return this;
    }


    public String getProtocol() {
        return protocol;
    }


    public URLBuilder removeParameters(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return this;
        }
        return removeParameters(keys.toArray(new String[0]));
    }

    public URLBuilder removeParameters(String... keys) {
        if (keys == null || keys.length == 0) {
            return this;
        }
        for (String key : keys) {
            parameters.remove(key);
        }
        return this;
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

    public URLBuilder setPath(String path) {
        this.path = path;
        return this;
    }



    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Map<String, String>> getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(Map<String, Map<String, String>> methodParameters) {
        this.methodParameters = methodParameters;
    }
}
