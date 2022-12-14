package com.si.kiki.model;

public class OfferCode {
  private int discount;
  // Assume min and max values are inclusive
  private int minWeight;
  private int maxWeight;
  private int minDistance;
  private int maxDistance;

  public OfferCode(int discount, int minDistance, int maxDistance,
      int minWeight, int maxWeight) {
    super();
    this.discount = discount;
    this.minDistance = minDistance;
    this.maxDistance = maxDistance;

    this.minWeight = minWeight;
    this.maxWeight = maxWeight;
  }

  public int getDiscount() {
    return discount;
  }

  public int getMinDistance() {
    return minDistance;
  }

  public int getMaxDistance() {
    return maxDistance;
  }
  
  public int getMinWeight() {
    return minWeight;
  }

  public int getMaxWeight() {
    return maxWeight;
  }
  
}
