package org.otus.social.main.service;

import org.otus.social.main.rabbitmq.PostsReceiver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.otus.social.main.config.RabbitConfig;
import org.otus.social.main.config.RedisConfig;
import org.otus.social.main.dto.PostDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final RabbitTemplate rabbitTemplate;

    @Qualifier("posts")
    private final RedisTemplate<String, PostDto> redisTemplate;
    private final PostsReceiver receiver;
    private final ObjectMapper objectMapper;

    @Override
    public Boolean publish( final PostDto postDto) {
        try {
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, objectMapper.writeValueAsString(postDto));
            receiver.getLatch().await(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | JsonProcessingException exception) {
            log.error("Error during message publishing :" + exception.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<PostDto> getFeed(final Long userId) {
        final List<PostDto> friendsFeed = redisTemplate.opsForList().range(RedisConfig.FEED_PREFIX + userId, 0, -1);
        return friendsFeed;
    }

}
