package com.tw.dubbo.config.spring.provider.impl;

import com.tw.dubbo.config.spring.api.Box;
import com.tw.dubbo.config.spring.api.DemoException;
import com.tw.dubbo.config.spring.api.DemoService;
import com.tw.dubbo.config.spring.api.User;

import java.util.List;

/**
 * @author tangwei
 * @date 2020/1/23 13:12
 */
public class DemoServiceImpl  implements DemoService {
    @Override
    public String sayName(String name) {
        return null;
    }

    @Override
    public Box getBox() {
        return null;
    }

    @Override
    public void throwDemoException() throws DemoException {

    }

    @Override
    public List<User> getUsers(List<User> users) {
        return null;
    }

    @Override
    public int echo(int i) {
        return 0;
    }
}
