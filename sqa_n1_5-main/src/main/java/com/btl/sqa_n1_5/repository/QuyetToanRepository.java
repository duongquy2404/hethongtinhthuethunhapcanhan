package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuyetToanRepository extends JpaRepository<ThongTinThue, Integer> {
  Optional<ThongTinThue> findByNguoiDongThueId(Integer id);

}
