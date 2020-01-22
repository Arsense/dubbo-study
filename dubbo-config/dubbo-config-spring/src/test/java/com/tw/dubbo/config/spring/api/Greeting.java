package com.tw.dubbo.config.spring.api;

import com.tw.dubbo.config.extension.SPI;

/**
 * @author clay
 * @date 2020/1/22 22:18
 */
@SPI
public interface Greeting {
    String hello();
}
