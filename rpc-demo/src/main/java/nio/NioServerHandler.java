package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author tangwei
 * @date 2018/8/14 14:37
 */
public class NioServerHandler implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;


    public void stop() {
        this.stop = true;
    }

    //初始化多用复用器绑定端口
    NioServerHandler(int port) throws IOException {
        try {
            //设置队列大小
            int backLog = 1024;
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port),backLog);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("server初始化失败");
        }
    }

    @Override
    public void run() {

    }
}
