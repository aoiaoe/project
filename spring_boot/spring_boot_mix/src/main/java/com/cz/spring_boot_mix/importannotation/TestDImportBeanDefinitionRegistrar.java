package com.cz.spring_boot_mix.importannotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jzm
 * @date 2022/1/13 : 10:15
 */
public class TestDImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        RootBeanDefinition testD = new RootBeanDefinition(TestD.class);
        registry.registerBeanDefinition("testD", testD);
    }
}
