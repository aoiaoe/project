package com.cz.spring_boot_k8s.builders;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzm
 * @date 2021/12/27 : 1:48 下午
 */
@Accessors(fluent = true)
@Data
public class ParamAbstractBuilder implements ParamBuilder{
    static Map<String, String> SYSTEM_MAPPING = new HashMap<>();

    static {
        SYSTEM_MAPPING.put("{{_PRESET_MODEL_URI}}", "{#system.modelUri}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
        SYSTEM_MAPPING.put("{{}}", "{#system.}");
    }
}