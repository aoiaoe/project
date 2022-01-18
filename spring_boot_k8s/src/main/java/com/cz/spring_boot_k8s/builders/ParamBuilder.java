package com.cz.spring_boot_k8s.builders;

import java.util.List;

/**
 * @author jzm
 * @date 2021/12/27 : 1:40 下午
 */
public interface ParamBuilder {

    /**
     * 构建环境变量
     *
     * @param data
     * @param systemObject
     */
    default void buildEnv(String data, Object systemObject) {
    }

    /**
     * 构建运行命令
     *
     * @param list
     * @param systemObject
     */
    default void buildCommand(List<String> list, Object systemObject) {
    }

    /**
     * 构建运行参数
     *
     * @param list
     * @param systemObject
     */
    default void buildArgs(List<String> list, Object systemObject) {
    }

    /**
     * 构建单机模式下资源参数
     *
     * @param reqCpu           cpu请求数
     * @param limitCpu         cpu最大值
     * @param reqMemory        内存请求数
     * @param limitMemory      内存最大值
     * @param gpu              gpu请求值
     * @param gpuSelection
     * @param addK8sProperties
     */
    default void buildResource(String reqCpu, String limitCpu,
                               String reqMemory, String limitMemory,
                               String gpu, String gpuSelection,
                               Boolean addK8sProperties) {
    }

    /**
     * 构建分布式master资源参数
     *
     * @param reqCpu           cpu请求数
     * @param limitCpu         cpu最大值
     * @param reqMemory        内存请求数
     * @param limitMemory      内存最大值
     * @param gpu              gpu请求值
     * @param gpuSelection
     * @param addK8sProperties
     */
    default void buildMasterResources(String reqCpu, String limitCpu,
                                      String reqMemory, String limitMemory,
                                      String gpu, String gpuSelection,
                                      Boolean addK8sProperties) {
    }
    /**
     * 构建分布式worker资源参数
     *
     * @param reqCpu           cpu请求数
     * @param limitCpu         cpu最大值
     * @param reqMemory        内存请求数
     * @param limitMemory      内存最大值
     * @param gpu              gpu请求值
     * @param gpuSelection
     * @param addK8sProperties
     */
    default void buildWorkerResources(String reqCpu, String limitCpu,
                                      String reqMemory, String limitMemory,
                                      String gpu, String gpuSelection,
                                      Boolean addK8sProperties) {
    }
}
