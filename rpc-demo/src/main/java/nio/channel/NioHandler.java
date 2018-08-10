package nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author tangwei
 * @date 2018/8/10 10:33
 */
public class NioHandler implements Runnable {


    private SocketChannel socketChannel;

    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    public NioHandler (SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                if (buffer.hasRemaining()) {
                    buffer.compact();
                } else {
                    buffer.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                throw new Exception("IO  异常");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
}
