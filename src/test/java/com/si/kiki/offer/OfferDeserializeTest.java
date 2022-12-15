package com.si.kiki.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class OfferDeserializeTest {
  
  static OfferService offerService = new OfferService();

  // Test deserializing offers of different types
  @Test
  void deserializeOffers () throws IOException {
    Offers offers = offerService.readOffersFromResource("testConfig.json");
    assertNotNull(offers.getOffer("GENERIC_OFFER001"));
    assertEquals(2,
        ((GenericOffer)(offers.getOffer("GENERIC_OFFER001"))).getDiscount());

    assertNotNull(offers.getOffer("OFR001"));
    assertEquals(10,
        ((PromoOffer)(offers.getOffer("OFR001"))).getDiscount());
  }

}
