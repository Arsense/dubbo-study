package league.of.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author tangwei
 * @date 2018/8/3 17:17
 */
public class AttackTest {


    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("legends-attack.xml");


        System.out.println("服务启动");
        System.in.read();

    }
}
