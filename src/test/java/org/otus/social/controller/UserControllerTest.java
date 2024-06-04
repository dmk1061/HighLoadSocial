package org.otus.social.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.otus.social.dto.UserDataDto;
import org.otus.social.service.SubscriptionService;
import org.otus.social.service.UserService;
import org.otus.social.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@Import({JwtUtil.class})
public class UserControllerTest {
    @MockBean
    UserService userService;
    @InjectMocks
    SubscriptionService subscriptionService;

    @Autowired
    private MockMvc mockMvc;
    @WithMockUser(username="user1@mail.ru")
    @Test
    public void getUserFormById() throws Exception {
        final List<String> interests = new ArrayList<>();
        final UserDataDto userDataDto = new UserDataDto("name", "surname",50L, "M", "MOSCOW", interests);
        given(userService.getUserDataByUserId(1L)).willReturn(userDataDto);
        final ResultActions response = mockMvc.perform(myFactoryRequest("/user/get/1"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.interests.size()",
                        is(userDataDto.getInterests().size())));
    }

    public MockHttpServletRequestBuilder myFactoryRequest(final String url) {

        return MockMvcRequestBuilders.get(url);
    }



}
