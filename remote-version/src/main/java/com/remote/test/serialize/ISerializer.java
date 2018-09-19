package com.remote.test.serialize;

/**
 * @author tangwei
 * @date 2018/9/19 16:01
 */
public interface ISerializer {

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public <T> byte[] serialize(T obj);


    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T deserialize(byte[] data, Class<T> clazz);
}
