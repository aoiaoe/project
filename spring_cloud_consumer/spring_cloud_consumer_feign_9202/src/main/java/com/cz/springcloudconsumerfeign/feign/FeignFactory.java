package com.cz.springcloudconsumerfeign.feign;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

/**
 * @author alian
 * @date 2020/11/12 下午 4:25
 * @since JDK8
 */
@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignFactory {
//
//    @Autowired
//    private Decoder feignDecoder;
//
//    private Feign.Builder builder;
//
//    @PostConstruct
//    public void init() {
//        builder = Feign.builder().decoder(feignDecoder);
//    }


    @Bean
    public UserClient userClient(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        return Feign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(UserClient.class, "http://user");
    }
}
