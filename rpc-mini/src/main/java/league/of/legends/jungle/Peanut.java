package league.of.legends.jungle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import league.of.legends.AttackService;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/8/4 19:46
 */
public class Peanut implements JungleCenter{


    //服务提供者列表,Key:服务提供者接口  value:服务提供者服务方法列表

    private static final Map<String, List<AttackService>> attackMap = Maps.newConcurrentMap();

    private static Peanut jungleRegisterCenter = new Peanut();

    private static volatile ZkClient zookeeperClient;

    private static String zookeeperServer = "localhost:2181";
    private final  static int SESSION_TIMEOUT = 1000;
    private final  static int CONNECT_TIMEOUT = 1000;
    private ZkSerializer zkSerializer;

    private static  String ROOT_PATH = "/config_jungle";

    public static Peanut singleton() {
        return jungleRegisterCenter;
    }

    /**
     *  连接zookeeper 注册服务
     */
    public  synchronized void registerAttack(List<AttackService> attacks) {

        if (attacks == null || attacks.size() == 0) {
            return;
        }
        //分类添加到 map 中
        for (AttackService attacker: attacks) {
            String interfaceKey = attacker.getServiceInterface().getName();

            List<AttackService> attackers = attackMap.get(interfaceKey);
            if (attackers == null) {
                attackers = Lists.newArrayList();
            }
            attackers.add(attacker);
            attackMap.put(interfaceKey, attackers);

        }


        if (zookeeperClient == null) {
            //通过一个配置类从配置文件读取的
            zookeeperClient = new ZkClient(zookeeperServer,SESSION_TIMEOUT,CONNECT_TIMEOUT,new SerializableSerializer());
        }
        //创建 ZK命名空间/当前部署应用APP命名空间/
        String APP_KET = "clay";
        String ZOOKEEPER_PATH = ROOT_PATH + "/" + APP_KET;
        if (!zookeeperClient.exists(ZOOKEEPER_PATH)) {
            //不存在则创建节点
            zookeeperClient.createPersistent(ZOOKEEPER_PATH,true);
        }


        for (Map.Entry<String, List<AttackService>> entry : attackMap.entrySet()) {

        }
    }
}
