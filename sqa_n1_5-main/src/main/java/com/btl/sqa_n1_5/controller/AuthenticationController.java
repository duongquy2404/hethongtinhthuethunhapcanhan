package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
  @GetMapping("/dang-nhap")
  public String showDangNhap(Model model){
    model.addAttribute("masothueDTO", new NguoiDongThueDTO());
    return "dangnhap";
  }
  @GetMapping ("/index")
  public String showIndex(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!authentication.getName().equals("anonymousUser")){
      System.out.println(authentication.getName());
      return "home";

    }
    return "index";
  }
  @GetMapping ("/")
  public String showDefault(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!authentication.getName().equals("anonymousUser")){
      System.out.println(authentication.getName());
      return "home";
    }
    return "index";
  }
  @GetMapping("/home-page")
  public String showHome(){
    System.out.println(".");
    return "home";
  }
}
