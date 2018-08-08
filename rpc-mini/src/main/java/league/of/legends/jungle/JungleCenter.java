package league.of.legends.jungle;

import league.of.legends.AttackService;

import java.util.List;

/**
 * @author tangwei
 * @date 2018/8/4 16:24
 */
public interface JungleCenter {


    /**
     *  消费端将消费者信心注册方法
     * @param attacks
     */
    public void registerAttack(List<AttackService> attacks);

//    /**
//     *  初始化服务提供者信心本地缓存
//     */
//    public void initProviderMap();
//
//
//    /**
//     * 消费端获取服务提供者信息
//     */
//    public void getServiceMetaDataMapConsume();
}
