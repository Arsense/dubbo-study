package com.tw.dubbo.common.util;

/**
 * @author tangwei
 * @date 2018/11/28 17:30
 */
public class StringUtils {
    /**
     * beanName 处理成 bean-name
     * @param camelName
     * @param split
     * @return
     */
    public static String camelToSplitName(String camelName, String split) {
        if (camelName == null || camelName.length() == 0) {
            return camelName;
        }
        StringBuilder buffer = null;
        // 碰到大写换小写 然后加入分隔符
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buffer == null) {
                    buffer = new StringBuilder();
                    if (i > 0) {
                        buffer.append(camelName.substring(0, i));
                    }
                }
                if (i > 0) {
                    buffer.append(split);
                }
            } else if (buffer != null) {
                buffer.append(ch);
            }
        }
        return buffer == null ? camelName : buffer.toString();

    }
}
