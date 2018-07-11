package org.service;

/**
 * @author tangwei
 * @date 2018/7/11 9:12
 */
public class SimpleServiceImpl implements SimpleService {


    public String hello(String msg) {
        return "hello "+msg;
    }
}
