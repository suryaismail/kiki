package com.si.kiki.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.si.kiki.model.*;

public class KikisDeliveryService {

  private static final Logger logger = LoggerFactory.getLogger(KikisDeliveryService.class);

  public KikisDeliveryService() {
    super();
  }

  public List<String> estimate(List<String> inputLines) {

    // Read the packages
    List<Pkg> packages = null;
    try {
      packages = PkgReader.readPackages(inputLines);
    } catch (Exception e) {
      logger.error("Reading list of packages failed", e);
      return null;
    }

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

    // Work out delivery cost estimates
    List<PkgDeliveryInfo> pkgDeliveryInfoList =
        DeliveryCostUtil.estimate(baseDeliveryCost, packages);
    try {
      // There's no end line
      if (inputLines.size() == (noOfPackages + 1)) {
        // Just return the delivery costs
        return pkgDeliveryInfoList.stream().map(
            p -> PkgDeliveryInfoPrinter.print(p))
          .collect(Collectors.toList());

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
        return pkgDeliveryInfoList.stream().map(
            p -> PkgDeliveryInfoPrinter.print(p))
          .collect(Collectors.toList());
      }
      // Work out the delivery times
      DeliveryTimeUtil.estimate(noVehicles, maxSpeed, maxWeight, pkgDeliveryInfoList);
      return pkgDeliveryInfoList.stream().map(
          p -> PkgDeliveryInfoPrinter.printWithDeliveryTime(p))
        .collect(Collectors.toList());    } catch (Exception e) {
      logger.error("Input header line parsing failed", e);
      return null;
    }
  }
}
