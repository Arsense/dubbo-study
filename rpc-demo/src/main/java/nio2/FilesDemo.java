package nio2;

import org.apache.commons.lang3.CharSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * @author tangwei
 * @date 2018/8/15 11:13
 */
public class FilesDemo {

    public static void main(String[] args) throws IOException {
        //遍历文件目录

        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/"));

        for (Path path : directoryStream) {
            System.out.println(path);
        }
        System.out.println();
        Path path = Files.createDirectories(Paths.get("C:\\Users\\tangwei9\\Desktop\\JSF"));
        System.out.println(path.getFileName());

        Path file = Files.createFile(Paths.get("C:\\Users\\tangwei9\\Desktop\\JSF\\test.pdf"));
        Charset charset = Charset.forName("UTF-8");
        String hello = "你好啊 这里是nio2测试";

        BufferedWriter  writer = Files.newBufferedWriter(file,charset, StandardOpenOption.APPEND);

        writer.write(hello);
        writer.close();

        //使用缓冲字节流读取文件内容

        BufferedReader reader = null;
        reader = Files.newBufferedReader(file,charset);
        String message = null;
        while ((message = reader.readLine()) != null) {
            System.out.println(message);
        }
        reader.close();


    }
}
