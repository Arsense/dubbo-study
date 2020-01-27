package com.tw.dubbo.config.spring.api;

import java.util.List;

/**
 * @author clay
 * @date 2020/1/23 13:13
 */
public interface DemoService {
    String sayName(String name);

    Box getBox();

    void throwDemoException() throws DemoException;

    List<User> getUsers(List<User> users);

    int echo(int i);
}
