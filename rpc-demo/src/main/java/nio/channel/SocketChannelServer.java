package nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tangwei
 * @date 2018/8/10 10:22
 */
public class SocketChannelServer {

    //创建线程池
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        if (serverSocketChannel.isOpen()) {
            //设置阻塞模式
            serverSocketChannel.configureBlocking(true);
            //设置网络参数
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF,4*1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1" , 8085));

            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                //提交给线程池处理
                executor.submit(new NioHandler(socketChannel));
            }
        }

    }
}
