package stream;

import java.io.*;

/** 增强了批量读写的能力
 * 比File流增加了缓存的能力
 * @author tangwei
 * @date 2018/8/8 11:13
 */
public class DataStreamTest {


    public static void main(String[] args) throws IOException {
        String filePath = "E:\\test\\source.txt";


        FileOutputStream outputStream = new FileOutputStream(filePath);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(2018);
        dataOutputStream.writeUTF("sadasdasdas /");
        dataOutputStream.writeBoolean(false);

        dataOutputStream.close();
        outputStream.close();

        FileInputStream inputStream = new FileInputStream(filePath);
        DataInputStream dataInputStream = new DataInputStream(inputStream);


        System.out.println(dataInputStream.readInt());
        System.out.println(dataInputStream.readUTF());
        System.out.println(dataInputStream.readBoolean());
        System.out.println(dataInputStream.readChar());

        dataInputStream.close();
        inputStream.close();



    }


}
