package com.si.kiki.offer;

// For testing extensibility and json serialization,
// a generic offer that gives a fixed discount for all packages
public class GenericOffer implements Offer {
  private int discount;

  public GenericOffer() {
    super();
  }

  public GenericOffer(int discount) {
    super();
    this.discount = discount;
  }

  public int getDiscount() {
    return discount;
  }

  @Override
  public double calculateDiscount(String code, int weight, int distance, int cost) {
    return cost * discount / 100.0;
  }
  
}
