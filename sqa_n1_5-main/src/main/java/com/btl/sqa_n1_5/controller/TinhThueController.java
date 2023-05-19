package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.service.TinhThueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/tinh-thue")
@AllArgsConstructor
public class TinhThueController {
  private final TinhThueService tinhThueService;
  @GetMapping()
  public String show(Model model){
    ThongTinThue thongTinThue = new ThongTinThue();
    model.addAttribute("thongtinthue", thongTinThue);
    return "tinhthue/tinhthue";
  }
  @PostMapping
  public String canculate(@ModelAttribute ThongTinThue thongTinThue, Model model){
    model.addAttribute("thongtinthue", tinhThueService.tinhthue(thongTinThue));
    return "tinhthue/tinhthue";
  }
}
