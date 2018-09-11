import java.io.*;

/**
 * @author tangwei
 * @date 2018/9/11 16:35
 */
public class SpringleakTest {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String object = "hello world";

        //创建一个包含对象进行反序列化信息的 object数据文件
        FileOutputStream outputStream = new FileOutputStream("object");
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(object);
        outputStream.close();
        os.close();


        //从文件中反序列化object
        FileInputStream fis = new FileInputStream("object");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //恢复对象
        String object2 = (String) ois.readObject();
        System.out.println(object2);
        ois.close();



    }
}
