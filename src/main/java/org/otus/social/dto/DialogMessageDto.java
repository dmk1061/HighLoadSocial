package org.otus.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogMessageDto implements Serializable {

    String body;

    String fromUser;

    String toUser;

    LocalDateTime created;

}
