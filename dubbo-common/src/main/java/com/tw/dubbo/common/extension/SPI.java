package com.tw.dubbo.common.extension;

import java.lang.annotation.*;

/**

 *扩展接口标记
 *<p/>
 *扩展配置文件的更改<br/>
 *以<code>协议为例，它的配置文件“META-INF/dubbo/com.xxx.Protocol”从：<br/>

 *<pre>
 *com.foo.XxxProtocol网站
 *com.foo.yyy协议
 *</pre>
 *<p>
 *到键值对<br/>
 *<pre>
 *xxx=com.foo.XxxProtocol网站
 *yyy=com.foo.yyy协议
 *</pre>
 *<br/>
 *更改的原因是：
 *<p>
 *如果在扩展实现中有被静态字段或方法引用的第三方库，则其类将
 *如果第三方库不存在，则无法初始化。在这种情况下，dubbo无法找出分机的id
 *因此，如果使用前一种格式，则无法将异常信息与扩展名映射。
 *<p/>
 *例如：
 *<p>
 *未能加载扩展（“mina”）。当用户配置使用mina时，dubbo会抱怨无法加载扩展名，
 *而不是报告哪个提取扩展实现失败和提取原因。
 *</p>
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * default extension name
     */
    String value() default "";
}
