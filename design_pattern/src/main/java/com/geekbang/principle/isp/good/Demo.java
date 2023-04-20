package com.geekbang.principle.isp.good;

import com.geekbang.principle.isp.good.service.impl.MysqlConfig;
import com.geekbang.principle.isp.good.service.impl.RedisConfig;

/**
 *
 * @author jzm
 * @date 2023/4/19 : 10:34
 */
public class Demo {

    public static void main(String[] args) {
        MysqlConfig mysqlConfig = new MysqlConfig();
        RedisConfig redisConfig = new RedisConfig();

        ScheduledConfigUpdater scheduledConfigUpdater = new ScheduledConfigUpdater(redisConfig);
        scheduledConfigUpdater.update();

        SimpleHttpServer simpleHttpServer = new SimpleHttpServer();
        simpleHttpServer.addViewer("/configs", mysqlConfig);
        simpleHttpServer.addViewer("/configs", redisConfig);
        simpleHttpServer.run();

    }
}
