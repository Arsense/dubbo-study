package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it =  selectionKeys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    handlerKey(key);
                }
                //多路复用器关闭后 注册在其上的channel和Pipe等资源都会被关闭
                if (selector != null) {
                    selector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handlerKey(SelectionKey key) throws IOException {

        if (key.isValid()) {
            //处理新请求
            if (key.isAcceptable()) {
                 ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                int readBytes = socketChannel.read(byteBuffer);
                //对字节进行编码
                if (readBytes >= 0) {
                    byteBuffer.flip();

                } else {
                    key.cancel();
                    socketChannel.close();
                    socketChannel.write(byteBuffer);
                }


            }
        }

    }
}
