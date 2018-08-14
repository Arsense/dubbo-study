package nio.channel;

import java.nio.CharBuffer;

/**
 * @author tangwei
 * @date 2018/8/10 9:21
 */
public class NioBuffer {

    public static void main(String[] args) {
        String content = " hello this is test";
        CharBuffer buffer = CharBuffer.allocate(40);

        for (int i = 0; i  < content.length(); i++) {
            buffer.put(content.charAt(i));
        }
        // 反转Buffer 准备读取
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }

        System.out.println();
        //再次反转 准备读取
        buffer.rewind();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();

        // 清空Buffer 可以再次使用Buffer
        buffer.clear();
        buffer.put("hello i am coming back");
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();

    }
}
