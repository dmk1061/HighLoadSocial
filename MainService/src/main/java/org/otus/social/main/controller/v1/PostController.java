package org.otus.social.main.controller.v1;

import lombok.AllArgsConstructor;
import org.otus.social.main.dto.PostDto;
import org.otus.social.main.service.PostService;
import org.otus.social.main.service.UserService;
import org.otus.social.main.service.WarmUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.HtmlUtils;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private  final UserService userService;
    private final WarmUpService warmUpService;

    @PostMapping("/publish")
    public ResponseEntity<Boolean> publish(@RequestBody final PostDto postDto)  {
        postDto.setUserId(getCurrentUserId());
        postDto.setCreated(LocalDateTime.now());
        return ResponseEntity.ok(postService.publish(postDto));
    }

    @GetMapping("/warm")
    public ResponseEntity<Boolean> warmFeed() {
        return ResponseEntity.ok(warmUpService.feedWarmUp());
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFriendsFeed() {
        return ResponseEntity.ok(postService.getFeed(getCurrentUserId()));
    }

    public Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        final String username = (principal instanceof UserDetails) ? ((UserDetails)principal).getUsername() : principal.toString();
        final Long userId = userService.getByUserName(username).getId();
        return userId;
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings/john_doe")
    public PostDto greeting(final PostDto message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new PostDto("Hello, " + HtmlUtils.htmlEscape(""+message.getUserId()) + "!", message.getUserId(), LocalDateTime.now());
    }


}
