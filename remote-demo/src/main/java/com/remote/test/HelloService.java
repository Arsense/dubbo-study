package com.remote.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author tangwei
 * @date 2018/7/11 18:26
 */
public interface HelloService extends Remote {

    /*
       必须显式声明抛出RemoteException异常
     */
    String sayHello(String guest)throws RemoteException;
}
