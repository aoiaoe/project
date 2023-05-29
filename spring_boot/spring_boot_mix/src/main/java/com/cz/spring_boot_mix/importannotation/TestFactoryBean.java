package com.cz.spring_boot_mix.importannotation;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过FactoryBean可以实例化bean
 * @author jzm
 * @date 2023/5/5 : 11:12
 */
@Configuration
public class TestFactoryBean implements FactoryBean<TestE> {
    @Override
    public TestE getObject() throws Exception {
        return new TestE();
    }

    @Override
    public Class<?> getObjectType() {
        return TestE.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
