package serializer;

import java.io.*;

/**
 * @author tangwei
 * @date 2018/7/12 13:35
 */
public class SerializerImpl implements  DataSerializer {
    public <T> byte[] serialize(T object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream output= new ObjectOutputStream(byteArrayOutputStream);
            output.writeObject(object);
            output.close();

        }catch (Exception e){
            throw new RuntimeException(e);
        }


        return byteArrayOutputStream.toByteArray();
    }

    public <T> T unserialize(byte[] data, Class<T> clazz) throws ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

        try {
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) inputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
