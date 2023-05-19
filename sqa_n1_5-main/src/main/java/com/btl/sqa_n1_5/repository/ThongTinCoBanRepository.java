package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.ThongTinCoBan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThongTinCoBanRepository extends JpaRepository<ThongTinCoBan, Integer> {
  Optional<ThongTinCoBan> findByNguoiDongThueId(Integer id);
}
