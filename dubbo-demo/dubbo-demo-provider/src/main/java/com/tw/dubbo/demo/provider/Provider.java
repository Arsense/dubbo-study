package com.tw.dubbo.demo.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tangwei
 * @date 2018/11/27 12:15
 */
public class Provider {

    public static void main(String[] args) throws IOException, BeansException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        //手动加载配置 然后开启
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-demo-provider.xml"});
        context.start();
        System.out.println("provider ok");
        System.in.read();
    }
}
