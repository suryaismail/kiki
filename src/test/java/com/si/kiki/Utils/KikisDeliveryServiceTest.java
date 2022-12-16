package com.si.kiki.Utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.si.kiki.utils.KikisDeliveryService;

public class KikisDeliveryServiceTest {

  private KikisDeliveryService kiki = new KikisDeliveryService();

  @Test
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
