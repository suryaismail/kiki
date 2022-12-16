package com.si.kiki;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.si.kiki.utils.KikisDeliveryService;

public class CourierApp {

  private static final Logger logger = LoggerFactory.getLogger(CourierApp.class);

  public static void main( String[] args ) {
    // Not too fond of scanning input but oh well, put it in a try catch
    int noOfPackages;
    List<String> inputLines = new ArrayList<String>();
    try (Scanner input = new Scanner(System.in)){
      try {
        inputLines.add(input.nextLine());
        String [] vals = inputLines.get(0).split(" ");
        noOfPackages = Integer.parseInt(vals[1]);
        } catch (Exception e) {
        logger.error("Input header line parsing failed", e);
        return;
      }

      for (int i = 0; i < noOfPackages; i++) {
        inputLines.add(input.nextLine());
      }

      // Distance information
      try {
        String lastLine = input.nextLine();
        if ((lastLine != null) && (lastLine.length() > 0)) {
          inputLines.add(lastLine);
        }
      } catch (Exception e) { // Ignore
        logger.debug("No last line, probably reading from file");
      }
      KikisDeliveryService service = new KikisDeliveryService();
      for (String outLine : service.estimate(inputLines)) {
        System.out.println(outLine);
      }

      // Ok print output
    } catch (Exception e) {
      logger.error("Scanner failed", e);
    }
  }

}
