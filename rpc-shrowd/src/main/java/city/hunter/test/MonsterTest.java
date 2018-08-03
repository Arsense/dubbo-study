package city.hunter.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author tangwei
 * @date 2018/7/26 15:24
 */
public class MonsterTest {




    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        context.start();

        //等待读取输入 这样会一直启动着
        System.in.read();

    }

}
