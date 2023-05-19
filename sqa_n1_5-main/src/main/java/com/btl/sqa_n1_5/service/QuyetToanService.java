package com.btl.sqa_n1_5.service;

import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import jakarta.persistence.criteria.CriteriaBuilder.In;

public interface QuyetToanService {
  ThongTinThue findByUserId(Integer userid);

}
