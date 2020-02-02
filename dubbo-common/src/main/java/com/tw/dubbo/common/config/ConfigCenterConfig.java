package com.tw.dubbo.common.config;

import com.tw.dubbo.common.constants.CommonConstants;

/**
 * @author tangwei
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
