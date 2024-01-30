package org.otus.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchRequestDto {

    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
}
