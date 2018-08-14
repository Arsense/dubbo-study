package nio;

/**
 * @author tangwei
 * @date 2018/8/14 14:39
 */
public class NioClient {

    public static void main(String[] args) {

        int port = 8081;
        new Thread(new NioClientHandler("127.0.0.1",port)).start();
    }
}
