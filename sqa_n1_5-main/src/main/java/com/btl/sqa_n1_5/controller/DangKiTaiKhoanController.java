package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.model.dto.MaSoThueDTO;
import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.service.BaseRedisService;
import com.btl.sqa_n1_5.service.DangKiTaiKhoanService;
import com.btl.sqa_n1_5.service.UserService;
import com.btl.sqa_n1_5.validate.CucThueValidate;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dang-ki-tai-khoan")
@AllArgsConstructor
public class DangKiTaiKhoanController {
  private final DangKiTaiKhoanService dangKiTaiKhoanService;
  private final CucThueValidate cucThueValidate;
  private final BaseRedisService<String> baseRedisService;
  private final UserService userService;
  @GetMapping("/kiem-tra-ma-so-thue")
  public String show(Model model){
    model.addAttribute("masothueDTO", new MaSoThueDTO());
    return "dangky/dangky_kiemtra";
  }
  @PostMapping("/dang-ki-thong-tin")
  public String checkMathue(@ModelAttribute("masothueDTO") MaSoThueDTO masothueDTO,BindingResult result,Model model){
    model.addAttribute("nguoidongthueDTO",dangKiTaiKhoanService.getNguoiDongThue(masothueDTO.getMasothue()));
    if(dangKiTaiKhoanService.getNguoiDongThue(masothueDTO.getMasothue()) == null){
      model.addAttribute("saimaso", true);
      return "dangky/dangky_kiemtra";
    }
    if(userService.findByUsername(masothueDTO.getMasothue()) != null){
      model.addAttribute("tontai", true);
      return "dangky/dangky_kiemtra";
    }
    return "dangky/dangky_thongtin";
  }
  @PostMapping("/xac-thuc")
  public String xacthuc(@Valid @ModelAttribute("nguoidongthueDTO")NguoiDongThueDTO nguoiDongThueDTO, BindingResult result, Model model){
    if(cucThueValidate.thongtinCanhan(nguoiDongThueDTO) == false){
      model.addAttribute("error", true);
      return "dangky/dangky_thongtin";
    }
    if (result.hasErrors()) {
      model.addAttribute("errors", result.getAllErrors());
      System.out.println("sai số điện thoại");
      return "dangky/dangky_thongtin";
    }
    model.addAttribute("nguoidongthueDTO", nguoiDongThueDTO);
    dangKiTaiKhoanService.sendOTP(nguoiDongThueDTO.getEmail());
    return "dangky/dangky_xacthuc";
  }
  @PostMapping("/kiem-tra")
  public String kiemtra(@Valid @ModelAttribute("nguoidongthueDTO")NguoiDongThueDTO nguoiDongThueDTO, BindingResult result, Model model){
    if(baseRedisService.get(nguoiDongThueDTO.getEmail()) == null){
      model.addAttribute("hethan",true);
      return "dangky/dangky_xacthuc";
    }
    if(baseRedisService.get(nguoiDongThueDTO.getEmail()).equals(nguoiDongThueDTO.getOTP()) == false){
      model.addAttribute("saima",true);
      return "dangky/dangky_xacthuc";
    }
    baseRedisService.delete(nguoiDongThueDTO.getEmail());
    userService.saveUser(nguoiDongThueDTO);
    return "dangky/dangky_thanhcong";
  }
}
