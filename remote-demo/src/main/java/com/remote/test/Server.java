package com.remote.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


/** 原因在于需要继承UnicastRemoteObject
 * @author tangwei
 * @date 2018/7/11 18:35
 */
public class Server {


    private final static Logger LOG =  LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        //创建注册服务

//        HelloService helloService = new HelloServiceImpl();
//        LocateRegistry.createRegistry(8801);
//
//        Naming.bind("rmi://localhost:8801/helloService",helloService);
//        System.out.println("Server provide RPC serivce now");

        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rpc-server.xml");
        LOG.info("服务发布");

    }
}
