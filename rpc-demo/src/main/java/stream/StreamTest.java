package stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author tangwei
 * @date 2018/8/8 9:55
 */
public class StreamTest {


    public static void main(String[] args) throws IOException {

        String content = " test Java 您好啊 是多少";
        byte[] inputBytes = content.getBytes(Charset.forName("utf-8"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int readBytes;

        byte[] outBuffer = new byte[1024];
        while ((readBytes = inputStream.read(outBuffer)) != -1) {
            outputStream.write(outBuffer,0,readBytes);
        }

        System.out.println(outputStream.toString());


    }

}
