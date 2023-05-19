package com.btl.sqa_n1_5.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ThongTinCoBan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer nguoiDongThueId;
  private String hovaten;
  private String diachi;
  private String dienthoai;
  private String email;
  private String masothue;
  private String cucthue;
  private String chicucthue;
  private Integer namKeKhai;
  private Integer thangBatDau;
  private Integer thangKetThuc;
  private Integer loaiToKhai; // 1 nếu là chính thức, 2 nếu là bổ sung
}
