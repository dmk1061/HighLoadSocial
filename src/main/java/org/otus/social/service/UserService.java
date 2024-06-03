package org.otus.social.service;

import org.otus.social.dto.SearchRequestDto;
import org.otus.social.dto.UserDataDto;
import org.otus.social.dto.RegisterUserDto;
import java.sql.SQLException;
import java.util.List;

public interface UserService {


     Long registerUser (RegisterUserDto registerUserDto) throws SQLException;

     List<UserDataDto> search(SearchRequestDto searchRequestDto);

    UserDataDto getUserDataByUserIdInMemory(Long userId);

    UserDataDto getUserDataByUserId (Long id);

     RegisterUserDto getByUserName (String username);

}
