package com.si.kiki.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delivery {
  List<Pkg> packageList;

  public Delivery() {
    super();
    packageList = new ArrayList<Pkg>();
  }

  public Delivery(List<Pkg> packageList) {
    super();
    this.packageList = packageList;
  }

  public List<Pkg> getPackageList() {
    return packageList;
  }

  public void add(Pkg pkg) {
    packageList.add(pkg);
  }
  
  public int size() {
    return packageList.size();
  }

  public int getTotalWeight() {
    return packageList.stream().reduce(0,
        (subtotal, element) -> subtotal + element.getWeight(), Integer::sum);
  }

  @Override
  public String toString() {
    return "Delivery [packageList=" + 
        packageList.stream().reduce("",
            (partialStr, pkg) -> partialStr + " " +
                pkg.getPkgId() + ":" + pkg.getWeight(), String::concat)
                + ", totalWeight=" + getTotalWeight()
                + ", maxDistance =" + getMaxDistance() +"]";
  }

  public int getMaxDistance() {
    return packageList.stream().reduce(0,
        (submax, element) -> Math.max(submax, element.getDistance()), Integer::max); 
  }

  public double getDeliveryDuration (double maxSpeed) {
    BigDecimal value = new BigDecimal(getMaxDistance() / maxSpeed);       
    return value.setScale(2, RoundingMode.DOWN).doubleValue() * 2;
  }
  
  public void calculatePackageDeliveryTime(double maxSpeed, double startTime) {
    for (Pkg p : packageList) {
      BigDecimal value = new BigDecimal(startTime + (p.getDistance() / maxSpeed));       
      p.setDeliveryTime(value.setScale(2, RoundingMode.DOWN).doubleValue());
    }
  }
}
