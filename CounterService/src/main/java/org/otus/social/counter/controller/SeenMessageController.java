package org.otus.social.counter.controller;


import lombok.AllArgsConstructor;
import org.otus.social.counter.service.SeenMessageService;
import org.otus.social.lib.dto.SeenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/counter")
public class SeenMessageController {

   private  final SeenMessageService seenMessageService;

    @PostMapping("/seen")
    public ResponseEntity<Boolean> seen(@RequestBody final SeenRequest seen)  {
        return ResponseEntity.ok(seenMessageService.seen(seen.getId(), seen.getMessageId()));
    }
    @GetMapping("/get_seen")
    public ResponseEntity<List<Long>> seen()  {
        return ResponseEntity.ok(seenMessageService.getSeen());
    }

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
