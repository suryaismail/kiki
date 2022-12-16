package com.si.kiki.utils;

import java.util.List;
import java.util.stream.Collectors;
import com.si.kiki.model.*;

public class DeliveryCostUtil {

  public DeliveryCostUtil() {
    super();
  }

  // Takes baseDeliveryCost and packages
  // Outputs delivery cost estimates as string array
  public static List<PkgDeliveryInfo> estimate(int baseDeliveryCost, List<Pkg> packages) {
    return packages.stream().map(
        p -> new PkgDeliveryInfo(p, baseDeliveryCost))
      .collect(Collectors.toList());
  }
}
