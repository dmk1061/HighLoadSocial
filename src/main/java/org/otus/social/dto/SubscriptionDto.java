package org.otus.social.dto;

import lombok.Data;


@Data
public class SubscriptionDto {

     String username;

     String friendUsername;

     boolean subscription;

}
