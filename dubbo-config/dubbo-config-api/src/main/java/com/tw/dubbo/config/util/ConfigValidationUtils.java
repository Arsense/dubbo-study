package com.tw.dubbo.config.util;

import com.tw.dubbo.common.extension.ExtensionLoader;
import com.tw.dubbo.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tw.dubbo.common.constants.CommonConstants.REMOVE_VALUE_PREFIX;
import static com.tw.dubbo.common.constants.CommonConstants.DEFAULT_KEY;

/**
 * config校验工具类
 * @author clay
 * @date 2020/1/22 21:53
 */
public class ConfigValidationUtils {

    /**
     * 属性配置最长限制
     */
    private static final int MAX_LENGTH = 200;
    /**
     * 正则匹配
     */
    private static final Pattern PATTERN_NAME = Pattern.compile("[\\-._0-9a-zA-Z]+");

    /**
     * 扩展属性格式校验 ,号分隔 所以运行,
     */
    private static final Pattern PATTERN_MULTI_NAME = Pattern.compile("[,\\-._0-9a-zA-Z]+");

    private static final Logger logger = LoggerFactory.getLogger(ConfigValidationUtils.class);
    public static void checkExtension(Class<?> type, String property, String value) {
        checkName(property, value);
        if (StringUtils.isNotEmpty(value)
                && !ExtensionLoader.getExtensionLoader(type).hasExtension(value)) {
            throw new IllegalStateException("No such extension " + value + " for " + property + "/" + type.getName());
        }
    }

    private static void checkName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_NAME);
    }

    /**
     * 检查是否有扩展名（属性）为value（需要特殊处理）
     * @param type
     * @param property
     * @param value
     */
    public static void checkMultiExtension(Class<?> type, String property, String value) {
        checkMultiName(property, value);
        if (StringUtils.isNotEmpty(value)) {
            //分隔成数组
            String[] values = value.split("\\s*[,]+\\s*");
            for (String v : values) {
                //去除前面 "-"
                if (v.startsWith(REMOVE_VALUE_PREFIX)) {
                    v = v.substring(1);
                }
                //默认 default 不处理
                if (DEFAULT_KEY.equals(v)) {
                    continue;
                }
                if (!ExtensionLoader.getExtensionLoader(type).hasExtension(v)) {
                    throw new IllegalStateException("No such extension " + v + " for " + property + "/" + type.getName());
                }
            }

        }


    }

    public static void checkMultiName(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, PATTERN_MULTI_NAME);
    }



        /**
         * 基础配置校验 1不能为空 2不能超过限定长度
         * @param property
         * @param value
         * @param maxlength
         * @param pattern
         */
    public static void checkProperty(String property, String value, int maxlength, Pattern pattern) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        if (value.length() > maxlength) {
            throw new IllegalStateException("Invalid " + property + "=\"" + value + "\" is longer than " + maxlength);
        }
        //正则匹配校验非法字符
        if (pattern != null) {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid " + property + "=\"" + value + "\" contains illegal " +
                        "character, only digit, letter, '-', '_' or '.' is legal.");
            }
        }
    }


    public static void checkLength(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, null);
    }


    public static void checkPathLength(String property, String value) {
        checkProperty(property, value, MAX_LENGTH, null);

    }
}
