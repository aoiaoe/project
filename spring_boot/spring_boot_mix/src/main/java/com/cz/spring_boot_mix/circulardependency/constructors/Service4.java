//package com.cz.spring_boot_mix.circulardependency.constructors;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
///**
// * 【构造器】注入,Spring不能解决循环依赖
// * @author alian
// * @date 2020/12/25 下午 12:22
// * @since JDK8
// */
//@Slf4j
//@Service
//public class Service4 {
//
//    private Service3 service3;
//
//    public Service4(Service3 service3){
//        this.service3 = service3;
//        log.info("初始化service4、、、、");
//    }
//
//}
