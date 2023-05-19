package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.NguoiPhuThuoc;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NguoiPhuThuocRepository extends JpaRepository<NguoiPhuThuoc, Integer> {
  List<NguoiPhuThuoc> findAllByNguoidongthueid(Integer id);

}
