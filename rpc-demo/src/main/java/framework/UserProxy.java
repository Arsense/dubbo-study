package framework;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author tangwei
 * @date 2018/7/12 0:21
 */
public class UserProxy {
    /*
    服务使用代理处理
     */

    /**
     *  通过 invoke函数来完成远程的调用
     * @param interfaceClass 需要代理的类
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T userProxy(final Class<T> interfaceClass, final String host, final int port){

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host,port);
                try{
                    //这里把Socket收到的值 转换成输出流
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    //使用utf-8编码
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(args);
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try{
                            Object result = input.readObject();
                            if(result instanceof  Throwable){
                                throw (Throwable) result;
                            }
                            return result;
                        }finally {
                            input.close();
                        }
                    }finally {
                        output.close();
                    }


                }finally {
                    socket.close();
                }

            }
        });
    }


}
