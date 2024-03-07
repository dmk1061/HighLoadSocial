package org.otus.social.controller;

import lombok.AllArgsConstructor;
import org.otus.social.dto.PostDto;
import org.otus.social.service.PostService;
import org.otus.social.service.WarmUpService;
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

    private final WarmUpService warmUpService;

    @PostMapping("/publish")
    public ResponseEntity<Boolean> publish(@RequestBody final PostDto postDto)  {
        postDto.setUsername(getCurrentUserName());
        postDto.setCreated(LocalDateTime.now());
        return ResponseEntity.ok(postService.publish(postDto));
    }

    @GetMapping("/warm")
    public ResponseEntity<Boolean> warmFeed() {
        return ResponseEntity.ok(warmUpService.feedWarmUp());
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFriendsFeed() {
        return ResponseEntity.ok(postService.getFeed(getCurrentUserName()));
    }

    public String getCurrentUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        return (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : principal.toString();
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public PostDto greeting(PostDto message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new PostDto("Hello, " + HtmlUtils.htmlEscape(message.getUsername()) + "!", "", LocalDateTime.now());
    }


}
