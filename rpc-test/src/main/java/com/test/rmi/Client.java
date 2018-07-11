package com.test.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author tangwei
 * @date 2018/7/11 23:39
 */
public class Client {


    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //先引入服务
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8801/helloService");

        //调用远程方法
        System.out.println("RMI服务器返回的结果是:"+helloService.sayHello("Clay"));

    }
}
