package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import com.btl.sqa_n1_5.repository.NguoiDongThueRepository;
import com.btl.sqa_n1_5.repository.ThanhVienRepository;
import com.btl.sqa_n1_5.service.BaseRedisService;
import com.btl.sqa_n1_5.service.DangKiTaiKhoanService;
import com.btl.sqa_n1_5.service.EmailService;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DangKiTaiKhoanServiceImpl implements DangKiTaiKhoanService {
  private final NguoiDongThueRepository nguoiDongThueRepository;
  private final ThanhVienRepository thanhVienRepository;
  private final EmailService emailService;
  private final BaseRedisService<String> baseRedisService;
  @Override
  public NguoiDongThueDTO getNguoiDongThue(String masothue) {
    Optional<NguoiDongThue> nguoiDongThue = nguoiDongThueRepository.findNguoiDongThueByMasothue(masothue);
    NguoiDongThueDTO nguoiDongThueDTO = nguoiDongThue.isPresent()?NguoiDongThueDTO.from(nguoiDongThue.get()):null;
    return nguoiDongThueDTO;
  }

  @Override
  public NguoiDongThueDTO getNguoiDongThue(Integer id) {
    Optional<NguoiDongThue> nguoiDongThue = nguoiDongThueRepository.findById(id);
    Optional<Thanhvien> thanhvien =thanhVienRepository.findById(id);
    NguoiDongThueDTO nguoiDongThueDTO = nguoiDongThue.isPresent()?NguoiDongThueDTO.from(nguoiDongThue.get()):null;
    return nguoiDongThueDTO;
  }

  @Override
  public String sendOTP(String email) {
      new Thread(
              new Runnable() {
                @Override
                public void run() {
                  emailService.sendEmail(
                      email,
                      forgotPasswordMail(email, randomOTP(email)),
                      "XÁC THỰC PHIÊN GIAO DỊCH\n");
                }
              })
          .start();
    return "Success";

  }
  private String randomOTP(String email) {
    String OTP_CHARS = "0123456789";
    StringBuilder sb = new StringBuilder(6);
    Random random = new Random();

    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(OTP_CHARS.length());
      sb.append(OTP_CHARS.charAt(index));
    }
    baseRedisService.set(email,sb.toString());
    return sb.toString();
  }

  private String forgotPasswordMail(String email, String OTP) {
    String content =
        "<p>Chào <b>"
            + email
            + "</b>,</p>"
            + "<p>Bạn đang thực hiện đăng kí tài khoản trên cổng dịch vụ THUECANHAN.VN</p>"
            + "<p>Dưới đây là mã OTP bạn cần để xác thực phiên giao dịch: </p>"
            + "<h1>OTP - "
            + OTP
            + "</h1>"
            + "<p> <span style=\"color: red\">(*)</span> Lưu ý: Mã OTP chỉ có giá trị trong vòng 5 phút.</p>"
            + "<p>Nếu có vướng mắc gì thêm, bạn vui lòng liên hệ theo thông tin phía dưới:</p>"
            + "<p>- Hotline: 090 488 6095 - 19001568</p>"
            + "<p>Cảm ơn bạn đã sử dụng dịch vụ của THUECANHAN.VN</p>"
            + "<p>Trân trọng.</p>"
            + "<p>Đội ngũ THUECANHAN.VN</p>";

    return content;
  }

}
