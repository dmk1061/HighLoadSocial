package org.otus.social.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.dto.RegisterUserDto;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.logging.Logger;

@Primary
@AllArgsConstructor
@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    private  final UserService userService;


    @Override
    public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
        final RegisterUserDto registerUserDto = userService.getByUserName(name);
        return new User(registerUserDto.getUsername(), registerUserDto.getPassword(), new ArrayList<>()) {
        };
    }

    public RegisterUserDto loadUserDataByUsername(final String name) throws UsernameNotFoundException {
        return userService.getByUserName(name);

    }

}
