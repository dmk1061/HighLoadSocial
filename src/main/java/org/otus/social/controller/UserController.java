package org.otus.social.controller;

import lombok.AllArgsConstructor;
import org.otus.social.dto.UserDataDto;
import org.otus.social.dto.RegisterUserDto;
import org.otus.social.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private  final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> registerUser(@RequestBody final RegisterUserDto registerUserDto) throws SQLException {
        return ResponseEntity.ok(userService.registerUser(registerUserDto));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDataDto> getUserDataById (@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.getUserDataByUserId(id));
    }




}
