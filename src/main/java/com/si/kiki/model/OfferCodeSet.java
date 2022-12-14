package com.si.kiki.model;

import java.util.HashMap;
import java.util.Map;

public class OfferCodeSet {

  Map<String, OfferCode> offerCodeMap;

  public OfferCodeSet() {
    super();
    offerCodeMap = new HashMap<String, OfferCode>();
    offerCodeMap.put("OFR001", new OfferCode(10, 0, 200, 70, 200));
    offerCodeMap.put("OFR002", new OfferCode(7, 50, 150, 100, 250));
    offerCodeMap.put("OFR003", new OfferCode(5, 50, 250, 10, 150));
  }

  public int getDiscountPercent(String str, int distance, int weight) {
    OfferCode offerCode = offerCodeMap.get(str);
    if (offerCode == null) {
      return 0;
    }
    if ((distance < offerCode.getMinDistance())
        || (distance > offerCode.getMaxDistance())) {
      return 0;
    }
    if ((weight < offerCode.getMinWeight()) || (weight > offerCode.getMaxWeight())) {
      return 0;
    }
    return offerCode.getDiscount();
  }
  
  
}

