package com.si.kiki.offer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.si.kiki.CourierApp;

// This should be something like an autowired bean in spring
// But spring seems like overkill

// Reads offer from file in resource
public class OfferService {
  
  private static final Logger logger = LoggerFactory.getLogger(CourierApp.class);

  private static Offers offers = null;

  // Serializes offers from a json file in the jar resources
  static Offers readOffersFromResource(String resource)
      throws IOException {
    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfSubType("com.si.kiki.offer")
        .allowIfSubType("java.util.HashMap")
        .build();
    ObjectMapper mapper = new ObjectMapper();
    mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

    String jsonString = null;
    try (InputStream in = OfferService.class.getResourceAsStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      jsonString = new BufferedReader(new InputStreamReader(in,
          StandardCharsets.UTF_8)).lines().collect(Collectors.joining(" "));
    }

    return mapper.readValue(jsonString, Offers.class);
  }
  
  public static double calculateDiscount(String code, int weight, int distance, int cost) {
    if (offers == null) {
      try {
        offers = readOffersFromResource("offerConfig.json");
      } catch (IOException e) {
        offers = new Offers();
        logger.error("Failed to read configured offer codes", offers);
      }
      return 0;
    }
    return offers.calculateDiscount(code, weight, distance, cost);
  }

}


