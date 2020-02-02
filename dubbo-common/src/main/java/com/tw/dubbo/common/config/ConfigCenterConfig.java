package com.tw.dubbo.common.config;

import com.tw.dubbo.common.constants.CommonConstants;
import com.tw.dubbo.common.utils.StringUtils;
import com.tw.dubbo.common.utils.URL;
import com.tw.dubbo.common.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

import static com.tw.dubbo.common.constants.CommonConstants.*;

/**
 * @author clay
 * @date 2020/2/2 16:24
 */
public class ConfigCenterConfig  extends AbstractConfig {

    /**
     * The config id
     */
    protected String id;

    /** the namespace of the config center, generally it's used for multi-tenant,
but it's real meaning depends on the actual Config Center you use.
*/
    private String namespace = CommonConstants.DUBBO;
    /**
     * The group of the config center, generally it's used to identify an isolated space for a batch of config items,
     but it's real meaning depends on the actual Config Center you use.
     */
    private String group = CommonConstants.DUBBO;
    private Boolean check = true;

    public URL toUrl() {
        Map<String, String> map = new HashMap<>();
        appendParameters(map, this);
        if (StringUtils.isEmpty(address)) {
            address = ANYHOST_VALUE;
        }
        map.put(PATH_KEY, ConfigCenterConfig.class.getSimpleName());
        // use 'zookeeper' as the default configcenter.
        if (StringUtils.isEmpty(map.get(PROTOCOL_KEY))) {
            map.put(PROTOCOL_KEY, ZOOKEEPER_PROTOCOL);
        }

        return UrlUtils.parseURL(address, map);
    }


    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }


}
