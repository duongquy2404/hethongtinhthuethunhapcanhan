package com.btl.sqa_n1_5.service;

import com.btl.sqa_n1_5.model.entity.HoaDon;

public interface ThanhToanService extends BaseRedisService<String>{
  void saveHoaDon(HoaDon hoaDon);
}
