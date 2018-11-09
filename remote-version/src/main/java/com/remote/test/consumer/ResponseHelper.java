package com.remote.test.consumer;

import com.google.common.collect.Maps;
import com.remote.test.utils.Response;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author tangwei
 * @date 2018/9/12 18:33
 */
public class ResponseHelper {

    //服务返回结果Map
    private static final Map<String, BlockingQueue<Response>> responseMap = Maps.newConcurrentMap();

    +

    //存储返回结果的阻塞队列
    private BlockingQueue<Response> responseQueue = new ArrayBlockingQueue<Response>(1);

    /**
     * 初始化返回结果容器,requestUniqueKey唯一标识本次调用
     *
     * @param requestUniqueKey
     */
    public static void initResponseData(String requestUniqueKey) {
        responseMap.put(requestUniqueKey, new ArrayBlockingQueue<Response>(1));
    }
    /**
     * 将Netty调用异步返回结果放入阻塞队列
     *
     */
    public static void putResultValue(Response response) {
        ResponseHelper responseHelper = new ResponseHelper();
        responseHelper.responseQueue = responseMap.get(response.getUniqueKey());
        responseMap.put(response.getUniqueKey(), responseHelper.responseQueue);
    }

    public BlockingQueue<Response> getResponseQueue() {
        return responseQueue;
    }

    public void setResponseQueue(BlockingQueue<Response> responseQueue) {
        this.responseQueue = responseQueue;
    }

    /**
     * 从阻塞队列中获取Netty异步返回的结果值
     * @param requestUniqueKey
     * @return
     */
    public static Response getValue(String requestUniqueKey) {
        ResponseHelper responseHelper = new ResponseHelper();
        responseHelper.responseQueue = responseMap.get(requestUniqueKey);
        try {
            return responseHelper.responseQueue.poll(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            responseMap.remove(requestUniqueKey);
        }
    }



}
