package invoke;

import framework.ProviderReflect;
import service.HelloService;
import service.HelloServiceImpl;
import java.io.IOException;


/**
 * @author tangwei
 * @date 2018/7/12 0:22
 */
public class Server {

    public static void main(String[] args) throws IOException {
        HelloService serivce= new HelloServiceImpl();

        ProviderReflect.provider(serivce,8803);

    }
}
