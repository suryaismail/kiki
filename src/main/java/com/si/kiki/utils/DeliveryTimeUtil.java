package com.si.kiki.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.si.kiki.model.*;

/*
 * Working out the packages in each delivery
 * - sort packages by weight
 * 
 * - work out max possible number of packages
 *   - count how many of the lightest packages can fit in one delivery (n)
 * - Find all combinations of n packages with weight less than or equal to max weight
 * - Sort the combinations by weight and distance (delivery time)
 * - Repeat until there are no more packages,
 *   or all remaining packages are over the max weight
 */
public class DeliveryTimeUtil {

  private static final Logger logger = LoggerFactory.getLogger(DeliveryTimeUtil.class);

  public DeliveryTimeUtil() {
    super();
  }

  // Takes noVehicles, maxSpeed, maxWeight and packages
  // Populates the deliveryTime field of Pkg
  public static void estimate(int noVehicles, int maxSpeed,
      int maxWeight, List<PkgDeliveryInfo> pkgs) {
    // Sort by weight
    List<PkgDeliveryInfo> sortedPkgs = new ArrayList<>(pkgs);
    Collections.sort(sortedPkgs, new Comparator<PkgDeliveryInfo>() {
      public int compare(PkgDeliveryInfo pkg1, PkgDeliveryInfo pkg2) {
        // Reverse so we get reverse sort
        return Integer.compare(pkg2.getPkg().getWeight(), pkg1.getPkg().getWeight());
      }
    });

    // Brute force approach
    // Sigh, not smart enough to figure out a better way without doing a whole bunch of research
    double [] vehicleTimeAvailable = createDoubleArrayOfSize(noVehicles);
    // Keep going as long as there are packages that are less than maxWeight
    while ((sortedPkgs.size() > 0) &&
        (sortedPkgs.get(sortedPkgs.size() - 1).getPkg().getWeight() <= maxWeight)) {
   
      int maxNumPkgs = findMaxNumPkgs(maxWeight, sortedPkgs);
      Delivery bestDelivery = getBestCombination(maxWeight, maxNumPkgs, sortedPkgs);

      int nextVehicleIndex = getNextAvailableVehicle(vehicleTimeAvailable);
      bestDelivery.calculatePackageDeliveryTime(maxSpeed,
          vehicleTimeAvailable[nextVehicleIndex]);
      vehicleTimeAvailable[nextVehicleIndex]
          += bestDelivery.getDeliveryDuration(maxSpeed);

      // Remove the delivered packages from the list
      for (PkgDeliveryInfo p : bestDelivery.getPackageList()) {
        sortedPkgs.remove(p);
      }
    }

    if ((sortedPkgs.size() > 0)) {
      logger.warn("There are packages with weight more than maxWeight {} : {}",
          maxWeight, sortedPkgs.stream().reduce("",
              (partialStr, pkg) -> partialStr + " " +
                  pkg.getPkg().getPkgId() + ":" + pkg.getPkg().getWeight(), String::concat) + "]"
                  );
    }
  }

  // Assumes array is sorted in descending order
  // So from the end of the array, figure how many packages we can fit under max weight
  private static int findMaxNumPkgs(int maxWeight, List<PkgDeliveryInfo> pkgs) {
    int i = pkgs.size(), sum = 0;
    while(i > 0) {
      sum += pkgs.get(--i).getPkg().getWeight();
      if (sum > maxWeight) {
        return (pkgs.size() - i - 1);
      }
    }
    return pkgs.size();
  }
  
  public static void combination(List<Delivery> deliveries, int maxWeight,
      List<PkgDeliveryInfo> inputArray, PkgDeliveryInfo[] combinationArray,
      int start, int end, int index, int r) {
    if (index == r) {
      Delivery d = new Delivery(
          new ArrayList<PkgDeliveryInfo>( Arrays.asList(combinationArray)));
      if (d.getTotalWeight() <= maxWeight) {
        deliveries.add(d);
      }
      return;
    }
    for (int i = start; (i <= end) && ((end - i + 1) >= (r - index)); i++) {
      combinationArray[index] = inputArray.get(i);
      combination(deliveries, maxWeight, inputArray, combinationArray, 
          i + 1, end, index + 1, r);
    }
  }

  private static Delivery getBestCombination(int maxWeight, int maxNumPkgs,
      List<PkgDeliveryInfo> pkgs) {
    List<Delivery> currDeliveries = new ArrayList<Delivery>();
    combination(currDeliveries, maxWeight, pkgs, new PkgDeliveryInfo[maxNumPkgs],
        0, pkgs.size() - 1, 0, maxNumPkgs);
    
    Collections.sort(currDeliveries, new Comparator<Delivery>() {
      public int compare(Delivery d1, Delivery d2) {
        int dCompare = Integer.compare(d2.getTotalWeight(), d1.getTotalWeight());
        if (dCompare == 0) {
          return Integer.compare(d1.getMaxDistance(), d2.getMaxDistance());
        }
        return dCompare;
      }
    });
    return currDeliveries.get(0);
  }

  // Gets the index of the next available vehicle
  private static int getNextAvailableVehicle(double [] vehicleTimeAvailable) {
    double min = Double.MAX_VALUE;
    int minIndex = 0;
    for (int i = 0; i < vehicleTimeAvailable.length; i++) {
      if (Double.compare(vehicleTimeAvailable[i], min) < 0) {
        min = vehicleTimeAvailable[i];
        minIndex = i;
      }
    }
    return minIndex;
  }
  
  static double [] createDoubleArrayOfSize(int size) {
    double [] dbls = new double[size];
    for (int i = 0;i < size; i++) {
      dbls[i] = 0.0;
    }
    return dbls;
  }
}
