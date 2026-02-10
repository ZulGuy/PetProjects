package com.studying.jira_ai.services;

import com.studying.jira_ai.rag.KnowledgeDocument;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaClient {

  private final RestTemplate restTemplate;
  private final String baseUrl = "http://localhost:11434";

  public OllamaClient() {
    this.restTemplate = new RestTemplate();
  }

  public String generateAdvice(String jiraText, List<KnowledgeDocument> contextDocs) {
    String url = baseUrl + "/api/chat";

    StringBuilder contextBuilder = new StringBuilder();
    if (contextDocs != null && !contextDocs.isEmpty()) {
      contextBuilder.append("Ось схожі раніше оброблені тікети (фрагменти):\n\n");
      int i = 1;
      for (KnowledgeDocument doc : contextDocs) {
        contextBuilder
            .append("[")
            .append(i++)
            .append("] ")
            .append(doc.getIssueKey())
            .append(" (")
            .append(doc.getChunkType())
            .append(doc.getFilename() != null ? ", file=" + doc.getFilename() : "")
            .append(")\n")
            .append(doc.getText())
            .append("\n\n");
      }
    } else {
      contextBuilder.append("Не знайдено схожих історичних тікетів.\n\n");
    }

    String userContent = """
        %s
        Ось новий тікет із Jira:

        %s

        Завдання:
        - На основі прикладів вище і опису нового тікета запропонуй КОНКРЕТНІ технічні кроки для інженера 1st Line Support.
        - Не пиши шаблонні кроки типу "подякуй", "запитай більше інформації" без конкретики.
        - Формат: нумерований список 1., 2., 3. лише українською мовою.
        """.formatted(contextBuilder.toString(), jiraText);

    Map<String, Object> systemMessage = Map.of(
        "role", "system",
        "content", """
            Ти досвідчений інженер 1-ї лінії підтримки Kernel.
            Використовуй історичні тікети як приклади рішень.
            Відповідь має бути прикладною: конкретні дії, перевірки, налаштування.
            Відповідай українською мовою, у форматі пронумерованих кроків.
            """
    );

    Map<String, Object> userMessage = Map.of("role", "user", "content", userContent);

    Map<String, Object> requestBody = Map.of(
        "model", "llama3",
        "messages", List.of(systemMessage, userMessage),
        "stream", false,
        "options", Map.of("temperature", 0.2)
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<Map> response =
        restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

    Map<String, Object> body = response.getBody();
    if (body == null) return "";

    Map<String, Object> message = (Map<String, Object>) body.get("message");
    if (message == null) return "";

    return (String) message.getOrDefault("content", "");
  }
}
