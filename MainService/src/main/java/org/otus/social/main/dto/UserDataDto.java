package org.otus.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {

    private String name;
    private String surname;
    private Long age;
    private String sex;
    private String city;
    private List<String> interests;

}
