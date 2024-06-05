package org.otus.social.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import otus.social.config.RedisConfig;
import org.otus.social.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    @Qualifier("friendRedisTemplate")
    private final RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;

    @Override
    public boolean subscribe(final SubscriptionDto subscriptionDto) {

        final List<Long> friends = redisTemplate.opsForList().range(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUserId(), 0, -1);
        redisTemplate.delete(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUserId());

        if (subscriptionDto.isSubscription()) {
            if (!friends.contains(subscriptionDto.getFriendId())) {
                friends.add(subscriptionDto.getFriendId());
                redisTemplate.opsForList().rightPushAll(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUserId(), friends);
            }
        } else {
            if (friends.contains(subscriptionDto.getFriendId())) {
                friends.remove(subscriptionDto.getFriendId());
            }
        }

        persistSubscription(subscriptionDto);
        return subscriptionDto.isSubscription();
    }

    public void persistSubscription(final SubscriptionDto subscriptionDto) {
        try (final Connection con = masterDataSource.getConnection()) {

            if (subscriptionDto.isSubscription()) {
                try (final PreparedStatement insertSubscription = con.prepareStatement(
                        "INSERT INTO SUBSCRIPTION (USER_ID, FRIEND_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    insertSubscription.setLong(1, subscriptionDto.getUserId());
                    insertSubscription.setLong(2, subscriptionDto.getFriendId());
                    insertSubscription.executeUpdate();
                }
            } else {
                try (final PreparedStatement deleteSubscription = con.prepareStatement(
                        "DELETE FROM SUBSCRIPTION  WHERE USER_ID= ? AND FRIEND_ID = ?")) {
                    deleteSubscription.setLong(1, subscriptionDto.getUserId());
                    deleteSubscription.setLong(2, subscriptionDto.getFriendId());
                    deleteSubscription.executeUpdate();
                }
            }
        }catch(Exception e){
                log.error("Error during subscription persistence");
            }
        }
    }
