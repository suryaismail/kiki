package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.si.kiki.utils.PkgDeliveryInfoPrinter;

public class PkgTest {

  @Test
  void whenLineIsValid_thenNewPackage () {
    Pkg pkg = new Pkg("PKG2 100 5 OFR005");
    assertEquals("PKG2", pkg.getPkgId());
    assertEquals(100, pkg.getWeight());
    assertEquals(5, pkg.getDistance());
    assertEquals("OFR005", pkg.getOfferCode());
  }

  @Test
  void whenLineIsInValid_thenThrowException () {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      new Pkg("PKG2 100 5 ");
    });
  }

  @Test
  void whenPkgAndBaseCost_thenGetPkgDeliveryInfo () {
    Pkg pkg = new Pkg("PKG2 100 5 OFR002");
    PkgDeliveryInfo deliveryInfo = new PkgDeliveryInfo(pkg, 0);
    assertEquals(0, deliveryInfo.getDiscountAmount());
    assertEquals(1025f, deliveryInfo.getDiscountedCost());
  }
  
  @Test
  void whenPkgWithDiscountAndBaseCost_thenGetPkgDeliveryInfo () {
    Pkg pkg = new Pkg("PKG2 100 50 OFR002");
    PkgDeliveryInfo deliveryInfo = new PkgDeliveryInfo(pkg, 0);
    assertEquals(87.5, deliveryInfo.getDiscountAmount());
    assertEquals(1162.5, deliveryInfo.getDiscountedCost());
  }

  @Test
  void whenCostHasDecimal_theRoundTo2Places () {
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
