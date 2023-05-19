package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.service.QuyetToanService;
import com.btl.sqa_n1_5.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("quyet-toan-thue")
@AllArgsConstructor
public class QuyetToanThueController {
  private final UserService userService;
  private final QuyetToanService quyetToanService;
  @GetMapping
  public String show(Model model, @RequestParam(name = "paystatus", required = false) String paystatus){
    // nếu check thong tin khai da duyet thành công 1 => hien thi thong tin can nop
    // nếu check thông tin khai phê duyệt thất bại 2 => hien thong tin sai
    // nếu thông tin chưa được duyệt 00 => quý khách vui lòng đợi thông tin từ cục thuế, kết quả sẽ có sau 3 ngày kể từ ngày khai báo
    // nếu đã đóng 03 => đã tất toán và tắt nút đóng đi., bên back end cũng cần check case này để tránh gọi api đóng lại
    // nếu đóng thất bại 04
    // làm trường hợp số 1, đã có thông tin thuế gửi về

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    ThongTinThue thongTinThue = quyetToanService.findByUserId(user.getId());
    NguoiDongThue nguoiDongThue = userService.findNguoiDongThueById(user.getId());
    if(paystatus!= null && paystatus.equals("00")){
      userService.saveTaxStatus(user.getId(),3);
      model.addAttribute("paystatus", "00"); // thanh toán thành công
    }else if(paystatus != null && paystatus.equals("99")){
      model.addAttribute("paystatus", "99"); // thanh toán thất bại
    };
    if(nguoiDongThue.getTaxStatus() == 0){
      model.addAttribute("status","00"); // chưa được duyệt
    }
    if(nguoiDongThue.getTaxStatus() == 1){
      model.addAttribute("status","01"); // duyệt thành công
    }
    if(nguoiDongThue.getTaxStatus() == 2){
      model.addAttribute("status","02"); // duyệt thất bại
    }
    if(nguoiDongThue.getTaxStatus() == 3){
      model.addAttribute("status", "03"); // đã thanh toán
    }
    model.addAttribute("thongtinthue", thongTinThue);
    return "quyettoan/quyettoan";
  }
}
