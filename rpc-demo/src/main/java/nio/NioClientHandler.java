package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author tangwei
 * @date 2018/8/14 14:40
 */
public class NioClientHandler implements Runnable {

    private int port;

    private String host;

    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;


    NioClientHandler (String host , int port) throws IOException {
        this.port = port;
        this.host = host;
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);

    }

    @Override
    public void run() {

        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        try {
            if (socketChannel.connect(new InetSocketAddress(host,port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);

            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }

            byte[] message = "Hello this my test".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(message.length);
            writeBuffer.put(message);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            if ( !writeBuffer.hasRemaining()) {
                System.out.println("send echo content to server ok");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stop(){
        this.stop = true;
    }

}
