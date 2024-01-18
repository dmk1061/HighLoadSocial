package org.otus.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private Long id;
    private String name;
    private String surname;
    private Long age;
    private String sex;
    private String city;
    private List<String> interests;
    private String login;
    private String password;

}
