package com.cz.job.spring_boot_job.executor;

import com.xxl.job.core.anno.JobTrigger;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoExecutor {

    @XxlJob(value = "simpleDemo")
    private ReturnT<String> execute(String arg) {
        log.info("simpleDemo execute!!!!");
        return new ReturnT<>("执行成功");
    }

    @JobTrigger(jobHandler = "executeByJobTrigger", jobDesc = "测试执行JobTrigger", author = "cz", scheduleConf = "0/10 * * * * ?")
    @XxlJob(value = "executeByJobTrigger")
    private ReturnT<String> executeByJobTrigger(String arg) {
        log.info("使用@JobTrigger注册到admin的任务 execute!!!!");
        return new ReturnT<>("执行成功");
    }

    /**
     * 分片任务
     * 其实就是xxl job在进行任务下发的时候，会将以下两个参数，跟任务执行参数一起下发给任务处理器<br/>
     *  <pre>
     *      1、shardTotal: 总的JobHandler数量<br/>
     *      2、shardIndex: 当前JobHandler的处理的第几个分片数据<br/>
     *  </pre>
     *  <pre> xxl 下发任务参数
     *      {
     * 	       "jobId": 3,
     * 	       "executorHandler": "jzmDemoShardJob",
     * 	       "executorParams": "",
     * 	       "executorBlockStrategy": "SERIAL_EXECUTION",
     * 	       "executorTimeout": 0,
     * 	       "logId": 1232,
     * 	       "logDateTime": 1723953888200,
     * 	       "glueType": "BEAN",
     * 	       "glueSource": "",
     * 	       "glueUpdatetime": 1723913907000,
     * 	       "broadcastIndex": 0, # 当前任务索引
     * 	       "broadcastTotal": 1  # 分片任务总节点数
     *      }
     *  </pre>
     * 任务处理器获取到此参数之后，根据此参数，去获取大任务的分片任务进行处理，分治<br/>
     * 比如说: 现在要从数据库处理一批300个状态为待支付的订单, 如果使用其他路由策略，总是只有一个节点进行处理，性能较差<br/>
     *  那么设置成分片广播任务，三个节点，那么三个节点可以处理不同的任务<br/>
     *  第一个节点根据 shardIndex/shardTotal, --计算出--> 处理前100个<br/>
     *  ...以此类推<br/>
     *  达到分治效果<br/>
     *      但是其中获取数据的细节得业务自己处理<br/>
     *          比如如果新增了较多待支付订单，那么可能导致每个分片节点查到的总数不一致，导致某些数据未被处理或者重复处理
     *
     * @param arg
     */
    @XxlJob("jzmDemoShardJob")
    public void executeShardJob(String arg) {
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.info("jzmDemoShardJob executeShardJob!!!!, shardIndex:{}, shardTotal:{}", shardIndex, shardTotal);
        XxlJobHelper.handleSuccess("执行完成");
    }
}
