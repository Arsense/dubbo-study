package com.tw.dubbo.common.config;

import com.tw.dubbo.common.context.FrameworkExt;
import com.tw.dubbo.common.context.LifecycleAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author clay
 * @date 2020/1/29 22:56
 * LifecycleAdapter 生命周期适配器
 */
public class ConfigManager extends LifecycleAdapter implements FrameworkExt {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public static final String NAME = "config";

    private ApplicationConfig application;

    public Optional<ApplicationConfig> getApplication() {
//        return ofNullable(getConfig(getTagName(ApplicationConfig.class)));
          return null;
    }


    public ApplicationConfig getApplicationOrElseThrow() {
        return getApplication().orElseThrow(() -> new IllegalStateException("There's no ApplicationConfig specified."));
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }
}
