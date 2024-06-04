package org.otus.social.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.otus.social.dto.PostDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {
    public static final String FEED_PREFIX = "feed";
    public static final String SUBSCRIPTION_PREFIX = "subscription";
    public static final int FEED_LIMIT = 1000;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean(name = "posts")
    public RedisTemplate<String, PostDto> postRedisTemplate() {
        final RedisTemplate<String, PostDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        final Jackson2JsonRedisSerializer<PostDto> serializer = new Jackson2JsonRedisSerializer<>(objectMapper(),PostDto.class);
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }
@Primary
    @Bean(name = "friends")
    public RedisTemplate<String, String> friendRedisTemplate() {
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(String.class));
        return redisTemplate;
    }
    @Bean
    public ObjectMapper objectMapper (){
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}