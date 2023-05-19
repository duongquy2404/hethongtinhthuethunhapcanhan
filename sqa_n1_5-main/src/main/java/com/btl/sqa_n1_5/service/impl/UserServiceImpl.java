package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import com.btl.sqa_n1_5.model.entity.NguoiDongThue;
import com.btl.sqa_n1_5.model.entity.Role;
import com.btl.sqa_n1_5.model.entity.Thanhvien;
import com.btl.sqa_n1_5.repository.NguoiDongThueRepository;
import com.btl.sqa_n1_5.repository.RoleRepository;
import com.btl.sqa_n1_5.repository.ThanhVienRepository;
import com.btl.sqa_n1_5.service.EmailService;
import com.btl.sqa_n1_5.service.UserService;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

  private final NguoiDongThueRepository nguoiDongThueRepository;
  private final ThanhVienRepository thanhVienRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;


  @Override
  public void saveUser(NguoiDongThueDTO nguoiDongThueDTO) {
    Thanhvien user = new Thanhvien();
    String pass = randompass();
    user.setId(nguoiDongThueDTO.getThanhvienId());
    user.setPassword(passwordEncoder.encode(pass));
    user.setUsername(nguoiDongThueRepository.findById(user.getId()).get().getMasothue());
    user.setDiachi(nguoiDongThueDTO.getDiachiHientai());
    user.setEmail(nguoiDongThueDTO.getEmail());
    user.setDienthoai(nguoiDongThueDTO.getDienthoai());
    Role role = roleRepository.findRoleByName("ROLE_USER");
    if(role == null){
      role = checkRoleExist();
    }
    user.setRoles(new LinkedList<Role>(Arrays.asList(role)));
    thanhVienRepository.save(user);
    sendOTP(nguoiDongThueDTO.getEmail(),pass);
  }

  @Override
  public Thanhvien findByUsername(String username) {
    return thanhVienRepository.findByUsername(username).isPresent()?
        thanhVienRepository.findByUsername(username).get():
        null;
  }

  @Override
  public List<NguoiDongThueDTO> findAllUsers() {
    return null;
  }

  @Override
  public void saveTaxStatus(Integer nguoidongthueId, Integer status) {
    NguoiDongThue nguoiDongThue = nguoiDongThueRepository.findById(nguoidongthueId).isPresent()?
        nguoiDongThueRepository.findById(nguoidongthueId).get(): null;
    nguoiDongThue.setTaxStatus(status);
    nguoiDongThueRepository.save(nguoiDongThue);
  }

  @Override
  public NguoiDongThue findNguoiDongThueById(Integer id) {
    return nguoiDongThueRepository.findById(id).isPresent()?
        nguoiDongThueRepository.findById(id).get():
        null;
  }

  private Role checkRoleExist() {
    Role role = new Role();
    role.setName("ROLE_USER");
    return roleRepository.save(role);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Thanhvien user = thanhVienRepository.findByUsername(username).get();

    if (user != null) {
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
          user.getPassword(),
          mapRolesToAuthorities(user.getRoles()));
    }else{
      throw new UsernameNotFoundException("Invalid username or password.");
    }
  }
  private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
    Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
    return mapRoles;
  }


  private String forgotPasswordMail(String email, String pass) {
    String content =
        "<p>Chào <b>"
            + email
            + "</b>,</p>"
            + "<p>Bạn đang thực hiện đăng kí tài khoản trên cổng dịch vụ THUECANHAN.VN</p>"
            + "<p>Dưới đây là mã thông tin về mật khẩu cho lần đăng nhập đầu tiên của bạn, vui lòng đổi mật khẩu sau khi đăng nhập thành công : </p>"
            + "<h1>MẬT KHẨU - "
            + pass
            + "</h1>"
            + "<p> <span style=\"color: red\">(*)</span> Lưu ý: Không chia sẻ mật khẩu này cho bất kì ai.</p>"
            + "<p>Nếu có vướng mắc gì thêm, bạn vui lòng liên hệ theo thông tin phía dưới:</p>"
            + "<p>- Hotline: 090 488 6095 - 19001568</p>"
            + "<p>Cảm ơn bạn đã sử dụng dịch vụ của THUECANHAN.VN</p>"
            + "<p>Trân trọng.</p>"
            + "<p>Đội ngũ THUECANHAN.VN</p>";

    return content;
  }
  public String sendOTP(String email, String pass) {
    new Thread(
        new Runnable() {
          @Override
          public void run() {
            emailService.sendEmail(
                email,
                forgotPasswordMail(email, pass),
                "THÔNG TIN MẬT KHẨU CHO LẦN ĐẦU ĐĂNG NHẬP\n");
          }
        })
        .start();
    return "Success";

  }
  private String randompass() {
    String OTP_CHARS = "zxcvbnmasdfghjklqwertyuiop1234567890";
    StringBuilder sb = new StringBuilder(6);
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(OTP_CHARS.length());
      sb.append(OTP_CHARS.charAt(index));
    }
    return sb.toString();
  }
}
