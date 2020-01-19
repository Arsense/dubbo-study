package com.tw.dubbo.demo.provider;

import com.tw.dubbo.demo.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author clay
 * @date 2018/11/27 12:18
 */
public class DemoServiceImpl  implements DemoService {
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: ");
        return "Hello " + name + ", response from provider: ";
    }
}
