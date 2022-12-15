package com.si.kiki.service;

import java.text.DecimalFormat;
import com.si.kiki.model.Pkg;
import com.si.kiki.offer.OfferService;

public class PkgPrinter {
  private static DecimalFormat df = new DecimalFormat("#.##");

  // Output: pkg_id1 discount1 total_cost1
  public static String getOutputLine(OfferService offerService, Pkg pkg,
      int baseDeliveryCost) {
    int totalCost = baseDeliveryCost
        + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
    double discount = offerService.calculateDiscount(pkg.getOfferCode(),
        pkg.getDistance(), pkg.getWeight(), totalCost);

    return pkg.getPkgId() + " "
      + df.format(discount) + " " + df.format(totalCost - discount);
  }
  
  public static String getOutputLineWithDeliveryTime(OfferService offerService, Pkg pkg,
       int baseDeliveryCost) {
    return getOutputLine(offerService,pkg, baseDeliveryCost) + " " 
        + df.format(pkg.getDeliveryTime());
  }
}