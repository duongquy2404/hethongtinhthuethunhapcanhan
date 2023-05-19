package com.btl.sqa_n1_5.service;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.model.entity.NguoiDongThue;

public interface DangKiTaiKhoanService {
  NguoiDongThueDTO getNguoiDongThue(String masothue);
  NguoiDongThueDTO getNguoiDongThue(Integer id);
  String sendOTP(String email);
}
