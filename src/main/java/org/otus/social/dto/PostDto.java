package org.otus.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto implements Serializable {

    String body;
    //TODO change flow on userID
    Long userId;

    LocalDateTime created;

}
