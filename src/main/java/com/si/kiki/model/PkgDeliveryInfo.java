package com.si.kiki.model;

import com.si.kiki.offer.OfferService;

public class PkgDeliveryInfo {
  
  Pkg pkg;
  double discountAmount;
  double discountedCost;
  double deliveryTime;
  
  public PkgDeliveryInfo(Pkg pkg, int baseDeliveryCost) {
    super();
    this.pkg = pkg;
    int totalCost = baseDeliveryCost
        + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
    discountAmount = OfferService.calculateDiscount(pkg.getOfferCode(),
        pkg.getDistance(), pkg.getWeight(), totalCost);
    discountedCost = totalCost - discountAmount;
  }

  public double getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(double deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public Pkg getPkg() {
    return pkg;
  }

  public double getDiscountAmount() {
    return discountAmount;
  }

  public double getDiscountedCost() {
    return discountedCost;
  }

}
