package com.si.kiki.offer;

import java.util.HashMap;
import java.util.Map;

public class Offers {
  Map<String, Offer> offers;

  public Offers() {
    super();
    offers = new HashMap<String, Offer>();
  }

  public Offers(Map<String, Offer> offers) {
    super();
    this.offers = offers;
  }

  public Map<String, Offer> getOffers() {
    return offers;
  }

  public void setOffers(Map<String, Offer> offers) {
    this.offers = offers;
  }
  
  public void addOffer(String code, Offer offer) {
    offers.put(code, offer);
  }

  public Object getOffer(String code) {
    return offers.get(code);
  }
  
  public double calculateDiscount(String code, int weight, int distance, int cost) {
    Offer offer = offers.get(code);
    if (offers == null) {
      return 0;
    }
    
    return offer.calculateDiscount(code, weight, distance, cost);
  }
  
}