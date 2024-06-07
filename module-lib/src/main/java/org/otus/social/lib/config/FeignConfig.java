package org.otus.social.lib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.lib.feign.CounterClient;
import org.otus.social.lib.feign.DialogClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class FeignConfig {

    @Value("${base.dialog-port}")
    private String dialogPort;
    @Value("${base.dialog-host}")
    private String dialogHost;
    @Value("${base.counter-port}")
    private String counterPort;
    @Value("${base.counter-host}")
    private String counterHost;


    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private  GsonEncoder gsonEncoder = new GsonEncoder(gson);
    private GsonDecoder gsonDecoder = new GsonDecoder(gson);
    private SpringMvcContract springMvcContract= new SpringMvcContract();
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Bean
    public OkHttpClient okHttpClient() {
        return okHttpClient;
    }
    @Bean
    public Contract feignContract() {
        return springMvcContract;
    }
    @Bean
    public DialogClient DialogClient() {
        return Feign.builder()
                .client(okHttpClient)
                .contract(feignContract())
                .encoder(gsonEncoder)
                .decoder(gsonDecoder)
                .target(DialogClient.class, "http://" + dialogHost + ":" + dialogPort);
    }
    @Bean
    public CounterClient CounterClient() {
        return Feign.builder()
                .client(okHttpClient)
                .contract(feignContract())
                .encoder(gsonEncoder)
                .decoder(gsonDecoder)
                .target(CounterClient.class, "http://" + counterHost + ":" + counterPort);
    }



    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                log.debug("Feign Request: {} {}", template.method(), template.url());
                log.debug("Headers: {}", template.headers());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    Map<String,String> map = (HashMap)authentication.getDetails();
                    template.header("Authorization", map.get("Bearer"));
                }
            }
        };
    }


}