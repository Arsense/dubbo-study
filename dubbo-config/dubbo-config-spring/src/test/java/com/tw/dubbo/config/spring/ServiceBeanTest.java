package com.tw.dubbo.config.spring;

import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.mock;

/**
 * @author clay
 * @date 2018/11/30 15:32
 */
public class ServiceBeanTest {
    @Test
    public void testGetService() {
        TestService service = mock(TestService.class);
//        ServiceBean serviceBean = new ServiceBean(service);
//        //<dubbo:service exported="false" uniqueServiceName="null" unexported="false" export="false" register="false" callbacks="0" retries="0" />
//        Service beanService = serviceBean.getService();
//        Assert.assertThat(beanService, not(nullValue()));
    }

    abstract class TestService implements Service {

    }
}
