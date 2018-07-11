package service;

import service.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/** RMI test
 * @author tangwei
 * @date 2018/7/11 18:26
 */
public class HelloServiceImpl  extends UnicastRemoteObject implements HelloService {


    public String sayHello(String guest) throws RemoteException {
        return "hello,"+guest;
    }

    public  HelloServiceImpl() throws  RemoteException{
        super();
    }



}
