package org.otus.social.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.config.RedisConfig;
import org.otus.social.dto.PostDto;
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
    @Qualifier("slaveDataSource")
    private final DataSource slaveDataSource;
    @Qualifier("friendRedisTemplate")
    private  final RedisTemplate redisTemplate;
    @Qualifier("posts")
    private final RedisTemplate<String, PostDto> redisPostTemplate;
    @Override
    public Boolean feedWarmUp() {
        try (final Connection con = slaveDataSource.getConnection()) {
            final List<String> usernames = getUserUsernames(con);
            for (String username : usernames){
                final List<String> friendUsernames = getFriendUsernames(con, username);
                if(friendUsernames.size()>0) {
                    redisTemplate.delete(RedisConfig.SUBSCRIPTION_PREFIX + username);
                    redisTemplate.opsForList().rightPushAll(RedisConfig.SUBSCRIPTION_PREFIX + username, friendUsernames);
                    List<PostDto> posts = getLastPostsFeed(con, username);
                    redisPostTemplate.delete(RedisConfig.FEED_PREFIX + username);
                    redisPostTemplate.opsForList().rightPushAll(RedisConfig.FEED_PREFIX + username, posts);
                }

            }
        } catch (Exception e) {
            log.error("Error during warmup");
            return false;
        }
        return true;
    }
    private List<PostDto> getLastPostsFeed(final Connection con,  final String user) throws SQLException {
        final List<PostDto> posts = new ArrayList<>();
        try (final PreparedStatement selectPosts = con.prepareStatement(
                "SELECT p.id, p.username, p.body, p.created\n" +
                        "FROM POSTS p\n" +
                        "INNER JOIN SUBSCRIPTION s ON p.username = s.friend_username\n" +
                        "WHERE s.username = ?\n" +
                        "ORDER BY p.created DESC\n" +
                        "LIMIT ?;")) {
            selectPosts.setString(1, user);
            selectPosts.setLong(2, RedisConfig.FEED_LIMIT);
            try (final ResultSet selectedUsers = selectPosts.executeQuery()) {
                while (selectedUsers.next()) {
                    PostDto postDto = new PostDto();
                    String username = selectedUsers.getString("username");
                    String body = selectedUsers.getString("body");
                    String time = selectedUsers.getString("created");
                 //   LocalDateTime created = LocalDateTime.parse(time);
                   // postDto.setCreated(created);
                    postDto.setUsername(username);
                    postDto.setBody(body);
                    posts.add(postDto);
                }
            }
        }
        return posts;
    }

    private List<String> getUserUsernames(final Connection con) throws SQLException {
        final List<String> userUsernames = new ArrayList<>();
        try (final PreparedStatement selectUsers = con.prepareStatement(
                "SELECT LOGIN FROM USERS;")) {
            try (final ResultSet selectedUsers = selectUsers.executeQuery()) {
                while (selectedUsers.next()) {
                    String username = selectedUsers.getString("login");
                    userUsernames.add(username);
                }
            }
        }
        return userUsernames;
    }

    private List<String> getFriendUsernames(final Connection con, final String username) throws SQLException {
        final List<String> friendUsernames = new ArrayList<>();
        try (final PreparedStatement selectFriends = con.prepareStatement(
                "SELECT U.LOGIN FROM SUBSCRIPTION S LEFT JOIN USERS U ON S.FRIEND_USERNAME = U.LOGIN WHERE S.USERNAME = ?;")) {
            selectFriends.setString(1, username);
            try (final ResultSet selectedUsers = selectFriends.executeQuery()) {
                while (selectedUsers.next()) {
                    String friendUsername = selectedUsers.getString("login");
                    friendUsernames.add(friendUsername);
                }
            }
        }
        return friendUsernames;
    }
}
