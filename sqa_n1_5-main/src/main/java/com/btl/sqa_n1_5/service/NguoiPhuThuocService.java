package com.btl.sqa_n1_5.service;

import com.btl.sqa_n1_5.model.entity.NguoiPhuThuoc;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;

public interface NguoiPhuThuocService {
  NguoiPhuThuoc themNguoiPhuThuoc(NguoiPhuThuoc nguoiPhuThuoc);
  NguoiPhuThuoc suaNguoiPhuThuoc(NguoiPhuThuoc nguoiPhuThuoc);
  void xoaNguoiPhuThuoc(Integer id);
  List<NguoiPhuThuoc> findAllByNguoiDongThue(Integer id);
}
