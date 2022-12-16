package com.si.kiki.Utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.si.kiki.model.Pkg;
import com.si.kiki.model.PkgDeliveryInfo;
import com.si.kiki.utils.DeliveryCostUtil;
import com.si.kiki.utils.PkgDeliveryInfoPrinter;

public class DeliveryCostUtilTest {

  @Test
  void sampleInput_estimateCost () {
    List<Pkg> packages = new ArrayList<Pkg>();
    packages.add(new Pkg("PKG1 5 5 OFR001"));
    packages.add(new Pkg("PKG2 15 5 OFR002"));
    packages.add(new Pkg("PKG3 10 100 OFR003"));

    List<PkgDeliveryInfo> pkgDeliveryInfo =
        DeliveryCostUtil.estimate(100, packages);

    assertEquals("PKG1 0 175", PkgDeliveryInfoPrinter.print(pkgDeliveryInfo.get(0)));
    assertEquals("PKG2 0 275", PkgDeliveryInfoPrinter.print(pkgDeliveryInfo.get(1)));
    assertEquals("PKG3 35 665", PkgDeliveryInfoPrinter.print(pkgDeliveryInfo.get(2)));
  }

}
