package co.markhoward.jipadvisor.services;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebServiceUtils {
  private static ObjectMapper mapper = new ObjectMapper ();
  
  public static <T> Optional<T> jsonToObject (final String json, final Class<T> expectedClass) {
    try {
      return Optional.of(mapper.readValue(json, expectedClass));
    } catch (IOException exception) {
      log.error("The json could not be converted into a java object", exception);
      return Optional.empty();
    }
  }
  
  private static final Logger log = LoggerFactory.getLogger(WebServiceUtils.class);
}
