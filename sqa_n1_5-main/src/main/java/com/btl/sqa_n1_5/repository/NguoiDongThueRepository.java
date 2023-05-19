package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDongThueRepository extends JpaRepository<NguoiDongThue,Integer> {
  Optional<NguoiDongThue> findNguoiDongThueByMasothue(String masothue);
  Optional<NguoiDongThue> findNguoiDongThueByEmail(String email);
}
