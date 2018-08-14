package nio;

import java.io.IOException;

/**
 * @author tangwei
 * @date 2018/8/14 14:37
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        NioServerHandler server = new NioServerHandler(port);

        new Thread(server).start();
    }
}
