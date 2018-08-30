package com.test.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/** 原因在于需要继承UnicastRemoteObject
 * @author tangwei
 * @date 2018/7/11 18:35
 */
public class Server {


    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        //创建注册服务

//        HelloService helloService = new HelloServiceImpl();
//        LocateRegistry.createRegistry(8801);
//
//        Naming.bind("rmi://localhost:8801/helloService",helloService);
//        System.out.println("Server provide RPC serivce now");

    }
}
