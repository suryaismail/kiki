package com.si.kiki.utils;

import java.text.DecimalFormat;
import com.si.kiki.model.PkgDeliveryInfo;

public class PkgDeliveryInfoPrinter {
  private static DecimalFormat df = new DecimalFormat("#.##");

  // Output: pkg_id1 discount1 total_cost1
  public static String print(PkgDeliveryInfo pkg) {
    return pkg.getPkg().getPkgId() + " "
      + df.format(pkg.getDiscountAmount()) + " "
      + df.format(pkg.getDiscountedCost());
  }
  
  public static String printWithDeliveryTime(PkgDeliveryInfo pkg) {
    return print(pkg) + " " +  df.format(pkg.getDeliveryTime());
  }
}