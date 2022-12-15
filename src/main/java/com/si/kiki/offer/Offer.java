package com.si.kiki.offer;


public interface Offer {
  double calculateDiscount(String code, int weight, int distance, int cost);
}