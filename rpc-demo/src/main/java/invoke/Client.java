package invoke;

import framework.UserProxy;
import service.HelloService;

import java.rmi.RemoteException;

/**
 * @author tangwei
 * @date 2018/7/12 0:22
 */
public class Client {

    public static void main(String[] args) throws RemoteException {
        HelloService service = UserProxy.userProxy(HelloService.class,"127.0.0.1",8803);
        String hello = service.sayHello("Clay");
        System.out.println(hello);
    }
}
