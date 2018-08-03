package city.hunter.weapon;




import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import java.lang.reflect.Method;



/** 这个config 就是把外部的接口类转换成ServerConfig
 * @author tangwei
 * @date 2018/7/31 16:17
 */
public class ServerBean implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware {



    private transient ApplicationContext applicationContext;
    //transient防止不被序列化 和volatile多线程安全
    private transient volatile boolean exported;

    private transient volatile boolean unexported = false;

//    protected final static Logger logger = LoggerFactory.getLogger(ServerBean.class);

    /** //这里我们发布服务 就是相当于公开自己
     * ContextRefreshedEvent 是初始化完成事件 意味着applicationEvent 加载完毕
     * ApplicationListener 接口
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        //目前根本没启动
//        logger.info("onApplicationEvent is start,监听开始");
        //这里可以加控制开始与结束的钩子
        exportService();
    }


    /**
     * 方法 加锁
     */
    public  synchronized void exportService(){
        if (unexported) {
            throw new IllegalStateException("Already unexported!");
        }
        if (exported) {
            return;
        }
        exported = true;
        String interfaceName = null;
        try {
            Class<?> interfaceClass = Class.forName(interfaceName,true,Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**  这个在onApplication之前加载 这里添加lister
     *  手动获取Bean 因为这是在Spring刚运行完 这里new 不了对象吧
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        try {
            // backward compatibility to spring 2.0.1
            Method method = applicationContext.getClass()
                    .getMethod("addApplicationListener", ApplicationListener.class);
            method.invoke(applicationContext, this);

        } catch (Throwable t) {

            if (applicationContext instanceof AbstractApplicationContext) {
                try {
                    Method method = AbstractApplicationContext.class.getDeclaredMethod("addListener", ApplicationListener.class); // backward compatibility to spring 2.0.1
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(applicationContext, this);
                } catch (Throwable t2) {
                }
            }
        }
    }
}
