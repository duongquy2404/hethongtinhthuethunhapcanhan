package com.btl.sqa_n1_5.model.dto;

import com.btl.sqa_n1_5.model.entity.HoaDon;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HoaDonDTO {
  private Integer id;
  private Integer nguoidongthueId;
  private double TNCT;
  private double TNTT;
  private Date thoigianBatdau;
  private Date thoigianCuoi;

}
