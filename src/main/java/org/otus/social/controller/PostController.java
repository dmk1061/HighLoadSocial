package org.otus.social.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.otus.social.dto.PostDto;
import org.otus.social.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/publish")
    public ResponseEntity<Boolean> publish(@RequestBody final PostDto postDto)  {
        postDto.setUsername(getCurrentUserName());
        postDto.setCreated(LocalDateTime.now());
        return ResponseEntity.ok(postService.publish(postDto));
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

}
