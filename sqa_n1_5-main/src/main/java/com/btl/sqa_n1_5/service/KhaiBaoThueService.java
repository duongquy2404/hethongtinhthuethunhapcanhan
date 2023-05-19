package com.btl.sqa_n1_5.service;

import com.btl.sqa_n1_5.model.dto.ThongTinCoBanDTO;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;

public interface KhaiBaoThueService {
  ThongTinCoBanDTO getThongTinCoBan(Integer userId);

  void save(ThongTinCoBanDTO dto);
  void luuThongTinThue(ThongTinThue thongTinThue);
}
