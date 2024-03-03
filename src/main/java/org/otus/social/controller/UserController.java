package org.otus.social.controller;

import lombok.AllArgsConstructor;
import org.otus.social.dto.SubscriptionDto;
import org.otus.social.dto.SearchRequestDto;
import org.otus.social.dto.UserDataDto;
import org.otus.social.dto.RegisterUserDto;
import org.otus.social.service.SubscriptionService;
import org.otus.social.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private  final UserService userService;
    private final SubscriptionService subscriptionService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody final RegisterUserDto registerUserDto) throws SQLException {
        return ResponseEntity.ok(userService.registerUser(registerUserDto));
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserDataDto>> search(@RequestBody final SearchRequestDto searchRequestDto) throws SQLException {
        if (searchRequestDto.getFirstName().length()<3 ||searchRequestDto.getLastName().length()<3){
            return  null;
        }
        return ResponseEntity.ok(userService.search(searchRequestDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserDataDto> getUserDataById (@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.getUserDataByUserId(id));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Boolean> subscribe (@RequestBody  final SubscriptionDto subscriptionDto) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        final String username = (principal instanceof UserDetails) ? ((UserDetails)principal).getUsername() : principal.toString();
        subscriptionDto.setUsername(username);
        return  ResponseEntity.ok(subscriptionService.subscribe(subscriptionDto));
    }

}
