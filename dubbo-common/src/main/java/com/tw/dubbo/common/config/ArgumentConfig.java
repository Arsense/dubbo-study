package com.tw.dubbo.common.config;

import com.tw.dubbo.common.config.annotation.Argument;
import com.tw.dubbo.common.config.annotation.Parameter;

import java.io.Serializable;

/**
 *
 * The method arguments configuration
 * @author clay
 * @date 2020/2/2 17:00
 */
public class ArgumentConfig  implements Serializable {

    /**
     * The argument index: index -1 represents not set
     */
    private Integer index = -1;

    /**
     * Argument type
     */
    private String type;

    /**
     * Whether the argument is the callback interface
     */
    private Boolean callback;

    public ArgumentConfig() {
    }

    public ArgumentConfig(Argument argument) {
        this.index = argument.index();
        this.type = argument.type();
        this.callback = argument.callback();
    }

    @Parameter(excluded = true)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Parameter(excluded = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCallback(Boolean callback) {
        this.callback = callback;
    }

    public Boolean isCallback() {
        return callback;
    }
}
