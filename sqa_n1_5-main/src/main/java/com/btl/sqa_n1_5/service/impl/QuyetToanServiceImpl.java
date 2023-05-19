package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.repository.QuyetToanRepository;
import com.btl.sqa_n1_5.service.QuyetToanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuyetToanServiceImpl implements QuyetToanService {
  private final QuyetToanRepository repository;
  @Override
  public ThongTinThue findByUserId(Integer userid) {
    ThongTinThue thongTinThue = repository.findByNguoiDongThueId(userid).isPresent()?
        repository.findByNguoiDongThueId(userid).get():null;
    return  thongTinThue;
  }
}
