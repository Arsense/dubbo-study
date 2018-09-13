package thread;

/**
 * @author tangwei
 * @date 2018/9/13 9:57
 */
public class ThreadTest1 implements Runnable{

    private int count = 10;
    ThreadTest1(){
        System.out.println("Thread start to work");
    }

    @Override
    public void run() {
        int times = 3;
        while (times-- > 0){
            System.out.println("run count:" + times);
            Thread.yield();
        }
    }
}
