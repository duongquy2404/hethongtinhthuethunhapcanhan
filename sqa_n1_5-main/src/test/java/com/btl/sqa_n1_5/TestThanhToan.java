package com.btl.sqa_n1_5;

import com.btl.sqa_n1_5.config.ThanhToanConfig;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Controller;
// sự khác biệt giữa thanh toán thành công và hủy thanh toán ( hoặc bất cứ lỗi nào phát sinh ) nằm ở vnp_ResponseCode=00(24)(any)
@Controller
public class TestThanhToan {
  public static String hmacSHA512(final String key, final String data) {
    try {

      if (key == null || data == null) {
        throw new NullPointerException();
      }
      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes();
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();

    } catch (Exception ex) {
      return "";
    }
  }
  public static void main(String[] args) throws UnsupportedEncodingException {
    String data = "vnp_Amount=10000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP13978035&vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3A86665900&vnp_PayDate=20230328143120&vnp_ResponseCode=00&vnp_TmnCode=OKVQCIMD&vnp_TransactionNo=13978035&vnp_TransactionStatus=00&vnp_TxnRef=86665900";
    System.out.println(TestThanhToan.hmacSHA512(ThanhToanConfig.vnp_HashSecret,data));
  }

}
