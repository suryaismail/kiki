package com.si.kiki.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.si.kiki.model.*;
import com.si.kiki.offer.OfferService;

public class KikisDeliveryService {

  private static final Logger logger = LoggerFactory.getLogger(KikisDeliveryService.class);

  private OfferService offers = new OfferService();

  public KikisDeliveryService() {
    super();
  }

  public List<String> estimate(List<String> inputLines) {

    List<Pkg> packages = null;
    int baseDeliveryCost = 0, noOfPackages = 0;
    try {
      // Parse the first line
      String [] vals = inputLines.get(0).split(" ");
      baseDeliveryCost = Integer.parseInt(vals[0]);
      noOfPackages = Integer.parseInt(vals[1]);
    } catch (Exception e) {
      logger.error("Input header line parsing failed", e);
      return null;
    }

    try {
      // Read the packages
      packages = readPackages(inputLines, noOfPackages);
    } catch (Exception e) {
      logger.error("Reading list of packages failed", e);
      return null;
    }

    try {
      // There's no end line
      if (inputLines.size() == (noOfPackages + 1)) {
        // Work out the delivery costs
        return estimateDeliveryCost(baseDeliveryCost, noOfPackages,
            packages);
      }
    } catch (Exception e) {
      logger.error("Estimating delivery cost failed", e);
      return null;
    }

    int noVehicles = 0, maxSpeed = 0, maxWeight = 0;
    try {
      // Parse the ending line
      String [] vals = inputLines.get(inputLines.size() - 1).split(" ");
      noVehicles = Integer.parseInt(vals[0]);
      maxSpeed = Integer.parseInt(vals[1]);
      maxWeight = Integer.parseInt(vals[2]);
    } catch (Exception e) {
      logger.error("Parsing last line failed", e);
      return null;
    }

    try {
      if (noVehicles == 0 || maxSpeed == 0 || maxWeight == 0) {
        logger.error("At least one 0 delivery value, returning estimated costs only, "
            + "noVehicles={} maxSpeed={}, maxWeight={}",
            noVehicles, maxSpeed, maxWeight);
        return estimateDeliveryCost(baseDeliveryCost, noOfPackages,
            packages);
      }
      // Work out the delivery times
      return estimateDeliveryTimes(baseDeliveryCost, noVehicles, maxSpeed, maxWeight,
          packages);
    } catch (Exception e) {
      logger.error("Input header line parsing failed", e);
      return null;
    }
  }

  private List<Pkg> readPackages(List<String> inputLines, int noOfPackages) {
    List<Pkg> packages = new ArrayList<Pkg>();
    for (int i = 0; i < noOfPackages; i++) {
      try {
        packages.add(new Pkg(inputLines.get(i + 1)));
      } catch (Exception e) {
        logger.error("Input line {} failed {}", i, inputLines.get(i + 1), e);
      }
    }
    return packages;
  }

  // Takes input, with header line and delivery info per line
  // Outputs result as string array
  public List<String> estimateDeliveryCost(int baseDeliveryCost, int noOfPackages,
      List<Pkg> packages) {

    return packages.stream().map(
        n -> PkgPrinter.getOutputLine(offers, n, baseDeliveryCost))
        .collect(Collectors.toList());
  }

  public List<String> estimateDeliveryTimes(int baseDeliveryCost,
      int noVehicles, int maxSpeed,
      int maxWeight, List<Pkg> pkgs) {
    // Sort by weight
    List<Pkg> sortedPkgs = new ArrayList<>(pkgs);
    Collections.sort(sortedPkgs, new Comparator<Pkg>() {
      public int compare(Pkg pkg1, Pkg pkg2) {
        // Reverse so we get reverse sort
        return Integer.compare(pkg2.getWeight(), pkg1.getWeight());
      }
    });

    // Brute force approach
    // Sigh, not smart enough to figure out a better way without doing a whole bunch of research
    double [] vehicleTimeAvailable = createDoubleArrayOfSize(noVehicles);
    // Keep going as long as there are packages that are less than maxWeight
    while ((sortedPkgs.size() > 0) &&
        (sortedPkgs.get(sortedPkgs.size() - 1).getWeight() <= maxWeight)) {
   
      int maxNumPkgs = findMaxNumPkgs(maxWeight, sortedPkgs);
      Delivery bestDelivery = getBestCombination(maxWeight, maxNumPkgs, sortedPkgs);

      int nextVehicleIndex = getNextAvailableVehicle(vehicleTimeAvailable);
      bestDelivery.calculatePackageDeliveryTime(maxSpeed,
          vehicleTimeAvailable[nextVehicleIndex]);
      vehicleTimeAvailable[nextVehicleIndex]
          += bestDelivery.getDeliveryDuration(maxSpeed);

      // Remove the delivered packages from the list
      for (Pkg p : bestDelivery.getPackageList()) {
        sortedPkgs.remove(p);
      }
    }

    if ((sortedPkgs.size() > 0)) {
      logger.warn("There are packages with weight more than maxWeight {} : {}",
          maxWeight, sortedPkgs.stream().reduce("",
              (partialStr, pkg) -> partialStr + " " +
                  pkg.getPkgId() + ":" + pkg.getWeight(), String::concat) + "]"
                  );
    }

    return pkgs.stream().map(
        n -> PkgPrinter.getOutputLineWithDeliveryTime(offers, n, baseDeliveryCost))
        .collect(Collectors.toList());
  }

  // Assumes array is sorted in descending order
  // So from the end of the array, figure how many packages we can fit under max weight
  private int findMaxNumPkgs(int maxWeight, List<Pkg> pkgs) {
    int i = pkgs.size(), sum = 0;
    while(i > 0) {
      sum += pkgs.get(--i).getWeight();
      if (sum > maxWeight) {
        return (pkgs.size() - i - 1);
      }
    }
    return pkgs.size();
  }
  
  public void combination(List<Delivery> deliveries, int maxWeight,
      List<Pkg> inputArray, List<Pkg> combinationArray,
      int start, int end, int index, int r) {
    if (index == r) {
      Delivery d = new Delivery(new ArrayList<Pkg>(combinationArray));
      if (d.getTotalWeight() <= maxWeight) {
        deliveries.add(d);
      }
      return;
    }
    for (int i = start; (i <= end) && ((end - i + 1) >= (r - index)); i++) {
      combinationArray.set(index, inputArray.get(i));
      combination(deliveries, maxWeight, inputArray, combinationArray, 
          i + 1, end, index + 1, r);
    }
  }

  private Delivery getBestCombination(int maxWeight, int maxNumPkgs, List<Pkg> pkgs) {
    List<Delivery> currDeliveries = new ArrayList<Delivery>();
    combination(currDeliveries, maxWeight, pkgs,
        createPkgListOfSize(maxNumPkgs),
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
  private int getNextAvailableVehicle(double [] vehicleTimeAvailable) {
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
  
  
  List<Pkg> createPkgListOfSize(int size) {
    List<Pkg> pkgs = new ArrayList<Pkg>();
    for (int i = 0;i < size; i++) {
      pkgs.add(new Pkg());
    }
    return pkgs;
  }
  
  double [] createDoubleArrayOfSize(int size) {
    double [] dbls = new double[size];
    for (int i = 0;i < size; i++) {
      dbls[i] = 0.0;
    }
    return dbls;
  }
}
