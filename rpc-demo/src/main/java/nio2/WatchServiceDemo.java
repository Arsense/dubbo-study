package nio2;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author tangwei
 * @date 2018/8/15 13:29
 */
public class WatchServiceDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("D://");
        WatchServiceDemo.watch(path);
    }

    public static void watch(Path path) throws IOException, InterruptedException {

        WatchService watchService = FileSystems.getDefault().newWatchService();
        //注册监听
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
        //开启循环监听
        while(true) {
            final WatchKey key = watchService.take();
            //检索并删除此监视键的所有待处理事件，并返回 pollEvents
            for ( WatchEvent<?> watchEvent : key.pollEvents()) {
                final WatchEvent.Kind<?> kind = watchEvent.kind();
                //忽略覆盖事件
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }
                final  Path fileName = (Path) watchEvent.context();
                System.out.println(fileName);
            }

            //重置key
            if (!key.reset()) {
                break;
            }
        }


    }
}
