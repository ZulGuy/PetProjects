package com.example.sqlide.controllers;

import com.example.schemacomparison.models.CompareModel;
import com.example.schemacomparison.models.ShowModel;
import com.example.sqlide.models.UserConnection;
import com.example.sqlide.services.DatabaseConnectionsService;
import com.example.sqlide.services.DriversService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
//@RequestMapping("/client")
public class ClientController {

  @PostMapping("/client/editor")
  public String executeQuery(@RequestParam("query") String query,
      @RequestParam("action") String action, Model model, HttpServletRequest request, HttpServletResponse response) {
    RestTemplate restTemplate = new RestTemplate();
    DatabaseConnectionsService dbConnect = (DatabaseConnectionsService) request.getSession().getAttribute("dbConnect");
    UserConnection userConnection = dbConnect.getUserConnection();
    Map<String, String> json = new HashMap<>();
    json.put("query", query);
    json.put("action", action);
    json.put("username", userConnection.getUsername() );
    json.put("password", userConnection.getPassword() );
    json.put("url", userConnection.getUrl() );
    json.put("driver", userConnection.getDriver() );
    HttpEntity<Map<String, String>> entity = new HttpEntity<>(json);
    try {
      String queryResult = restTemplate.postForObject("http://localhost:5050/editor/new", entity,
          String.class);
      model.addAttribute("queryResult", queryResult);
      request.getSession().setAttribute("query", query);
      return "editor";
    }catch (HttpClientErrorException e) {
      return "editorExceptionPage";
    }
  }

  @PostMapping("client/compareSchema")
  public String compareSchemas(@RequestParam("sourceUrl") String sourceUrl, @RequestParam("targetUrl") String targetUrl,
      @RequestParam("sourceUsername") String sourceUsername, @RequestParam("targetUsername") String targetUsername,
      @RequestParam("sourcePassword") String sourcePassword, @RequestParam("targetPassword") String targetPassword,
      @RequestParam("action") String action, @RequestParam("sourceDb") String sourceDb, Model model) {
    UserConnection sourceUserConnection = new UserConnection(sourceUrl, sourceUsername, sourcePassword);
    UserConnection targetUserConnection = new UserConnection(targetUrl, targetUsername, targetPassword);
    sourceUserConnection.setDriver(DriversService.getDriver(sourceDb));
    targetUserConnection.setDriver(DriversService.getDriver(sourceDb));
    RestTemplate restTemplate = new RestTemplate();
    CompareModel compareModel = new CompareModel(action, sourceUserConnection, targetUserConnection);
    HttpEntity<CompareModel> entity = new HttpEntity<>(compareModel);
    model.addAttribute("comparisonResult", restTemplate.postForObject("http://localhost:6060/schema/compare/new", entity, String.class));
    return "schemaComparison";
  }

  @PostMapping("client/showSchema")
  public String showSchema(@RequestParam("url") String url, @RequestParam("username") String username,
      @RequestParam("password") String password, @RequestParam("action") String action,
      @RequestParam("sourceDb") String sourceDb, Model model) {
    UserConnection userConnection = new UserConnection(url, username, password);
    userConnection.setDriver(DriversService.getDriver(sourceDb));
    RestTemplate restTemplate = new RestTemplate();
    ShowModel showModel = new ShowModel(userConnection, action);
    HttpEntity<ShowModel> entity = new HttpEntity<>(showModel);
    model.addAttribute("showResult", restTemplate.postForObject("http://localhost:6060/schema/show/new", entity, String.class));
    return "schema";
  }

}
