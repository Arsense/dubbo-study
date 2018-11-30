package com.tw.dubbo.rpc.protocol.dubbo;

import com.tw.dubbo.rpc.Protocol;

/**
 * @author tangwei
 * @date 2018/11/30 15:08
 */
public class DubboProtocol  implements Protocol {
    @Override
    public int getDefaultPort() {
        return 0;
    }
}
