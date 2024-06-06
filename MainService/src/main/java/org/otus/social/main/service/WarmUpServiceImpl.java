package org.otus.social.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.main.config.RedisConfig;
import org.otus.social.main.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarmUpServiceImpl implements WarmUpService {

    @Autowired
    @Qualifier("masterDataSource")
    private final DataSource masterDataSource;
    @Qualifier("friendRedisTemplate")
    private  final RedisTemplate redisTemplate;
    @Qualifier("posts")
    private final RedisTemplate<String, PostDto> redisPostTemplate;
    @Override
    public Boolean feedWarmUp() {
        try (final Connection con = masterDataSource.getConnection()) {
            final List<Long> userIds = getUserUsernames(con);
            for (Long userId : userIds){
                final List<String> friendUsernames = getFriendUsernames(con, userId);
                if(friendUsernames.size()>0) {
                    redisTemplate.delete(RedisConfig.SUBSCRIPTION_PREFIX + userId);
                    redisTemplate.opsForList().rightPushAll(RedisConfig.SUBSCRIPTION_PREFIX + userId, friendUsernames);
                    List<PostDto> posts = getLastPostsFeed(con, userId);
                    redisPostTemplate.delete(RedisConfig.FEED_PREFIX + userId);
                    redisPostTemplate.opsForList().rightPushAll(RedisConfig.FEED_PREFIX + userId, posts);
                }
            }
        } catch (Exception e) {
            log.error("Error during warmup");
            return false;
        }
        return true;
    }
    private List<PostDto> getLastPostsFeed(final Connection con,  final Long userId) throws SQLException {
        final List<PostDto> posts = new ArrayList<>();
        try (final PreparedStatement selectPosts = con.prepareStatement(
                "SELECT p.id, p.user_id, p.body, p.created\n" +
                        "FROM POSTS p\n" +
                        "INNER JOIN SUBSCRIPTION s ON p.user_id = s.friend_id\n" +
                        "WHERE s.user_id = ?\n" +
                        "ORDER BY p.created DESC\n" +
                        "LIMIT ?;")) {
            selectPosts.setLong(1, userId);
            selectPosts.setLong(2, RedisConfig.FEED_LIMIT);
            try (final ResultSet selectedUsers = selectPosts.executeQuery()) {
                while (selectedUsers.next()) {
                    PostDto postDto = new PostDto();
                //    Long userId = selectedUsers.getLong("user_id");
                    String body = selectedUsers.getString("body");
                    String time = selectedUsers.getString("created");
                 //   LocalDateTime created = LocalDateTime.parse(time);
                   // postDto.setCreated(created);
                    postDto.setUserId(userId);
                    postDto.setBody(body);
                    posts.add(postDto);
                }
            }
        }
        return posts;
    }

    private List<Long> getUserUsernames(final Connection con) throws SQLException {
        final List<Long> userIds = new ArrayList<>();
        try (final PreparedStatement selectUsers = con.prepareStatement(
                "SELECT ID FROM USERS;")) {
            try (final ResultSet selectedUsers = selectUsers.executeQuery()) {
                while (selectedUsers.next()) {
                    Long userId = selectedUsers.getLong("id");
                    userIds.add(userId);
                }
            }
        }
        return userIds;
    }

    private List<String> getFriendUsernames(final Connection con, final Long userId) throws SQLException {
        final List<String> friendUsernames = new ArrayList<>();
        try (final PreparedStatement selectFriends = con.prepareStatement(
                "SELECT U.USERNAME FROM SUBSCRIPTION S LEFT JOIN USERS U ON S.FRIEND_ID = U.ID WHERE S.USER_ID = ?;")) {
            selectFriends.setLong(1, userId);
            try (final ResultSet selectedUsers = selectFriends.executeQuery()) {
                while (selectedUsers.next()) {
                    String friendUsername = selectedUsers.getString("username");
                    friendUsernames.add(friendUsername);
                }
            }
        }
        return friendUsernames;
    }
}
