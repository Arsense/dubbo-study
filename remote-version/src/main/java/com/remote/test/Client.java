package com.remote.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author tangwei
 * @date 2018/7/11 23:39
 */
public class Client {

    private final static Logger LOG =  LoggerFactory.getLogger(Client.class);

    public static void main(String[] args){
//        //先引入服务
//        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8801/helloService");
//
//        //调用远程方法
//        System.out.println("RMI服务器返回的结果是:"+helloService.sayHello("Clay"));


        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rpc-client.xml");

        TestService testService = (TestService) context.getBean("testService");
        List list = testService.genericTest();

        LOG.info("Client 开始工作");
        for(Object s :  list){
            System.out.println("list return ==>" + s);
        }
        //关闭jvm
        System.exit(0);
    }
}
