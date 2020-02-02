package com.tw.dubbo.common;

import com.tw.dubbo.common.utils.URL;

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

    // by default, host to registry
    private String host;

    // by default, port to registry
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

    public URL build() {

        return new URL();
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

    public void setPath(String path) {
        this.path = path;
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
