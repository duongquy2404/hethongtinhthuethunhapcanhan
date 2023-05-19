package com.btl.sqa_n1_5.service.impl;

import com.btl.sqa_n1_5.model.entity.NguoiPhuThuoc;
import com.btl.sqa_n1_5.model.entity.ThongTinThue;
import com.btl.sqa_n1_5.service.NguoiPhuThuocService;
import com.btl.sqa_n1_5.service.TinhThueService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TinhThueServiceImpl implements TinhThueService {
  private final NguoiPhuThuocService nguoiPhuThuocService;
  @Override
  public ThongTinThue tinhthue(ThongTinThue thongTinThue) {
    // get thu nhap chiu thue
    thongTinThue.setTNCT(thongTinThue.getTNPSQT() + thongTinThue.getTNPSVN());
    thongTinThue.setGTPT(thongTinThue.getSonguoiphuthuoc() * 52800000);
    // set ds giam tru
    thongTinThue.setDSGT(thongTinThue.getGTPT() + thongTinThue.getGTBH() + thongTinThue.getGTCN() + thongTinThue.getTTND());

    // set TNTT
    thongTinThue.setSotienTinhthue(thongTinThue.getTNCT() - thongTinThue.getDSGT() <= 0?0:thongTinThue.getTNCT() - thongTinThue.getDSGT());
    // tinh thue

    thongTinThue.setThueCanNop(sotiencandong(thongTinThue));
    return thongTinThue;
  }
  private Long sotiencandong(ThongTinThue thongTinThue){
    Long TNTT = thongTinThue.getSotienTinhthue();
    Long unit = 1000000L;
    if(TNTT <=0){
      return 0L;
    }
    else if(TNTT <= 60*unit){
      return TNTT*5/100 ;
    }else if(TNTT <= 120*unit){
      return TNTT*1/10 - 3*unit;
    }else if(TNTT <= 216*unit){
      return TNTT*15/100 - 9*unit;
    }else if(TNTT <= 384*unit){
      return (long) (TNTT*1/5 - 19.8*unit);
    }else if(TNTT <= 624*unit){
      return TNTT*1/4 - 39*unit;
    }else if(TNTT <= 960*unit){
      return (long) (TNTT*3/10 - 70.2*unit);
    }
    return (long) (TNTT*35/100 - 118.2*unit);
  }
}
