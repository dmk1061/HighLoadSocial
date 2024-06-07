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
    public ResponseEntity<Boolean> publish(@RequestBody final DialogMessageDto dialogMessageDto, @RequestHeader(value = "API-Version", required = false) final String apiVersion) {
        dialogMessageDto.setFromUserId(getCurrentUserId());
        dialogMessageDto.setCreated(LocalDateTime.now());
        Boolean result;
        if (apiVersion!= null  && apiVersion.equals("v2")) {
            result = dialogService.sentV2(dialogMessageDto);
        } else {
            result = dialogService.sent(dialogMessageDto);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_seen")
    public ResponseEntity<Boolean> seen(@RequestBody final List<Long> ids, @RequestHeader(value = "API-Version", required = false) final String apiVersion) {
        Boolean result;
        if (apiVersion!= null  && apiVersion.equals("v2")) {
            result = dialogService.updateSeenV2(ids);
        } else {
            result = dialogService.updateSeen(ids);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<DialogMessageDto>> getDialog(@PathVariable("id") final Long id, @RequestHeader(value = "API-Version", required = false) final String apiVersion) {
        List<DialogMessageDto> result;
        if (apiVersion!= null  && apiVersion.equals("v2")) {
            result = dialogService.getDialogV2(getCurrentUserId(), id);
        } else {
            result = dialogService.getDialog(getCurrentUserId(), id);
        }
        return ResponseEntity.ok(result);
    }

    public Long getCurrentUserId() {
        final AuthenticationWrap authentication = (AuthenticationWrap) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
