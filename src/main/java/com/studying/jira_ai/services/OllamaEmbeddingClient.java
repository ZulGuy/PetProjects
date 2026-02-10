package com.studying.jira_ai.services;

import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaEmbeddingClient {

  private final RestTemplate restTemplate = new RestTemplate();
  private final String baseUrl = "http://localhost:11434";
  private final String modelName = "qwen3-embedding"; // заміни, якщо хочеш іншу embedding-модель

  public float[] embed(String text) {
    String url = baseUrl + "/api/embed";

    Map<String, Object> body = Map.of(
        "model", modelName,
        "input", text
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

    Object raw = response.getBody().get("embeddings");
    if (!(raw instanceof List<?> embeddingsOuter) || embeddingsOuter.isEmpty()) {
      return new float[0];
    }

    Object first = embeddingsOuter.get(0);
    if (!(first instanceof List<?> vectorRaw)) {
      return new float[0];
    }

    float[] result = new float[vectorRaw.size()];
    for (int i = 0; i < vectorRaw.size(); i++) {
      Object n = vectorRaw.get(i);
      if (n instanceof Number num) {
        result[i] = num.floatValue();
      } else {
        // на всякий — якщо раптом прийде щось дивне
        result[i] = 0f;
      }
    }
    return result;
  }

}
