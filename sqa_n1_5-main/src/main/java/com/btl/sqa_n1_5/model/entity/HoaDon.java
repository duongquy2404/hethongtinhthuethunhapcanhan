package com.btl.sqa_n1_5.model.entity;

import com.btl.sqa_n1_5.model.dto.HoaDonDTO;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HoaDon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer nguoidongthueId;
  private Long TNCT;
  private Long TNTT;
  private Long thuedanop;
}
