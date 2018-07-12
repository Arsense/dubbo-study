package serializer;

/**
 * @author tangwei
 * @date 2018/7/12 11:04
 */
public interface DataSerializer {

    /**
     * 序列化函数
     * @param Object
     * @param <T>
     * @return
     */
    public <T>byte[] serialize(T Object);

    public <T> T unserialize(byte[] data,Class<T> clazz) throws ClassNotFoundException;

}
