package com.btl.sqa_n1_5.controller;

import com.btl.sqa_n1_5.config.ThanhToanConfig;
import com.btl.sqa_n1_5.model.entity.HoaDon;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.service.ThanhToanService;
import com.btl.sqa_n1_5.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/thanh-toan")
@AllArgsConstructor
@Slf4j
public class ThanhToanController {
  private final ThanhToanService thanhToanService;
  private final UserService userService;
  @RequestMapping("/vnpay")
  public RedirectView thanhtoan(@ModelAttribute("thongtinthue")ThongTinThue thongTinThue) throws UnsupportedEncodingException {
    // fix cứng hóa đơn
    // hóc chỗ date và chuyển date sang sqldate :) này nên vứt vào 1 hàm DateUtils
//    HoaDon hoaDon = HoaDon.builder().
//        id(null).
//        TNCT(thongTinThue.getTNCT()).
//        TNTT(thongTinThue.getSotienTinhthue()).
//        thuedanop(thongTinThue.getThueCanNop()).
//        build();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Thanhvien user = userService.findByUsername(authentication.getName());
    log.info("user id {}", user.getId());
    HoaDon hoaDon = new HoaDon(null,user.getId(), thongTinThue.getTNCT(), thongTinThue.getSotienTinhthue(), thongTinThue.getThueCanNop());
    String vnp_Version = ThanhToanConfig.vnp_Version;
    String vnp_Command = ThanhToanConfig.vnp_Command;
    String orderType = ThanhToanConfig.orderType;
    long amount = thongTinThue.getThueCanNop()*100;
//    double amount = hoaDonDTO.getTNTT()*100;
    String vnp_TxnRef = ThanhToanConfig.getRandomNumber(thongTinThue.getId());
    thanhToanService.set(vnp_TxnRef,hoaDon.toString());
    String vnp_IpAddr = ThanhToanConfig.vnp_IpAddr;
    String vnp_TmnCode = ThanhToanConfig.vnp_TmnCode;
    ModelMapper modelMapper = new ModelMapper();
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");

    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Thanh toán hóa đơn thuế:" + vnp_TxnRef);
    vnp_Params.put("vnp_OrderType", orderType);

    String locate = "";
    if (locate != null && !locate.isEmpty()) {
      vnp_Params.put("vnp_Locale", locate);
    } else {
      vnp_Params.put("vnp_Locale", "vn");
    }
    vnp_Params.put("vnp_ReturnUrl", ThanhToanConfig.vnp_Returnurl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = ThanhToanConfig.hmacSHA512(ThanhToanConfig.vnp_HashSecret, hashData.toString());
    System.out.println("hashdata is: "+ hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = ThanhToanConfig.vnp_PayUrl + "?" + queryUrl;
    System.out.println(paymentUrl);
    return new RedirectView(paymentUrl);
  }
  @RequestMapping("/luu-thanh-toan")
  public RedirectView luuThanhtoan(HttpServletRequest request, RedirectAttributes attributes)
      throws IOException {
    Map<String, String[]> params = request.getParameterMap();
    HashMap<String, String> paramMap = new HashMap<>();
    Set<String> keySets = params.keySet();
    for (String key : keySets) {
      String[] values = params.get(key);
      paramMap.put(key, values[0]);
    }
    String vnp_SecureHash = paramMap.get("vnp_SecureHash");
    paramMap.remove("vnp_SecureHash");
    List fieldNames = new ArrayList(paramMap.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) paramMap.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }

    if(ThanhToanConfig.hmacSHA512(ThanhToanConfig.vnp_HashSecret,hashData.toString()).equals(vnp_SecureHash)==false){
      attributes.addAttribute("paystatus", "99");
      return new RedirectView("/quyet-toan-thue");
    }
    log.info("id hóa đơn is: {}", paramMap.get("vnp_TxnRef"));
    String id = paramMap.get("vnp_TxnRef");
    log.info("hoa don from cache is: {}", thanhToanService.get(id));
    String obj = (String) thanhToanService.get(id);
    // Parse the fields from the string
    HoaDon hoaDon = new HoaDon();
    String[] fields = obj.replaceAll("HoaDon\\(", "").replaceAll("\\)", "").split(", ");
    for (String field : fields) {
      String[] parts = field.split("=");
      switch (parts[0]) {
        case "id":
          hoaDon.setId(null);
          break;
        case "nguoidongthueId":
//          hoaDon.setNguoidongthueId(Integer.valueOf(parts[1]));
          hoaDon.setNguoidongthueId(Integer.parseInt(parts[1]));
          break;
        case "TNCT":
          hoaDon.setTNCT((long) Integer.parseInt(parts[1]));
          break;
        case "TNTT":
          hoaDon.setTNTT((long) Integer.parseInt(parts[1]));
          break;
        case "thuedanop":
          hoaDon.setThuedanop((long) Integer.parseInt(parts[1]));
          break;
      }
    }
    thanhToanService.saveHoaDon(hoaDon);
    attributes.addAttribute("paystatus", "00");
    return new RedirectView("/quyet-toan-thue");
    // thay vì trả về trang mới thì trả veef trang checkout của Dương luôn
  }

}
