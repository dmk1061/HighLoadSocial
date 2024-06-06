package org.otus.social.main.service;


import org.otus.social.main.dto.RegisterUserDto;
import org.otus.social.main.dto.SearchRequestDto;
import org.otus.social.main.dto.UserDataDto;

import java.sql.SQLException;
import java.util.List;

public interface UserService {


     Long registerUser (RegisterUserDto registerUserDto) throws SQLException;

     List<UserDataDto> search(SearchRequestDto searchRequestDto);

    UserDataDto getUserDataByUserIdInMemory(Long userId);

    UserDataDto getUserDataByUserId (Long id);

     RegisterUserDto getByUserName (String username);

}
