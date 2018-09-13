package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tangwei
 * @date 2018/9/13 10:02
 */
public class Start {

    public static void main(String[] args) {
        //普通调用
//        for(int i=0; i < 20; i++) {
//            new Thread(new ThreadTest1()).start();
//        }
//        System.out.println("waiting for end");



        //执行器调用

        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i=0; i < 20; i++) {
            exec.execute(new ThreadTest1());
        }
        exec.shutdown();
        System.out.println("waiting for end");
    }
}
