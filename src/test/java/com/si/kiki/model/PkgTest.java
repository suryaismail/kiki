package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.si.kiki.offer.OfferService;
import com.si.kiki.service.PkgPrinter;

public class PkgTest {

  private OfferService offerService = new OfferService();

  @Test
  void validValuesWithDecimalValues () {
    assertEquals("PKG2 94.85 1260.15",
        PkgPrinter.getOutputLine(offerService, new Pkg("PKG2 100 51 OFR002"), 100));

    assertEquals("PKG2 95.2 1264.8",
        PkgPrinter.getOutputLine(offerService, new Pkg("PKG2 100 52 OFR002"), 100));

  }

}
