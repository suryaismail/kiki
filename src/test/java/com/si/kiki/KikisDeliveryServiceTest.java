package com.si.kiki;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.si.kiki.model.Pkg;

public class KikisDeliveryServiceTest {

  private KikisDeliveryService kiki = new KikisDeliveryService();

  @Test
  @Disabled
  void sampleInput_estimateCost () {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("100 3");
    inputLines.add("PKG1 5 5 OFR001");
    inputLines.add("PKG2 15 5 OFR002");
    inputLines.add("PKG3 10 100 OFR003");

    List<String> outputLines = kiki.estimate(inputLines);

    assertEquals("PKG1 0 175", outputLines.get(0));
    assertEquals("PKG2 0 275", outputLines.get(1));
    assertEquals("PKG3 35 665", outputLines.get(2));
  }

  @Test
  @Disabled
  void packagesOverMaxValue () {
    List<Pkg> pkgs = new ArrayList<Pkg>();
    pkgs.add(new Pkg("PKG1", 55, 10, "OFR001"));
    pkgs.add(new Pkg("PKG2", 21, 10, "OFR001"));

    List<String> outputLines = kiki.estimateDeliveryTimes(0, 0, 0, 20, pkgs);
    assertEquals("PKG1 0 620 0", outputLines.get(0));
    assertEquals("PKG2 0 280 0", outputLines.get(1));
  }

  @Test
  @Disabled
  void selfTest_estimateDeliveryTime () {
    List<Pkg> pkgs = new ArrayList<Pkg>();
    pkgs.add(new Pkg("PKG1", 55, 10, "OFR005"));
    pkgs.add(new Pkg("PKG2", 15, 15, "OFR005"));
    pkgs.add(new Pkg("PKG3", 60, 10, "OFR005"));
    pkgs.add(new Pkg("PKG4", 70, 10, "OFR005"));
    pkgs.add(new Pkg("PKG5", 50, 10, "OFR005"));
    pkgs.add(new Pkg("PKG6", 15, 10, "OFR005"));
    pkgs.add(new Pkg("PKG7", 25, 10, "OFR005"));
    pkgs.add(new Pkg("PKG8", 60, 15, "OFR005"));

    List<String> outputLines = kiki.estimateDeliveryTimes(0, 1, 10, 100,
        pkgs);
    // First delivery, starting at 1:00 PKG3 PKG7 PKG6
    // Second delivery, starting at 2:00 PKG4 PKG2
    // Third delivery, starting at 5:00 PKG8
    // Fourth delivery, starting at 8:00 PKG1
    // Fifth delivery, starting at 10:00 PKG5
    assertEquals("PKG1 0 700 9", outputLines.get(0));
    assertEquals("PKG2 0 325 3.5", outputLines.get(1));
    assertEquals("PKG3 0 750 1", outputLines.get(2));
    assertEquals("PKG4 0 850 3", outputLines.get(3));
    assertEquals("PKG5 0 650 11", outputLines.get(4));
    assertEquals("PKG6 0 300 1", outputLines.get(5));
    assertEquals("PKG7 0 400 1", outputLines.get(6));
    assertEquals("PKG8 0 775 6.5", outputLines.get(7));
  }
  
  @Test
  void sampleInput_estimateDeliveryTime () {
    List<String> inputLines = new ArrayList<String>();
    
    inputLines.add("100 5");
    inputLines.add("PKG1 50 30 OFR001");
    inputLines.add("PKG2 75 125 OFR008");
    inputLines.add("PKG3 175 100 OFR003");
    inputLines.add("PKG4 110 60 OFR002");
    inputLines.add("PKG5 155 95 NA");
    inputLines.add("2 70 200");

    List<String> outputLines = kiki.estimate(inputLines);

    assertEquals("PKG1 0 750 3.98", outputLines.get(0));
    assertEquals("PKG2 0 1475 1.78", outputLines.get(1));
    assertEquals("PKG3 0 2350 1.42", outputLines.get(2));
    assertEquals("PKG4 105 1395 0.85", outputLines.get(3));
    assertEquals("PKG5 0 2125 4.19", outputLines.get(4));
  }
}
