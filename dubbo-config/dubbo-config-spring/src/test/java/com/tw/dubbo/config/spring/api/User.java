package com.tw.dubbo.config.spring.api;

import java.io.Serializable;

/**
 * @author clay
 * @date 2020/1/23 13:13
 */
public class User implements Serializable {


    private static final long serialVersionUID = 1L;

    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
