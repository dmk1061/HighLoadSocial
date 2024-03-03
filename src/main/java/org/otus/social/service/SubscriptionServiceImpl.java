package org.otus.social.service;

import lombok.RequiredArgsConstructor;
import org.otus.social.config.RedisConfig;
import org.otus.social.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    @Qualifier("friendRedisTemplate")
    private  final RedisTemplate redisTemplate;
    @Override
    public boolean subscribe(final SubscriptionDto subscriptionDto) {

        final List<String> friends = redisTemplate.opsForList().range(RedisConfig.RELATION_PREFIX + subscriptionDto.getUsername(), 0, -1);
        redisTemplate.delete(RedisConfig.RELATION_PREFIX + subscriptionDto.getUsername());

        if(subscriptionDto.isSubscription()){
            if(!friends.contains(subscriptionDto.getFriendUsername())){
                friends.add(subscriptionDto.getFriendUsername());
                redisTemplate.opsForList().rightPushAll(RedisConfig.RELATION_PREFIX + subscriptionDto.getUsername(), friends);
            }
        } else {
            if(friends.contains(subscriptionDto.getFriendUsername())){
                friends.remove(subscriptionDto.getFriendUsername());
            }
        }


        return subscriptionDto.isSubscription();
    }

}
