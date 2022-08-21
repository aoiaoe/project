package com.cz.spring_boot_mix.extention;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.stereotype.Component;

/**
 * org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 * 这个接口在读取项目中的beanDefinition之后执行，提供一个补充的扩展点
 *
 * 场景： 你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean
 * @author jzm
 * @date 2022/1/18 : 16:24
 */
@Component
@Slf4j
public class _2MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        BeanDefinition testBean = new RootBeanDefinition();
//        testBean.setBeanClassName("TestBean.class");
//        testBean.setScope("singleton");
//        registry.registerBeanDefinition("testBean1", testBean);
        log.info("扩展点 2 [BeanDefinitionRegistryPostProcessor] postProcessBeanDefinitionRegistry： 你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean，或者修改beanDefinition");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        TestBean bean = beanFactory.getBean(TestBean.class);
        bean.setBeanDefinitionRegistryPostProcessor("通过BeanDefinitionRegistryPostProcessor设值");
        log.info("扩展点 2 [BeanDefinitionRegistryPostProcessor] postProcessBeanFactory ： 你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean, 或者修改beanDefinition");
    }
}
