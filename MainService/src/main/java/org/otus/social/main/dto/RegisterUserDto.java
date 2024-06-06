package org.otus.social.main.dto;

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
    private String username;
    private String password;

}
