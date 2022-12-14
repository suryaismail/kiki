package com.si.kiki.model;

import java.text.DecimalFormat;

public class Pkg {
  DecimalFormat df = new DecimalFormat("#.##");

  String pkgId;
  int weight;
  int distance;
  String offerCode;
  double deliveryTime;

  public Pkg(String pkgId, int weight, int distance, String offerCode) {
    super();
    this.pkgId = pkgId;
    this.weight = weight;
    this.distance = distance;
    this.offerCode = offerCode;
  }

  // Input: pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
  public Pkg(String line) {
    super();
    String [] vals = line.split(" ");
    pkgId = vals[0];
    weight = Integer.parseInt(vals[1]);
    distance = Integer.parseInt(vals[2]);
    offerCode = vals[3];
  }

  public Pkg() {
    pkgId = "";
    weight = 0;
    distance = 0;
    offerCode = "";
  }

  public String getPkgId() {
    return pkgId;
  }

  public int getWeight() {
    return weight;
  }

  public int getDistance() {
    return distance;
  }

  public String getOfferCode() {
    return offerCode;
  }

  // Output: pkg_id1 discount1 total_cost1
  public String getOutputLine(OfferCodeSet offerCodeSet, int baseDeliveryCost) {
    int totalCost = baseDeliveryCost + (weight * 10) + (distance * 5);
    double discount = totalCost * offerCodeSet.getDiscountPercent(offerCode, distance, weight)
        / 100.0;
    
    return pkgId + " " + 
      df.format(discount) + " " + df.format(totalCost - discount);
  }
  
  public String getOutputLineWithDeliveryTime(
      OfferCodeSet offerCodeSet, int baseDeliveryCost) {
    int totalCost = baseDeliveryCost + (weight * 10) + (distance * 5);
    double discount = totalCost * offerCodeSet.getDiscountPercent(offerCode, distance, weight)
        / 100.0;
    
    return pkgId + " "
      + df.format(discount) + " " + df.format(totalCost - discount)
      + " " + df.format(deliveryTime);
  }

  public double getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(double deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

}
