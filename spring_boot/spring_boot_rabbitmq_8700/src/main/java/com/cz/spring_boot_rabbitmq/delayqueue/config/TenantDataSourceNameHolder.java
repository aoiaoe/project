package com.cz.spring_boot_rabbitmq.delayqueue.config;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TenantDataSourceNameHolder {

    private static final ThreadLocal<String> TENANT_DATASOURCE_NAME = new TransmittableThreadLocal<>();

    public static String get(){
        return TENANT_DATASOURCE_NAME.get();
    }

    /**
     * MCD是日志追踪框架, 将数据源名称写入到MDC中, 打印日志时,会从中取值,进行日志重写
     * 配合LogstashAutoConfiguration中的配置MdcJsonProvider实现
     * @param dataSourceName
     */
    public static void set(String dataSourceName){
        TENANT_DATASOURCE_NAME.set(dataSourceName);
//        MDC.put(TokenProvider.DATA_SOURCE_NAME, dataSourceName);
    }

    public static void remove(){
        TENANT_DATASOURCE_NAME.remove();
//        MDC.clear();
    }
}
