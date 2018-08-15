package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

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
                doWrite();

            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it =  selectionKeys.iterator();
                    SelectionKey key;
                    while (it.hasNext()) {
                        key = it.next();
                        it.remove();
                        handlerInput(key);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void doWrite() throws IOException {
        byte[] message = "Hello this my test".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(message.length);
        writeBuffer.put(message);
        writeBuffer.flip();

        socketChannel.write(writeBuffer);
        if ( !writeBuffer.hasRemaining()) {
            System.out.println("send echo content to server ok");
        }
    }
    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新请求
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector,SelectionKey.OP_READ);
                } else {
                    //连接失败  退出
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                int readBytes = socketChannel.read(byteBuffer);

                //对字节进行编码
                if (readBytes >= 0) {
                    byteBuffer.flip();
                    System.out.println(byteBuffer.toString());
                    this.stop = true;

                } else {
                    key.cancel();
                    socketChannel.close();
                    socketChannel.write(byteBuffer);
                }


            }
        }
    }



    public void stop(){
        this.stop = true;
    }

}
