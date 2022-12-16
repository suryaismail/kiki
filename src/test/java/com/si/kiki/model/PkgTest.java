package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.si.kiki.utils.PkgDeliveryInfoPrinter;

public class PkgTest {

  @Test
  void validValuesWithDecimalValues () {
    PkgDeliveryInfo deliveryInfo = new PkgDeliveryInfo(
        new Pkg("PKG2 100 51 OFR002"), 100);
    assertEquals("PKG2 94.85 1260.15",
        PkgDeliveryInfoPrinter.print(deliveryInfo));

    deliveryInfo = new PkgDeliveryInfo(
        new Pkg("PKG2 100 52 OFR002"), 100);
    assertEquals("PKG2 95.2 1264.8",
        PkgDeliveryInfoPrinter.print(deliveryInfo));
  }

}
