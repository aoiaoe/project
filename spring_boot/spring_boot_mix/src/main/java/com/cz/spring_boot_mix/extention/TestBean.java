package com.cz.spring_boot_mix.extention;

import lombok.Data;

/**
 * @author jzm
 * @date 2022/1/18 : 16:31
 */

@Data
public class TestBean {

    private String beanDefinitionRegistryPostProcessor = "默认值";

    private String beanFactoryPostProcessor = "默认值";

    private String instantiationAwareBeanPostProcessorBefore = "默认值";

    private String instantiationAwareBeanPostProcessorAfter = "默认值";
}
