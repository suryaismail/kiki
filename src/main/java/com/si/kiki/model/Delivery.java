package com.si.kiki.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Delivery {
  List<PkgDeliveryInfo> packageList;

  public Delivery() {
    super();
    packageList = new ArrayList<PkgDeliveryInfo>();
  }

  public Delivery(List<PkgDeliveryInfo> packageList) {
    super();
    this.packageList = packageList;
  }

  public List<PkgDeliveryInfo> getPackageList() {
    return packageList;
  }

  public void add(PkgDeliveryInfo pkg) {
    packageList.add(pkg);
  }
  
  public int size() {
    return packageList.size();
  }

  public int getTotalWeight() {
    return packageList.stream().reduce(0,
        (subtotal, element) -> subtotal + element.getPkg().getWeight(), Integer::sum);
  }

  @Override
  public String toString() {
    return "Delivery [packageList=" + 
        packageList.stream().reduce("",
            (partialStr, pkg) -> partialStr + " " +
                pkg.getPkg().getPkgId() + ":" + pkg.getPkg().getWeight(), String::concat)
                + ", totalWeight=" + getTotalWeight()
                + ", maxDistance =" + getMaxDistance() +"]";
  }

  public int getMaxDistance() {
    return packageList.stream().reduce(0,
        (submax, element) -> Math.max(submax, element.getPkg().getDistance()), Integer::max); 
  }

  public double getDeliveryDuration (double maxSpeed) {
    BigDecimal value = new BigDecimal(getMaxDistance() / maxSpeed);       
    return value.setScale(2, RoundingMode.DOWN).doubleValue() * 2;
  }
  
  public void calculatePackageDeliveryTime(double maxSpeed, double startTime) {
    for (PkgDeliveryInfo p : packageList) {
      BigDecimal value = new BigDecimal(startTime + (p.getPkg().getDistance() / maxSpeed));       
      p.setDeliveryTime(value.setScale(2, RoundingMode.DOWN).doubleValue());
    }
  }
}
