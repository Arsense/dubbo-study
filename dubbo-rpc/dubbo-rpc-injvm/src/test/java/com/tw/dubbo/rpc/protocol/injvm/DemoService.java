package com.tw.dubbo.rpc.protocol.injvm;

/**
 * @author clay
 * @date 2020/2/2 17:04
 */
public interface DemoService {
    void sayHello(String name);

    String echo(String text);

    long timestamp();

    String getThreadName();

    int getSize(String[] strs);

    int getSize(Object[] os);

    Object invoke(String service, String method) throws Exception;

    int stringLength(String str);

    Type enumlength(Type... types);


    String getRemoteApplicationName();


}
