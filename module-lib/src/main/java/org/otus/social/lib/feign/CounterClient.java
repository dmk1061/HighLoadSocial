package org.otus.social.lib.feign;


import org.otus.social.lib.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "http://localhost:8002", name = "otus-social-dialog-service", configuration = FeignConfig.class)
public interface CounterClient {


    @GetMapping(value = "/counter/get_seen", consumes = "application/json")
    List<Long> getSeen();

    @PostMapping(value = "/counter/seen_rollback", consumes = "application/json")
    Boolean rollback (@RequestBody List<Long> ids);



}