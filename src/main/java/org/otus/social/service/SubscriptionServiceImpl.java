package org.otus.social.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.config.RedisConfig;
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

        final List<String> friends = redisTemplate.opsForList().range(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUsername(), 0, -1);
        redisTemplate.delete(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUsername());

        if (subscriptionDto.isSubscription()) {
            if (!friends.contains(subscriptionDto.getFriendUsername())) {
                friends.add(subscriptionDto.getFriendUsername());
                redisTemplate.opsForList().rightPushAll(RedisConfig.SUBSCRIPTION_PREFIX + subscriptionDto.getUsername(), friends);
            }
        } else {
            if (friends.contains(subscriptionDto.getFriendUsername())) {
                friends.remove(subscriptionDto.getFriendUsername());
            }
        }

        persistSubscription(subscriptionDto);
        return subscriptionDto.isSubscription();
    }

    public void persistSubscription(SubscriptionDto subscriptionDto) {
        try (Connection con = masterDataSource.getConnection()) {

            if (subscriptionDto.isSubscription()) {
                try (final PreparedStatement insertSubscription = con.prepareStatement(
                        "INSERT INTO SUBSCRIPTION (USERNAME, FRIEND_USERNAME) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    insertSubscription.setString(1, subscriptionDto.getUsername());
                    insertSubscription.setString(2, subscriptionDto.getFriendUsername());
                    insertSubscription.executeUpdate();
                }
            } else {
                try (final PreparedStatement deleteSubscription = con.prepareStatement(
                        "DELETE FROM SUBSCRIPTION  WHERE USERNAME= ? AND FRIEND_USERNAME = ?")) {
                    deleteSubscription.setString(1, subscriptionDto.getUsername());
                    deleteSubscription.setString(2, subscriptionDto.getFriendUsername());
                    deleteSubscription.executeUpdate();
                }

            }
        }catch(Exception e){
                log.error("Error during subscription persistence");
            }
        }

    }
