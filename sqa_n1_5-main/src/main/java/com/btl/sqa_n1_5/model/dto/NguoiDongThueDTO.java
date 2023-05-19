package com.btl.sqa_n1_5.model.dto;

import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import java.util.Date;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

//@AllArgsConstructor
@NoArgsConstructor
@Data
public class NguoiDongThueDTO {
  private Integer ThanhvienId;
  private String masothue;
  private String coquanthue;
  private String username;
  private String password;
  private String hovaten;
  private Date ngaysinh;
  @NotBlank(message = "Không bỏ trống số điện thoại")
  @Pattern(regexp = "^\\+?[0-9]{10,12}$", message = "Số điện thoại không hợp lệ")
  private String dienthoai;
  @NotBlank(message = "Không bỏ trống email")
  @Email(message = "Email không hợp lệ")
  private String email;
  private String gioitinh;
  private String quoctich;
  private String diachiThuongtru;
  private String diachiHientai;
  private Integer CCCDId;
  private String OTP;
  public NguoiDongThueDTO(Integer thanhvienId, String masothue, String hovaten, Integer CCCDId, String coquanthue, String diachiHientai, String dienthoai, String email) {
    ThanhvienId = thanhvienId;
    this.masothue = masothue;
    this.hovaten = hovaten;
    this.CCCDId = CCCDId;
    this.coquanthue = coquanthue;
    this.diachiHientai = diachiHientai;
    this.dienthoai = dienthoai;
    this.email = email;
  }

  public static NguoiDongThueDTO from(NguoiDongThue nguoiDongThue){
    return new NguoiDongThueDTO(nguoiDongThue.getThanhvienId(),nguoiDongThue.getMasothue(),
        nguoiDongThue.getHovaten() , nguoiDongThue.getCCCDId(),nguoiDongThue.getCoquanthue(),
        nguoiDongThue.getDiachiHientai(), nguoiDongThue.getDienthoai(), nguoiDongThue.getEmail());
  }
}
