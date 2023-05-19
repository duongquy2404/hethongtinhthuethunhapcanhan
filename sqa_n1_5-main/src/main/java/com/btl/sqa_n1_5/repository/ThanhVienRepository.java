package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.Thanhvien;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhVienRepository extends JpaRepository<Thanhvien, Integer> {
  Optional<Thanhvien> findByUsername (String username);
  Optional<Thanhvien> findByEmail(String email);
}
