package com.btl.sqa_n1_5.model.dto;

import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinCoBanDTO {

  private Integer id;
  private Integer nguoiDongThueId;
  private String hovaten;
  private String diachi;
  private String dienthoai;
  @Email
  private String email;
  private String masothue;
  private String cucthue;
  private String chicucthue;
  private Integer namKeKhai;
  private Integer thangBatDau;
  private Integer thangKetThuc;
  private Integer loaiToKhai; // 1 nếu là chính thức, 2 nếu là bổ sung
  public static ThongTinCoBanDTO from(NguoiDongThue nguoiDongThue){
    ThongTinCoBanDTO thongTinCoBanDTO = new ThongTinCoBanDTO();
    thongTinCoBanDTO.setHovaten(nguoiDongThue.getHovaten());
    thongTinCoBanDTO.setDiachi(nguoiDongThue.getDiachiHientai());
    thongTinCoBanDTO.setEmail(nguoiDongThue.getEmail());
    thongTinCoBanDTO.setCucthue(nguoiDongThue.getCoquanthue());
    thongTinCoBanDTO.setChicucthue(nguoiDongThue.getCoquanthue());
    thongTinCoBanDTO.setDienthoai(nguoiDongThue.getDienthoai());
    thongTinCoBanDTO.setNamKeKhai(2023);
    return thongTinCoBanDTO;
  }
}
