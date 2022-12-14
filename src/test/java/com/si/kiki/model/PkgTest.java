package com.si.kiki.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class PkgTest {

  private OfferCodeSet offerCodeSet = new OfferCodeSet();

  @Test
  void validValuesWithDecimalValues () {
    assertEquals("PKG2 94.85 1260.15",
        (new Pkg("PKG2 100 51 OFR002")).getOutputLine(offerCodeSet, 100));

    assertEquals("PKG2 95.2 1264.8",
        (new Pkg("PKG2 100 52 OFR002")).getOutputLine(offerCodeSet, 100));
  }

}
