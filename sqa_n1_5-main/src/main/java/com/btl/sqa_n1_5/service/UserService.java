package com.btl.sqa_n1_5.service;


import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Thanhvien;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;

public interface UserService {
  void saveUser(NguoiDongThueDTO nguoiDongThueDTO);

  Thanhvien findByUsername(String username);

  List<NguoiDongThueDTO> findAllUsers();
  void saveTaxStatus(Integer nguoidongthueId, Integer status);
  NguoiDongThue findNguoiDongThueById(Integer id);
}
