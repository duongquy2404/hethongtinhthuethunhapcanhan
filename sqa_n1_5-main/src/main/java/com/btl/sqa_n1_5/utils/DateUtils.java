package com.btl.sqa_n1_5.utils;

import java.text.ParseException;
import java.util.Date;

public class DateUtils {
  public static java.sql.Date from(Date date) throws ParseException {
    return (java.sql.Date)new Date(date.getTime());
  }
}
