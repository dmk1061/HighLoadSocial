package org.otus.social.service;

import org.otus.social.dto.UserDataDto;
import org.otus.social.dto.RegisterUserDto;

import java.sql.SQLException;

public interface UserService {


     Long registerUser (RegisterUserDto registerUserDto) throws SQLException;

     UserDataDto getUserDataByUserId (Long id);

     RegisterUserDto getByUserName (String login);
}
