package com.tw.dubbo.rpc.protocol.injvm;

import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.rpc.Protocol;
import com.tw.dubbo.rpc.protocol.AbstractProtocol;

import static com.tw.dubbo.common.constants.CommonConstants.LOCAL_PROTOCOL;

/**
 * @author clay
 * @date 2020/2/2 12:06
 */
public class InjvmProtocol extends AbstractProtocol implements Protocol {


    public static final String NAME = LOCAL_PROTOCOL;


    private static InjvmProtocol INSTANCE;

    public InjvmProtocol() {
        INSTANCE = this;
    }

    /**
     *
     * @return
     */
    public static InjvmProtocol getInjvmProtocol() {
        if (INSTANCE == null) {
            ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(InjvmProtocol.NAME);
        }
        return INSTANCE;
    }
}
