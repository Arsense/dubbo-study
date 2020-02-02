package com.tw.dubbo.common.constants;

/**
 *
 * why interface 有什么好处呢
 * @author clay
 * @date 2020/1/22 23:05
 */
public interface CommonConstants {
    String DUBBO = "dubbo";

    String SIDE_KEY = "side";

    String PROVIDER_SIDE = "provider";

    String DUBBO_VERSION_KEY = "dubbo";
    String PID_KEY = "pid";


    String LOCAL_PROTOCOL = "injvm";
    String LOCALHOST_VALUE = "127.0.0.1";

    String DUBBO_PROTOCOL = "dubbo";

    String RELEASE_KEY = "release";

    String REMOVE_VALUE_PREFIX = "-";

    String DEFAULT_KEY = "default";

    String VERSION_KEY = "version";


    String GROUP_KEY = "group";

    String CLIENT_KEY = "client";
    String HOST_KEY = "host";

    String TOKEN_KEY = "token";

    String PATH_KEY = "path";

    String RETURN_PREFIX = "return ";
    String THROW_PREFIX = "throw";
    String MOCK_KEY = "mock";
    String FAIL_PREFIX = "fail:";
    String FORCE_PREFIX = "force:";

    String PROTOCOL_KEY = "protocol";
    String CLUSTER_KEY = "cluster";
    String USERNAME_KEY = "username";
    String PASSWORD_KEY = "password";
    String FILE_KEY = "file";
    String SERVER_KEY = "server";


    String SHUTDOWN_WAIT_KEY = "dubbo.service.shutdown.wait";

    @Deprecated
    String SHUTDOWN_WAIT_SECONDS_KEY = "dubbo.service.shutdown.wait.seconds";

    // FIXME: is this still useful?
    String SHUTDOWN_TIMEOUT_KEY = "shutdown.timeout";

}
