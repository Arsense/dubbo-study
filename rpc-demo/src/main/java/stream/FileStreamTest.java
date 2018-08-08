package stream;

import java.io.*;

/**
 * @author tangwei
 * @date 2018/8/8 10:33
 */
public class FileStreamTest {


    public static void main(String[] args) throws IOException {
        File sourceFile = new File("E:\\test\\source.txt");
        File targetFile = new File("E:\\test\\target.txt");

        FileInputStream inputStream = new FileInputStream(sourceFile);
        FileOutputStream outputStream = new FileOutputStream(targetFile);

        int length;
            //为什么是int形式
        while ((length = inputStream.read()) != -1) {
            outputStream.write(length);
        }
        inputStream.close();
        outputStream.close();
    }
}
