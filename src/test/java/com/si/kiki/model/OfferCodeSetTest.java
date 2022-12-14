package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class OfferCodeSetTest {

  private OfferCodeSet offerCodeSet = new OfferCodeSet();

  @Test
  void givenInvalidOfferCode_thenReturn0 () {
    assertEquals(0, offerCodeSet.getDiscountPercent("OFR004", 5, 5));
  }

  @Test
  void edgeValues () {

    assertEquals(5, offerCodeSet.getDiscountPercent("OFR003", 50, 100));
    assertEquals(5, offerCodeSet.getDiscountPercent("OFR003", 250, 100));

    assertEquals(5, offerCodeSet.getDiscountPercent("OFR003", 200, 10));
    assertEquals(5, offerCodeSet.getDiscountPercent("OFR003", 200, 150));
  }

  @Test
  void invalidValues_noDiscount () {
    assertEquals(0, offerCodeSet.getDiscountPercent("OFR003", 49, 100));
    assertEquals(0, offerCodeSet.getDiscountPercent("OFR003", 251, 100));

    assertEquals(0, offerCodeSet.getDiscountPercent("OFR003", 200, 9));
    assertEquals(0, offerCodeSet.getDiscountPercent("OFR003", 200, 151));

  }

}
