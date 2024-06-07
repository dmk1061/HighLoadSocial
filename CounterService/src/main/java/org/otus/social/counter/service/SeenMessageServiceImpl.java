package org.otus.social.counter.service;

import lombok.RequiredArgsConstructor;
import org.otus.social.counter.config.RedisConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeenMessageServiceImpl implements SeenMessageService {


    @Qualifier("friendRedisTemplate")
    private  final RedisTemplate redisTemplate;

    @Override
    public Boolean seen(Long id, Long messageId) {
        redisTemplate.opsForList().rightPushAll(RedisConfig.MESSAGE_PREFIX, messageId);
        return true;
    }

    @Override
    public List<Long> getSeen() {
        List<Long> ids = redisTemplate.opsForList().range(RedisConfig.MESSAGE_PREFIX,0,-1);
        return ids;
    }

    @Override
    public Boolean rollBack(List<Long> ids) {
        redisTemplate.opsForList().rightPushAll(RedisConfig.MESSAGE_PREFIX, ids);
        return true;
    }

}
