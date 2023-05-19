package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.service.BaseRedisService;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BaseRedisServiceImpl<T> implements BaseRedisService<T> {
  private final RedisTemplate<String,Object> redisTemplate;
  private final Long timeout = 150L;
  private final TimeUnit unitTimeOut = TimeUnit.MINUTES;
  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void set(String key, T object) {
    redisTemplate.opsForValue().set(key,object,timeout,unitTimeOut);
  }
}
