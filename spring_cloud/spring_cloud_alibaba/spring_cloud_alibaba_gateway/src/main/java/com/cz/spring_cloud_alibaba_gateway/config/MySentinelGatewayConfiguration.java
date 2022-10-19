package com.cz.spring_cloud_alibaba_gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sun.awt.IconInfo;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * gateway整合sentinel样板代码，来自官方文档
 * https://github.com/alibaba/Sentinel/wiki/网关限流#spring-cloud-gateway
 */
@Slf4j
@Configuration
public class MySentinelGatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public MySentinelGatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                        ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 默认的限流异常处理器
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(JsonCustomizeErrorHandler jsonCustomizeErrorHandler) {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer){
            @Override
            public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
                // 如果是限流异常，则由此类处理
                // 此类内部会使用限流异常处理器进行处理， 可以使用自定义的限流异常处理器
                if(BlockException.isBlockException(ex)) {
                    return super.handle(exchange, ex);
                }
                // 其他异常则由自定义全局异常处理器处理
                return jsonCustomizeErrorHandler.handle(exchange, ex);
            }
        };
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void init() {
        log.info("----------------------------------------------");
//        this.initRouteIdFlowRUleHardCodeRules();
        this.initApiGroupHardCodeRules();

        this.customizeBlockHandler();
        log.info("----------------------------------------------");
    }

    /**
     * 自定义限流异常处理器
     */
    private void customizeBlockHandler() {
        GatewayCallbackManager.setBlockHandler((exchange, t) -> {
            log.error("[网关限流]  触发限流异常处理器--------------");
            JSONObject response = new JSONObject();
            response.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
            response.put("message", "限流啦");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(response ));
        });
    }

    /**
     * 硬编码配置分组路由
     */
    private void initApiGroupHardCodeRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        // 硬编码api分组限流规则
        // sentinel先去找分组，在找分组下的规则
        // 配置分组
        GatewayFlowRule group1 = new GatewayFlowRule();
        group1.setResource("group1")
                .setCount(1)         // 阈值
                .setIntervalSec(10); // 时间窗
        rules.add(group1);

        GatewayFlowRule group2 = new GatewayFlowRule();
        group2.setResource("group2")
                .setCount(3)         // 阈值
                .setIntervalSec(10); // 时间窗
        rules.add(group2);
        GatewayRuleManager.loadRules(rules);

        // 配置分组下的限流规则
        Set<ApiDefinition> definitions = new HashSet<>();
        // 分组名需要和上面的分组名相同，才是属于配置好的分组中的
        ApiDefinition api1 = new ApiDefinition("group1")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // url精确匹配
                    add(new ApiPathPredicateItem().setPattern("/gw/user/config/test"));
                }});
        ApiDefinition api2 = new ApiDefinition("group2")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // url前缀匹配
                    add(new ApiPathPredicateItem().setPattern("/gw/user/user/**")
                    .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 硬编码配置routeId级别限流
     */
    private void initRouteIdFlowRUleHardCodeRules() {
        log.info("[网关限流配置] 路由id级别限流配置ing");
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule rule = new GatewayFlowRule();
        rule.setResource("nacos-config-sentinel-user")
                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
                .setCount(2)         // 阈值
                .setIntervalSec(10) // 时间窗
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rules.add(rule);

        GatewayRuleManager.loadRules(rules);
    }
}