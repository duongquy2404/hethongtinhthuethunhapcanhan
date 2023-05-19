package com.btl.sqa_n1_5;

import com.btl.sqa_n1_5.controller.AuthenticationController;
import com.btl.sqa_n1_5.model.dto.NguoiDongThueDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthenticationControllerTest {

  @Mock
  private Model mockModel;
  @Mock
  private Authentication mockAuthentication;
  @Test
  public void testShowDangNhap() {
    openMocks(this);
    AuthenticationController authenticationController = new AuthenticationController();

    String result = authenticationController.showDangNhap(mockModel);

    assertEquals("dangnhap", result);
    verify(mockModel).addAttribute("masothueDTO", new NguoiDongThueDTO());
  }
  @Test
  public void testShowIndex() {
    openMocks(this);
    AuthenticationController authenticationController = new AuthenticationController();

    when(mockAuthentication.getName()).thenReturn("anonymousUser");
    SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

    String result = authenticationController.showIndex();

    assertEquals("index", result);
  }

  @Test
  public void testShowDefault() {
    openMocks(this);
    AuthenticationController authenticationController = new AuthenticationController();

    when(mockAuthentication.getName()).thenReturn("anonymousUser");
    SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

    String result = authenticationController.showDefault();

    assertEquals("index", result);
  }

  @Test
  public void testShowHome() {
    openMocks(this);
    AuthenticationController authenticationController = new AuthenticationController();

    String result = authenticationController.showHome();

    assertEquals("home", result);
  }
}

