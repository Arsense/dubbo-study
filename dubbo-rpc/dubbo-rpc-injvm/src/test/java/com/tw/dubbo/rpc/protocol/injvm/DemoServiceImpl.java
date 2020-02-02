package com.tw.dubbo.rpc.protocol.injvm;

import com.tw.dubbo.rpc.RpcContext;

/**
 * @author clay
 * @date 2020/2/2 17:04
 */
public class DemoServiceImpl  implements DemoService {
    public DemoServiceImpl() {
        super();
    }

    public void sayHello(String name) {
        System.out.println("hello " + name);
    }

    public String echo(String text) {
        return text;
    }

    public long timestamp() {
        return System.currentTimeMillis();
    }

    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    public int getSize(String[] strs) {
        if (strs == null)
            return -1;
        return strs.length;
    }

    public int getSize(Object[] os) {
        if (os == null)
            return -1;
        return os.length;
    }

    public Object invoke(String service, String method) throws Exception {
        System.out.println("RpcContext.getContext().getRemoteHost()=" + RpcContext.getContext());
        return service + ":" + method;
    }

    public Type enumlength(Type... types) {
        if (types.length == 0)
            return Type.Lower;
        return types[0];
    }

    public int stringLength(String str) {
        return str.length();
    }


    @Override
    public String getRemoteApplicationName() {
//        return RpcContext.getContext().getRemoteApplicationName();
        return null;
    }

}
