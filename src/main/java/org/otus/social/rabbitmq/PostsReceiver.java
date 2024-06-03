package org.otus.social.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.config.RedisConfig;
import org.otus.social.dto.PostDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostsReceiver {
    public static final String NEW_POST ="/topic/greetings/";

    @Qualifier("posts")
    private final RedisTemplate<String, PostDto> redisPostTemplate;
    @Qualifier("friends")
    private final RedisTemplate<String, String> redisFriendTemplate;
    private final ObjectMapper objectMapper;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final SimpMessagingTemplate simpMessagingTemplate;
    public void receiveMessage(byte[] byteArray) throws JsonProcessingException {
        final String message = new String(byteArray, StandardCharsets.UTF_8); try {
            final PostDto postDto = objectMapper.readValue(message, PostDto.class);
            final List<String> friends = redisFriendTemplate.opsForList().range(RedisConfig.SUBSCRIPTION_PREFIX + postDto.getUserId(), 0, -1);
            for (final String friendUsername : friends) {
                redisPostTemplate.opsForList().rightPushAll(RedisConfig.FEED_PREFIX + friendUsername, postDto);
                redisPostTemplate.opsForList().trim(RedisConfig.FEED_PREFIX + friendUsername, -1 * RedisConfig.FEED_LIMIT, -1);
                simpMessagingTemplate.convertAndSend( NEW_POST+friendUsername, postDto);
            }
            log.info("Received <" + message + ">");
            latch.countDown();
        }catch (Exception e){
            log.error("PostReceiver error " + e.getMessage());
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}