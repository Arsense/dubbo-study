package com.tw.dubbo.common.constants;

/**
 *
 * why interface 有什么好处呢
 * @author clay
 * @date 2020/1/22 23:05
 */
public interface CommonConstants {

    String REMOVE_VALUE_PREFIX = "-";

    String DEFAULT_KEY = "default";


    String SHUTDOWN_WAIT_KEY = "dubbo.service.shutdown.wait";

    @Deprecated
    String SHUTDOWN_WAIT_SECONDS_KEY = "dubbo.service.shutdown.wait.seconds";

    // FIXME: is this still useful?
    String SHUTDOWN_TIMEOUT_KEY = "shutdown.timeout";

}
