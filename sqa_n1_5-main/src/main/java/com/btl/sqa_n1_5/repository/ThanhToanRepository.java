package com.btl.sqa_n1_5.repository;

import com.btl.sqa_n1_5.model.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhToanRepository extends JpaRepository<HoaDon,Integer> {

}
