package com.si.kiki.model;

public class Pkg {
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

  public double getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(double deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

}
