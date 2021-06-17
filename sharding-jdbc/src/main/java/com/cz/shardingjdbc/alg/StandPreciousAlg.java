package com.cz.shardingjdbc.alg;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public class StandPreciousAlg implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        log.info("==-=-=-=-=-=-=-=-collection:" + availableTargetNames + ",preciseShardingValue:" + shardingValue);
        //配置的分库分片的sharding-column对应的值,也就是具体时间
        String str = shardingValue.getValue();
        if (str.isEmpty()) {
            throw new UnsupportedOperationException("pre is null");
        }
        try {
            //each为每个库的名字
            for (String each : availableTargetNames) {
                Long res = Long.valueOf(each) % 2;
                return res.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
