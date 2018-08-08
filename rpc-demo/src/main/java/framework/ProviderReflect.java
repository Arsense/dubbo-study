package framework;

import org.apache.commons.lang3.reflect.MethodUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tangwei
 * @date 2018/7/12 0:21
 */
public class ProviderReflect {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 发布服务
     * @param service
     * @param port
     */
    public  static  void provider(final Object service, final int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            final Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        //先得到方法名称
                        String methodName = input.readUTF();
                        //方法的参数
                        Object[] args = (Object[]) input.readObject();
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        //方法引用
                        try {
                            Object result = MethodUtils.invokeExactMethod(service,methodName,args);
                            output.writeObject(result);
                        } catch (NoSuchMethodException e) {
                            output.writeObject(e);
                        } catch (IllegalAccessException e) {
                            output.writeObject(e);
                        } catch (InvocationTargetException e) {
                            output.writeObject(e);
                        }finally {
                            output.close();
                        }

                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }

    }
}
