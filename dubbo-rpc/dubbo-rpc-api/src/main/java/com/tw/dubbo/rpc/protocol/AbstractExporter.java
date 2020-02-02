package com.tw.dubbo.rpc.protocol;

import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author clay
 * @date 2018/11/30 16:20
 */
public class AbstractExporter<T>  implements Exporter<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    private final Invoker<T> invoker;

    private volatile boolean unexported = false;


    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null) {
            throw new IllegalStateException("service invoker == null");
        }
        if (invoker.getInterface() == null) {
            throw new IllegalStateException("service type == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalStateException("service url == null");
        }
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return null;
    }

    @Override
    public void unexport() {

    }
}
