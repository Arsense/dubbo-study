package com.tw.dubbo.rpc;

import com.tw.dubbo.remoting.exception.RemotingException;

/**
 * @author tangwei
 * @date 2018/11/30 14:59
 */
public class RpcException extends RuntimeException{
    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String s, RemotingException e) {
    }
}
