package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.si.kiki.offer.OfferService;

public class OfferServiceTest {

  private OfferService offerService = new OfferService();

  @Test
  void givenInvalidOfferCode_thenReturn0 () {
    assertEquals(0, offerService.calculateDiscount("OFR004", 5, 5, 100));
  }

  @Test
  void edgeValues () {

    assertEquals(5, offerService.calculateDiscount("OFR003", 50, 100, 100));
    assertEquals(5, offerService.calculateDiscount("OFR003", 250, 100, 100));

    assertEquals(5, offerService.calculateDiscount("OFR003", 200, 10, 100));
    assertEquals(5, offerService.calculateDiscount("OFR003", 200, 150, 100));
  }

  @Test
  void invalidValues_noDiscount () {
    assertEquals(0, offerService.calculateDiscount("OFR003", 49, 100, 100));
    assertEquals(0, offerService.calculateDiscount("OFR003", 251, 100, 100));

    assertEquals(0, offerService.calculateDiscount("OFR003", 200, 9, 100));
    assertEquals(0, offerService.calculateDiscount("OFR003", 200, 151, 100));

  }

}
