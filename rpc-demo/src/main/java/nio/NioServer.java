package nio;

/**
 * @author tangwei
 * @date 2018/8/14 14:37
 */
public class NioServer {

    public static void main(String[] args) {
        int port = 8081;
        NioServerHandler server = new NioServerHandler(port);

        new Thread(server).start();
    }
}
