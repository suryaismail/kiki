package com.si.kiki.offer;

public class PromoOffer implements Offer {
  private int discount;
  // Assume min and max values are inclusive
  private int minWeight;
  private int maxWeight;
  private int minDistance;
  private int maxDistance;

  public PromoOffer() {
    super();
  }

  public PromoOffer(int discount, int minDistance, int maxDistance,
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

  public int getMinWeight() {
    return minWeight;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public int getMinDistance() {
    return minDistance;
  }

  public int getMaxDistance() {
    return maxDistance;
  }

  @Override
  public double calculateDiscount(String code, int weight, int distance, int cost) {
    if ((distance < minDistance) || (distance > maxDistance)) {
      return 0;
    }
    if ((weight < minWeight) || (weight > maxWeight)) {
      return 0;
    }
    return cost * discount / 100.0;
  }
  
}
