package com.si.kiki.Utils;

import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.si.kiki.model.Pkg;
import com.si.kiki.model.PkgDeliveryInfo;
import com.si.kiki.utils.DeliveryCostUtil;
import com.si.kiki.utils.DeliveryTimeUtil;
import com.si.kiki.utils.PkgDeliveryInfoPrinter;

public class DeliveryTimeUtilTest {
  private static DecimalFormat df = new DecimalFormat("#.##");

  @Test
  void packagesOverMaxWeight () {
    List<Pkg> packages = new ArrayList<Pkg>();
    packages.add(new Pkg("PKG1", 55, 10, "OFR001"));
    packages.add(new Pkg("PKG2", 21, 10, "OFR001"));

    List<PkgDeliveryInfo> pkgDeliveryInfo =
        DeliveryCostUtil.estimate(20, packages);
    
    DeliveryTimeUtil.estimate(1, 10, 20, pkgDeliveryInfo); 
    assertEquals("0", df.format(pkgDeliveryInfo.get(0).getDeliveryTime()));
    assertEquals("0", df.format(pkgDeliveryInfo.get(1).getDeliveryTime()));
  }

  @Test
  void selfTest_estimateDeliveryTime () {
    List<Pkg> packages = new ArrayList<Pkg>();
    packages.add(new Pkg("PKG1", 55, 10, "OFR005"));
    packages.add(new Pkg("PKG2", 15, 15, "OFR005"));
    packages.add(new Pkg("PKG3", 60, 10, "OFR005"));
    packages.add(new Pkg("PKG4", 70, 10, "OFR005"));
    packages.add(new Pkg("PKG5", 50, 10, "OFR005"));
    packages.add(new Pkg("PKG6", 15, 10, "OFR005"));
    packages.add(new Pkg("PKG7", 25, 10, "OFR005"));
    packages.add(new Pkg("PKG8", 60, 15, "OFR005"));

    List<PkgDeliveryInfo> pkgDeliveryInfo =
        DeliveryCostUtil.estimate(20, packages);
    
    DeliveryTimeUtil.estimate(1, 10, 100, pkgDeliveryInfo);
    // First delivery, starting at 1:00 PKG3 PKG7 PKG6
    // Second delivery, starting at 2:00 PKG4 PKG2
    // Third delivery, starting at 5:00 PKG8
    // Fourth delivery, starting at 8:00 PKG1
    // Fifth delivery, starting at 10:00 PKG5
    assertEquals("9", df.format(pkgDeliveryInfo.get(0).getDeliveryTime()));
    assertEquals("3.5", df.format(pkgDeliveryInfo.get(1).getDeliveryTime()));
    assertEquals("1", df.format(pkgDeliveryInfo.get(2).getDeliveryTime()));
    assertEquals("3", df.format(pkgDeliveryInfo.get(3).getDeliveryTime()));
    assertEquals("11", df.format(pkgDeliveryInfo.get(4).getDeliveryTime()));
    assertEquals("1", df.format(pkgDeliveryInfo.get(5).getDeliveryTime()));
    assertEquals("1", df.format(pkgDeliveryInfo.get(6).getDeliveryTime()));
    assertEquals("6.5", df.format(pkgDeliveryInfo.get(7).getDeliveryTime()));
  }

}
