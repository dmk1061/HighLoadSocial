package org.otus.social.lib.dto;

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

    Long fromUserId;

    Long toUserId;

    Boolean seen;

    LocalDateTime created;

}
