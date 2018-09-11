package safety;

import java.io.*;

/**
 * @author tangwei
 * @date 2018/9/11 17:28
 */
public class SpringSerilizeTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //定义myObj对象
        MyObject myObj = new MyObject();
        myObj.name = "hi";
        //创建一个包含对象进行反序列化信息的”object”数据文件
        FileOutputStream fos = new FileOutputStream("object");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        //writeObject()方法将myObj对象写入object文件
        os.writeObject(myObj);
        os.close();
        //从文件中反序列化obj对象
        FileInputStream fis = new FileInputStream("object");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //恢复对象
        MyObject objectFromDisk = (MyObject)ois.readObject();
        System.out.println(objectFromDisk.name);
        ois.close();
    }

    static class MyObject implements Serializable {
        public String name;
        //重写readObject()方法
        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
            //执行默认的readObject()方法
            in.defaultReadObject();
            //执行打开计算器程序命令
            String cmd1= "calc.exe";
            Runtime.getRuntime().exec(cmd1);
        }
    }

}
