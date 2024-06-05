package otus.social.controller;

import lombok.AllArgsConstructor;
import org.otus.social.dto.DialogMessageDto;
import org.otus.social.service.DialogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        dialogMessageDto.setFromUser(getCurrentUserName());
        dialogMessageDto.setCreated(LocalDateTime.now());
        return ResponseEntity.ok(dialogService.sent(dialogMessageDto));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<DialogMessageDto>> getDialog(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(dialogService.getDialog( getCurrentUserName(), id));
    }

    public String getCurrentUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        return (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
    }
}
