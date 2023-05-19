package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.entity.HoaDon;
import com.btl.sqa_n1_5.repository.ThanhToanRepository;
import com.btl.sqa_n1_5.service.ThanhToanService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ThanhToanServiceImpl extends BaseRedisServiceImpl<String> implements ThanhToanService {
  private final ThanhToanRepository thanhToanRepository;

  public ThanhToanServiceImpl(
      RedisTemplate<String, Object> redisTemplate, ThanhToanRepository thanhToanRepository) {
    super(redisTemplate);
    this.thanhToanRepository = thanhToanRepository;
  }

  @Override
  public void saveHoaDon(HoaDon hoaDon) {
    thanhToanRepository.save(hoaDon);
  }
}
