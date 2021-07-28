//    package com.cz.spring_boot_mix.circulardependency.constructors;
//
//    import lombok.extern.slf4j.Slf4j;
//    import org.springframework.stereotype.Service;
//
//    /**
//     * 【构造器】注入,Spring不能解决循环依赖
//     * @author alian
//     * @date 2020/12/25 下午 12:22
//     * @since JDK8
//     */
//    @Service
//    @Slf4j
//    public class Service3 {
//
//        private Service4 service4;
//
//        public Service3(Service4 service4){
//            this.service4 = service4;
//            log.info("初始化service3、、、、");
//        }
//
//    }
