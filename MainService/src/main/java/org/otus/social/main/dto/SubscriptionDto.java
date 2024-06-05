package org.otus.social.dto;

import lombok.Data;

@Data
public class SubscriptionDto {

     Long userId;

     Long friendId;

     boolean subscription;

}
