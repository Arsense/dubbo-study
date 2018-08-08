package stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @author tangwei
 * @date 2018/8/8 11:21
 */
public class PrintStreamTest {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("E:\\test\\source.txt");
        PrintStream printStream = new PrintStream(file);
        printStream.println("你好，是多少但是但是");
        printStream.close();
    }
}
