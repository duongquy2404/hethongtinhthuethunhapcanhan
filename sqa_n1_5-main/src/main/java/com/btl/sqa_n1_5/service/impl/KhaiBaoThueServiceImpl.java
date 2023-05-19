package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.model.dto.ThongTinCoBanDTO;
import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.ThongTinCoBan;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.repository.NguoiDongThueRepository;
import com.btl.sqa_n1_5.repository.ThongTinCoBanRepository;
import com.btl.sqa_n1_5.repository.ThongTinThueRepository;
import com.btl.sqa_n1_5.service.KhaiBaoThueService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KhaiBaoThueServiceImpl implements KhaiBaoThueService {
  private final ThongTinCoBanRepository repository;
  private final NguoiDongThueRepository nguoiDongThueRepository;
  private final ThongTinThueRepository thongTinThueRepository;
  @Override
  public ThongTinCoBanDTO getThongTinCoBan(Integer userId) {
    ModelMapper modelMapper = new ModelMapper();
    ThongTinCoBanDTO thongTinCoBan = repository.findByNguoiDongThueId(userId).isPresent()?
        modelMapper.map(repository.findByNguoiDongThueId(userId).get(), ThongTinCoBanDTO.class):
        null;
    if(thongTinCoBan == null){
      thongTinCoBan = ThongTinCoBanDTO.from(nguoiDongThueRepository.findById(userId).get());
    }
    return thongTinCoBan;
  }

  @Override
  public void save(ThongTinCoBanDTO dto) {
    ModelMapper modelMapper = new ModelMapper();
    ThongTinCoBan thongTinCoBan = repository.findByNguoiDongThueId(dto.getNguoiDongThueId()).isPresent()?
        repository.findByNguoiDongThueId(dto.getNguoiDongThueId()).get():
        null;
    if(thongTinCoBan == null){
      thongTinCoBan  = modelMapper.map(dto, ThongTinCoBan.class);
      repository.save(thongTinCoBan);
    }
  }

  @Override
  public void luuThongTinThue(ThongTinThue thongTinThue) {
    thongTinThueRepository.save(thongTinThue);
  }
}
