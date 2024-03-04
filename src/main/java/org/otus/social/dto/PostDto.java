package org.otus.social.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostDto implements Serializable {

    String body;
    //TODO change flow on userID
    String username;

    LocalDateTime created;

}
