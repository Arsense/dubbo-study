package com.tw.dubbo.rpc.protocol.dubbo;

import com.tw.dubbo.rpc.Exporter;
import com.tw.dubbo.rpc.Invoker;
import com.tw.dubbo.rpc.protocol.AbstractExporter;

import java.util.Map;

/**
 * @author clay
 * @date 2018/11/30 16:19
 */
public class DubboExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public DubboExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    @Override
    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }

}
