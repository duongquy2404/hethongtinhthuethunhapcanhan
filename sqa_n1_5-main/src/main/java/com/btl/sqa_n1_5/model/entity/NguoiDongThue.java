package com.btl.sqa_n1_5.model.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Entity
@Data
public class NguoiDongThue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer ThanhvienId;
  private String masothue;
  private String coquanthue;
  private String hovaten;
  private Date ngaysinh;
  private String dienthoai;
  private String email;
  private String gioitinh;
  private String quoctich;
  private String diachiThuongtru;
  private String diachiHientai;
  private Integer CCCDId;
  private Integer taxStatus;
  private Integer thongtincobanId;
  private Integer thongtinthueId;
}
