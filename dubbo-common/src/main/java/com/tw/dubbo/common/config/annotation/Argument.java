package com.tw.dubbo.common.config.annotation;

import java.lang.annotation.*;

/**
 *
 * @since 2.6.5
 * @author clay
 * @date 2020/2/2 17:01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
@Inherited
public @interface Argument {
    //argument: index -1 represents not set
    int index() default -1;

    //argument type
    String type() default "";

    //callback interface
    boolean callback() default false;
}