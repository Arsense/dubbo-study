package com.remote.test.serialize;

/**
 * @author tangwei
 * @date 2018/9/19 16:02
 */
public class JsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return null;
    }
}
