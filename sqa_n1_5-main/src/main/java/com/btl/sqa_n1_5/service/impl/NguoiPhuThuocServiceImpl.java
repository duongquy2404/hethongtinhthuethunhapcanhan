package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.entity.NguoiPhuThuoc;
import com.btl.sqa_n1_5.repository.NguoiPhuThuocRepository;
import com.btl.sqa_n1_5.service.NguoiPhuThuocService;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NguoiPhuThuocServiceImpl implements NguoiPhuThuocService {
  private final NguoiPhuThuocRepository repository;

  @Override
  public NguoiPhuThuoc themNguoiPhuThuoc(NguoiPhuThuoc nguoiPhuThuoc) {
    return repository.save(nguoiPhuThuoc);
  }

  @Override
  public NguoiPhuThuoc suaNguoiPhuThuoc(NguoiPhuThuoc nguoiPhuThuoc) {
    NguoiPhuThuoc phuThuoc = repository.findById(nguoiPhuThuoc.getId()).isPresent()?repository.findById(nguoiPhuThuoc.getId()).get():
        null;
    if(phuThuoc!= null){
      nguoiPhuThuoc.setNguoidongthueid(phuThuoc.getNguoidongthueid());
      repository.save(nguoiPhuThuoc);
    }
    return nguoiPhuThuoc;
  }

  @Override
  public void xoaNguoiPhuThuoc(Integer id) {
     repository.deleteById(id);
  }

  @Override
  public List<NguoiPhuThuoc> findAllByNguoiDongThue(Integer id) {
    log.info("findallbynguoidongthue is: {}", repository.findAllByNguoidongthueid(id));
    return repository.findAllByNguoidongthueid(id);
  }
}
