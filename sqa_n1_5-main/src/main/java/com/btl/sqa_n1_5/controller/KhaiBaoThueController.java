package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.model.dto.ThongTinCoBanDTO;
import com.btl.sqa_n1_5.model.entity.NguoiPhuThuoc;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import com.btl.sqa_n1_5.model.entity.ThongTinCoBan;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.service.KhaiBaoThueService;
import com.btl.sqa_n1_5.service.NguoiPhuThuocService;
import com.btl.sqa_n1_5.service.TinhThueService;
import com.btl.sqa_n1_5.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.json.JSONObject;


@Controller
@RequestMapping("/khai-bao-thue")
@AllArgsConstructor
@Slf4j
public class KhaiBaoThueController {
  private final UserService userService;
  private final KhaiBaoThueService khaiBaoThueService;
  private final NguoiPhuThuocService nguoiPhuThuocService;
  private final TinhThueService tinhThueService;
  @GetMapping("/thong-tin-co-ban")
  public String khaiThongTinCoBan(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    ThongTinCoBanDTO thongTinCoBanDTO = khaiBaoThueService.getThongTinCoBan(user.getId());
    log.info("thong tin co ban + {}", thongTinCoBanDTO);
    model.addAttribute("dto", thongTinCoBanDTO);
    return "khaibaothue/khaibao_thongtin";
  }
  @PostMapping("/thong-tin-co-ban")
  public RedirectView luuThongTinCoBan(Model model,@Valid @ModelAttribute("dto") ThongTinCoBanDTO dto){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    dto.setNguoiDongThueId(userService.findByUsername(authentication.getName()).getId());
    // set id thong tin co ban cho
    khaiBaoThueService.save(dto);
    return new RedirectView("/khai-bao-thue/tien-thue");
  }
  @GetMapping("/tien-thue")
  public String khaiBaoThuNhap(Model model){
    model.addAttribute("thunhap", new ThongTinThue());
    return "khaibaothue/khaibao_tienthue";
  }
//  @PostMapping("/tien-thue")
//  public RedirectView luuThongTinThuNhap(Model model, @ModelAttribute("thunhap")ThongTinThuNhap thongTinThuNhap){
//    System.out.println(thongTinThuNhap.toString());
//    return new RedirectView("/quyet-toan-thue");
//  }
  @PostMapping("/tien-thue")
  public RedirectView luuThongTinThuNhap(Model model, @ModelAttribute("thunhap")ThongTinThue thongTinThuNhap){
    System.out.println(thongTinThuNhap.toString());
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    Long songuoiphuthuoc = (long) nguoiPhuThuocService.findAllByNguoiDongThue(user.getId()).size();
    thongTinThuNhap.setSonguoiphuthuoc(songuoiphuthuoc);
    thongTinThuNhap.setNguoiDongThueId(user.getId());
    khaiBaoThueService.luuThongTinThue(tinhThueService.tinhthue(thongTinThuNhap));
    log.info("Thong tin thue :{}", thongTinThuNhap);
    return new RedirectView("/khai-bao-thue/nguoi-phu-thuoc");
  }
  @GetMapping("/nguoi-phu-thuoc")
  public String khaiNguoiPhuThuoc(Model model){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    List<NguoiPhuThuoc> dtoList = nguoiPhuThuocService.findAllByNguoiDongThue(user.getId());
    model.addAttribute("dtoList",dtoList);
    log.info("list nguoi dong thue is: {}", dtoList);
    model.addAttribute("dto", new NguoiPhuThuoc());
    return "khaibaothue/khaibao_nguoiphuthuoc";
  }
  @PostMapping("/nguoi-phu-thuoc")
  public RedirectView luuNguoiPhuThuoc(Model model){
    return new RedirectView("/quyet-toan-thue");
  }
  @PostMapping("/nguoi-phu-thuoc/edit")
  public RedirectView editNguoiPhuThuoc(Model model, @Valid @ModelAttribute("dto") NguoiPhuThuoc nguoiPhuThuoc  ){
    nguoiPhuThuocService.suaNguoiPhuThuoc(nguoiPhuThuoc);
    return new RedirectView("/khai-bao-thue/nguoi-phu-thuoc");
  }
  @PostMapping("/nguoi-phu-thuoc/delete")
  public RedirectView deleteNguoiPhuThuoc(@RequestBody String id){
    JSONObject jsonObject = new JSONObject(id);
    int idi = jsonObject.getInt("id");
    nguoiPhuThuocService.xoaNguoiPhuThuoc(idi);
    return new RedirectView("/khai-bao-thue/nguoi-phu-thuoc");
  }
  @PostMapping("/nguoi-phu-thuoc/add")
  public RedirectView addNguoiPhuThuoc(Model model, @Valid @ModelAttribute("dto") NguoiPhuThuoc nguoiPhuThuoc ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    nguoiPhuThuoc.setNguoidongthueid(user.getId());
    nguoiPhuThuocService.themNguoiPhuThuoc(nguoiPhuThuoc);
    return new RedirectView("/khai-bao-thue/nguoi-phu-thuoc");
  }
}
