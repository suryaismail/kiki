package com.si.kiki.offer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.si.kiki.CourierApp;

// Reads offer from file in resource
public class OfferService {
  
  private static final Logger logger = LoggerFactory.getLogger(CourierApp.class);

  private static Offers offers = null;

  // Serializes offers from a json file in the jar resources
  public OfferService() {
    super();
    try {
      offers = readOffersFromResource("offerConfig.json");
    } catch (IOException e) {
      logger.error("Failed to read configured offer codes", offers);
    }
  }

  Offers readOffersFromResource(String resource)
      throws IOException {
    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfSubType("com.si.kiki.offer")
        .allowIfSubType("java.util.HashMap")
        .build();
    ObjectMapper mapper = new ObjectMapper();
    mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

    String jsonString = null;
    try (InputStream in = getClass().getResourceAsStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      jsonString = new BufferedReader(new InputStreamReader(in,
          StandardCharsets.UTF_8)).lines().collect(Collectors.joining(" "));
    }

    return mapper.readValue(jsonString, Offers.class);
  }
  
  public double calculateDiscount(String code, int weight, int distance, int cost) {
    if (offers == null) {
      return 0;
    }
    return offers.calculateDiscount(code, weight, distance, cost);
  }

}


