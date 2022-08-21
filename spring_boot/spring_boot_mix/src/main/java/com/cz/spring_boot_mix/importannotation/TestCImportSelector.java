package com.cz.spring_boot_mix.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jzm
 * @date 2022/1/13 : 10:13
 */
public class TestCImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.cz.spring_boot_mix.importannotation.TestC"};
    }
}
