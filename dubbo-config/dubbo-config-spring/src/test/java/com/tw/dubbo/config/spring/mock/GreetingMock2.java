package com.tw.dubbo.config.spring.mock;

import com.tw.dubbo.config.spring.api.Greeting;

/**
 * @author clay
 * @date 2020/1/22 22:38
 */
public class GreetingMock2  implements Greeting {

    private GreetingMock2() {
    }

    @Override
    public String hello() {
        return "mock";
    }
}
