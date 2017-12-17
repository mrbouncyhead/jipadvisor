package co.markhoward.jipadvisor.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class WebServiceUtils {
  private static ObjectMapper mapper = new ObjectMapper();

  public static <T> Optional<T> jsonToObject(final String json, final Class<T> expectedClass) {
    try {
      return Optional.of(mapper.readValue(json, expectedClass));
    } catch (IOException exception) {
      log.error("The json could not be converted into a java object", exception);
      return Optional.empty();
    }
  }

  public static Optional<String> ObjectToJson(final Object object) {
    try {
      return Optional.of(mapper.writeValueAsString(object));
    } catch (JsonProcessingException exception) {
      log.error("The object could not be converted to json", exception);
      return Optional.empty();
    }
  }

  public static <T> List<T> jsonToList(final String json, final Class<T> expectedClass) {
    CollectionType collectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, expectedClass);
    try {
      return mapper.readValue(json, collectionType);
    } catch (IOException exception) {
      log.error("The json could not be converted into a java object", exception);
    }

    return new ArrayList<>();
  }

  private static final Logger log = LoggerFactory.getLogger(WebServiceUtils.class);
}
