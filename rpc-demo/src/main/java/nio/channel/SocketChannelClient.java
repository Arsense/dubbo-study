package nio.channel;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author tangwei
 * @date 2018/8/10 10:22
 */
public class SocketChannelClient {


    public static void main(String[] args) throws IOException {
        ByteBuffer hello = ByteBuffer.wrap("hello this is a test".getBytes());
        CharBuffer charBuffer;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();
        SocketChannel socketChannel = SocketChannel.open();

        if (socketChannel.isOpen()) {

            //同步的阻塞模式
            socketChannel.configureBlocking(true);
            socketChannel.setOption(StandardSocketOptions.SO_RCVBUF,128 * 1024);
            socketChannel.setOption(StandardSocketOptions.SO_SNDBUF,128 * 1024);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE,true);
            socketChannel.setOption(StandardSocketOptions.SO_LINGER,5);

            socketChannel.connect(new InetSocketAddress("127.0.0.1",8085));
            if (socketChannel.isConnected()) {

                socketChannel.write(hello);
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                while (socketChannel.read(buffer) != -1) {

                    buffer.flip();
                    charBuffer = decoder.decode(buffer);
                    System.out.println(charBuffer.toString());
                    if (buffer.hasRemaining()) {
                        buffer.compact();
                    } else {
                        buffer.clear();
                    }

                }
            }
            socketChannel.close();
        }


    }

}
