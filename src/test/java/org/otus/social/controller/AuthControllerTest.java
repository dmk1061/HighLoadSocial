package org.otus.social.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.otus.social.dto.AuthenticationRequest;
import org.otus.social.dto.RegisterUserDto;
import org.otus.social.service.MyUserDetailService;
import org.otus.social.service.UserService;
import org.otus.social.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
@Import({JwtUtil.class,MyUserDetailService.class})
public class AuthControllerTest {

    @MockBean
    private MyUserDetailService userDetailsService;
    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Test

    public void testGetTokenSuccess() { // Arrange

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setPassword("hashed_password_1");
        registerUserDto.setUsername("john_doe");
        registerUserDto.setId(1L);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("john_doe", "hashed_password_1--");
        given(userDetailsService.loadUserDataByUsername(authenticationRequest.getUsername())).willReturn(registerUserDto);
        given(userDetailsService.loadUserByUsername(authenticationRequest.getUsername())).willReturn(new User(registerUserDto.getUsername(),
                registerUserDto.getPassword(), new ArrayList<>()) {
        });

        final ResultActions response;
        try {
            response = mockMvc.perform(myFactoryRequest("/auth/getToken")
                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                    .with(csrf()).content(objectMapper.writeValueAsString(authenticationRequest)));
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.jwt",
                            is(notNullValue())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public MockHttpServletRequestBuilder myFactoryRequest(final String url) {

        return MockMvcRequestBuilders.post(url).header("Content-Type","application/json");
    }
}