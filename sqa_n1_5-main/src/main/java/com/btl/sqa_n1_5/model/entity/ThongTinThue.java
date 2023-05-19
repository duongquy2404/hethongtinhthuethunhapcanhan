package com.btl.sqa_n1_5.model.entity;

import com.btl.sqa_n1_5.constant.GiamTruConstant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class ThongTinThue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer nguoiDongThueId;
  private Long TNCT;
  private Long TNPSVN;
  private Long TNPSQT;
  private Long DSGT;
  private Long GTCN = GiamTruConstant.GiamTruCaNhan;
  private Long GTPT = GiamTruConstant.GiamTruPhuThuoc;
  private Long TTND;
  private Long GTBH;
  private Long SotienTinhthue;
  private Long ThueCanNop;
  private Long songuoiphuthuoc;

}
