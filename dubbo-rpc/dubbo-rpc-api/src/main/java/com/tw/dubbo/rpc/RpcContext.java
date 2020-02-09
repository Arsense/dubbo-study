package com.tw.dubbo.rpc;

/**
 * @author clay
 * @date 2020/2/2 17:10
 */
public class RpcContext {
//    /**
//     * use internal thread local to improve performance
//     */
//    // FIXME REQUEST_CONTEXT
//    private static final InternalThreadLocal<RpcContext> LOCAL = new InternalThreadLocal<RpcContext>() {
//        @Override
//        protected RpcContext initialValue() {
//            return new RpcContext();
//        }
//    };

    public static RpcContext getContext() {
        return null;
    }


}
