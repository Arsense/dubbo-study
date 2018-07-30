package city.hunter.test;

import city.hunter.begin.Monster;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tangwei
 * @date 2018/7/26 15:24
 */
public class MonsterTest {




    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");

        Monster monster = (Monster) applicationContext.getBean("cutesource");
        System.out.println(monster.getId());
        System.out.println(monster.getAge());
        System.out.println(monster.getName());
    }

}
