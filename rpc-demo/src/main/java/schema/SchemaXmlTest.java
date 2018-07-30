package schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tangwei
 * @date 2018/7/30 10:57
 */
public class SchemaXmlTest {


    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
        People people = (People) ctx.getBean("cutesource");

        System.out.println(people.getId());
        System.out.println(people.getName());
        System.out.println(people.getAge());

    }
}
