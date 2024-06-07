package org.otus.social.lib.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.otus.social.lib.config.FeignConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "http://localhost:8001", name = "otus-social-dialog-service", configuration = FeignConfig.class)
public interface DialogClient {


    @PostMapping(value = "/dialog/update_seen", consumes = "application/json")
    Boolean updateSeen(@RequestBody List<Long> ids);

}