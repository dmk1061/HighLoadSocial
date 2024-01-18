package org.otus.social.controller;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.otus.social.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations =  "{classpath:application.yml}")
@ContextConfiguration(classes = JwtUtil.class)
public class JwtUtilsTest {


    @InjectMocks
    JwtUtil jwtUtils;

    @Test
    public  void generateToken(){
        ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", "value");
        UserDetails userDetails = new User("login", "password", new ArrayList<>());
        String jwt = jwtUtils.generateToken(userDetails, "1");
        Assert.assertTrue(!jwt.isEmpty());

    }
}
