package league.of.legends.jungle;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * @author tangwei
 * @date 2018/8/4 19:46
 */
public class Peanut implements JungleCenter{


    private static Peanut jungleRegisterCenter = new Peanut();

    private static volatile ZkClient zookeeperClient;

    private static String zookeeperServer;
    private final  static int SESSION_TIMEOUT = 1000;
    private final  static int CONNECT_TIMEOUT = 1000;
    private ZkSerializer zkSerializer;

    public static Peanut singleton() {
        return jungleRegisterCenter;
    }

    /**
     *  连接zookeeper 注册服务
     */
    public  synchronized void registerAttack() {

        if(zookeeperClient == null) {
            //通过一个配置类从配置文件读取的
            zookeeperClient = new ZkClient(zookeeperServer,SESSION_TIMEOUT,CONNECT_TIMEOUT,zkSerializer);
        }
    }
}
