package com.tw.dubbo.config.spring.api;

/**
 * @author tangwei
 * @date 2020/1/23 13:39
 */
public class DemoException extends Exception {

    private static final long serialVersionUID = -8213943026163641747L;

    public DemoException() {
        super();
    }

    public DemoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoException(String message) {
        super(message);
    }

    public DemoException(Throwable cause) {
        super(cause);
    }

}
