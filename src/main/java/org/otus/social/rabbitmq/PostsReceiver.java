package org.otus.social.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.config.RedisConfig;
import org.otus.social.dto.PostDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostsReceiver {

    @Qualifier("posts")
    private final RedisTemplate<String, PostDto> redisPostTemplate;
    @Qualifier("friends")
    private final RedisTemplate<String, String> redisFriendTemplate;
    private final ObjectMapper objectMapper;
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) throws JsonProcessingException {
        final PostDto postDto = objectMapper.readValue(message, PostDto.class);
        final List<String> friends = redisFriendTemplate.opsForList().range(RedisConfig.RELATION_PREFIX + postDto.getUsername(), 0, -1);
        for (final String friendUsername : friends) {
            final List<PostDto> posts = redisPostTemplate.opsForList().range(RedisConfig.FEED_PREFIX + friendUsername, 0, -1);
            posts.add(postDto);
            redisPostTemplate.delete(RedisConfig.FEED_PREFIX + friendUsername);
            redisPostTemplate.opsForList().rightPushAll(RedisConfig.FEED_PREFIX + friendUsername, posts);
        }
        log.info("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}