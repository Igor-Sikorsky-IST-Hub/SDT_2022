package org.example.myServer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.FileReader;
import java.io.IOException;

public class Json {

    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static JsonNode parse(String json) throws IOException {
        return objectMapper.readTree(json);
    }

    public static <T> T fromJson(JsonNode node, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.treeToValue(node, tClass);
    }

    public static JsonNode toJson(Object o) {
        return objectMapper.valueToTree(o);
    }

}
