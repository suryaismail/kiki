package com.si.kiki.utils;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.si.kiki.model.*;

public class PkgReader {

  private static final Logger logger = LoggerFactory.getLogger(PkgReader.class);

  public PkgReader() {
    super();
  }

  public static List<Pkg> readPackages(List<String> inputLines) {
    int noOfPackages = 0;
    try {
      // Parse the first line
      String [] vals = inputLines.get(0).split(" ");
      noOfPackages = Integer.parseInt(vals[1]);
    } catch (Exception e) {
      logger.error("Input header line parsing failed", e);
      return null;
    }

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
}
