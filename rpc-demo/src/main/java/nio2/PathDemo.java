package nio2;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/** nio2是JDK7后的新特性 Path和Files两个不错的工具类
 * @author tangwei
 * @date 2018/8/15 10:27
 */
public class PathDemo {

    public static void main(String[] args) {

        //绝对路径获取Path
        Path path = Paths.get("E:/test.txt");
        System.out.println("path1:" + path);

        path = Paths.get("E:","test.txt");
        System.out.println("path2:" + path);

        //相对路径
        path = Paths.get("/test.txt");
        System.out.println("path3:" + path);

        //通过URI获取
        path = Paths.get(URI.create("file:///test.txt"));
        System.out.println("path4" + path);

        //FileSystem获取Path
        path = FileSystems.getDefault().getPath("/test.txt");
        System.out.println("path5" + path);

        //Path转URI
        URI uirPath = path.toUri();
        System.out.println("URI TO PATH" + uirPath);

        //获取绝对路径
        Path aPath = path.toAbsolutePath();
        System.out.println("complete path:"+aPath);

    }
}
