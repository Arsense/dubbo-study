package com.tw.dubbo.common.config;

import java.lang.annotation.*;

/**
 * 入参配置注解
 * @author clay
 * @date 2020/1/19 17:02
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Parameter {
    /**
     * map的前缀key
     * @return
     */
    String key() default "";

    /**
     * 必须
     * @return
     */
    boolean required() default false;

    /**
     * 不作处理
     * @return
     */
    boolean excluded() default false;

    /**
     * escaped URL加密
     * @return
     */
    boolean escaped() default false;

    boolean attribute() default false;

    /**
     * 将map的 key直接作为前缀添加
     * 比如 值是1 配置的key name= > name.1的格式
     * @return
     */
    boolean append() default false;

    /**
     * if {@link #key()} is specified, it will be used as the key for the annotated property when generating url.
     * by default, this key will also be used to retrieve the config value:
     * <pre>
     * {@code
     *  class ExampleConfig {
     *      // Dubbo will try to get "dubbo.example.alias_for_item=xxx" from .properties, if you want to use the original property
     *      // "dubbo.example.item=xxx", you need to set useKeyAsProperty=false.
     *      @Parameter(key = "alias_for_item")
     *      public getItem();
     *  }
     * }
     *
     * </pre>
     */
    boolean useKeyAsProperty() default true;
}
