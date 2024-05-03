package org.otus.social.config;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarantoolConfig  {

    @Value("${tarantool.host}")
    protected String host;
    @Value("${tarantool.port}")
    protected int port;
    @Value("${tarantool.username}")
    protected String username;
    @Value("${tarantool.password}")
    protected String password;

    @Bean
    public TarantoolClient tarantoolClient(){
        TarantoolClient tarantoolClient = TarantoolClientFactory.createClient()
                .withAddress(host, port)
  //              .withCredentials(username, password)
                .build();
        return tarantoolClient;
    }
}