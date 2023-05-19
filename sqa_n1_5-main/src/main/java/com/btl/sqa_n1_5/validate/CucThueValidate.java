package com.btl.sqa_n1_5.validate;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.service.DangKiTaiKhoanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class CucThueValidate {
  private final DangKiTaiKhoanService dangKiTaiKhoanService;
  public boolean thongtinCanhan(NguoiDongThueDTO nguoiDongThueDTO){
    NguoiDongThueDTO nguoiDongThueFromDB = dangKiTaiKhoanService.getNguoiDongThue(nguoiDongThueDTO.getThanhvienId());
    if(nguoiDongThueFromDB.getMasothue().equals(nguoiDongThueDTO.getMasothue()) == false ||
    nguoiDongThueFromDB.getHovaten().equals(nguoiDongThueDTO.getHovaten()) == false ||
    nguoiDongThueFromDB.getCCCDId().equals(nguoiDongThueDTO.getCCCDId()) == false  ||
    nguoiDongThueFromDB.getCoquanthue().equals(nguoiDongThueDTO.getCoquanthue()) == false )
      return false;
    return true;
  }
}
