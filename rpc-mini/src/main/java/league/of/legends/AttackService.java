package league.of.legends;

import java.lang.reflect.Method;

/**
 * @author tangwei
 * @date 2018/8/4 16:20
 */
public class AttackService {

    //服务接口
    private Class<?> serviceInterface;
    //服务的对象
    private Object serviceBean;
    //端口
    private String port;
    //设置短时间连接超时
    private long timeout;

    private String groupName;

    private String serverIp;

    //服务提交者方法
    private Method serviceMethod;


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Method getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(Method serviceMethod) {
        this.serviceMethod = serviceMethod;
    }


    @Override
    public String toString() {
        return "AttackService{" +
                "serviceInterface=" + serviceInterface +
                ", serviceBean=" + serviceBean +
                ", port='" + port + '\'' +
                ", timeout=" + timeout +
                '}';
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
