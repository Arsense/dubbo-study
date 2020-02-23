package com.tw.dubbo.common.extension;

import java.lang.annotation.*;

/**
 * Provide helpful information for {@link ExtensionLoader} to inject dependency extension instance.
 * @author clay
 * @date 2020/2/9 22:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Adaptive {

    /**
     * 确定要注入的目标扩展。目标扩展名的名称由传递的参数决定
     *在URL中，参数名由该方法给出。
     * @return
     */
    String[] value() default {};


}
