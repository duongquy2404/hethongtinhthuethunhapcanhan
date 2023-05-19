package com.btl.sqa_n1_5.service;

public interface BaseRedisService<T> {
  void delete(String key);
  Object get(String key);
  void set(String key, T object);
}
