package com.tw.dubbo.rpc.protocol.injvm;

import com.tw.dubbo.rpc.Invoker;
import com.tw.dubbo.rpc.protocol.AbstractExporter;

/**
 * @author clay
 * @date 2020/2/2 12:07
 */
public class InjvmExporter <T> extends AbstractExporter<T> {


    public InjvmExporter(Invoker<T> invoker) {
        super(invoker);
    }
}
