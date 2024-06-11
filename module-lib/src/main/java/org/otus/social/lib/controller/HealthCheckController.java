package org.otus.social.lib.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthCheckController {

    @GetMapping
    public ResponseEntity  <String> health() {
        log.info(System.getenv("CONTAINER_NAME"));
        return ResponseEntity.ok(System.getenv("CONTAINER_NAME"));
    }
}
