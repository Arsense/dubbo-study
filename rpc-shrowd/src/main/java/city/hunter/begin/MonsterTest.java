package city.hunter.begin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tangwei
 * @date 2018/7/26 15:24
 */
public class MonsterTest {




    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

        People p = (People) applicationContext.getBean("cutesource");
        System.out.println(p.getId());
        System.out.println(p.getAge());
        System.out.println(p.getName());
    }

}
