package com.tw.dubbo.rpc.protocol;

import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Invoker;

/**
 * @author tangwei
 * @date 2018/11/30 16:20
 */
public class AbstractExporter<T>  implements Exporter<T> {
    @Override
    public Invoker<T> getInvoker() {
        return null;
    }

    @Override
    public void unexport() {

    }
}
