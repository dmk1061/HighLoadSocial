package org.otus.social.dialog.controller;

import lombok.AllArgsConstructor;
import org.otus.social.dialog.service.DialogService;
import org.otus.social.lib.dto.AuthenticationWrap;
import org.otus.social.lib.dto.DialogMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dialog")
public class DialogController {

    private final DialogService dialogService;

    @PostMapping("/sent")
    public ResponseEntity<Boolean> publish(@RequestBody final DialogMessageDto dialogMessageDto)  {
        dialogMessageDto.setFromUserId(getCurrentUserId());
        dialogMessageDto.setCreated(LocalDateTime.now());
        return ResponseEntity.ok(dialogService.sent(dialogMessageDto));
    }

    @PostMapping("/update_seen")
    public ResponseEntity<Boolean> seen(@RequestBody final List<Long> ids)  {
        return ResponseEntity.ok(dialogService.updateSeen(ids));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<DialogMessageDto>> getDialog(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(dialogService.getDialog(getCurrentUserId(), id));
    }

    public Long getCurrentUserId() {
        final AuthenticationWrap authentication = (AuthenticationWrap) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
