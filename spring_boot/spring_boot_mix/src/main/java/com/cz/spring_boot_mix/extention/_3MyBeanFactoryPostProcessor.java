package com.cz.spring_boot_mix.extention;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class _3MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        TestBean testBean = beanFactory.getBean("testBean", TestBean.class);
        testBean.setBeanFactoryPostProcessor("通过BeanFactoryPostProcessor设值");
        log.info("扩展点 3 ：[BeanFactoryPostProcessor]  场景 ： 用户可以通过实现这个扩展接口来自行处理一些东西，比如修改已经注册的beanDefinition的元信息。");
    }
}
