package com.example.sqlide.controllers;

import com.example.editor.abstractions.QueriesServiceAbstraction;
import com.example.editor.interfaces.IQueriesService;
import com.example.editor.services.SqlQueriesService;
import com.example.sqlide.interfaces.DatabaseObserver;
import com.example.sqlide.models.UserConnection;
import com.example.sqlide.observables.DatabaseConnectionObservable;
import com.example.sqlide.services.DatabaseConnectionsService;
import com.example.sqlide.services.DriversService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NavigationController {

  @GetMapping("/")
  public String getConnectionPage(Model model){
    model.addAttribute("userConnection", new UserConnection());
    return "connection";
  }

  @PostMapping("/connection")
  public void setConnection(@ModelAttribute("userConnection") UserConnection userConnection, HttpServletRequest request, HttpServletResponse response){
    String db = request.getParameter("db");
    userConnection.setDriver(DriversService.getDriver(db));
    DatabaseObserver dbConnect = DatabaseConnectionsService.getInstance(userConnection);
    DatabaseConnectionObservable observable = new DatabaseConnectionObservable();
    observable.subscribe(dbConnect);
    request.getSession().setAttribute("dbConnect", dbConnect);
    request.getSession().setAttribute("observable", observable);
    try {
      request.getRequestDispatcher("/editor").forward(request,response);
    } catch (ServletException | IOException e) {
      e.printStackTrace();
    }
  }

  @PostMapping("/editor")
  public String getEditor(HttpServletRequest request) {
    IQueriesService implementation = new SqlQueriesService((DatabaseConnectionsService) request.getSession().getAttribute("dbConnect"));
    request.getSession().setAttribute("abstractQueryService",new QueriesServiceAbstraction(implementation));
    return "editor";
  }

  @GetMapping("/editor/exit")
  public String exitEditor(@ModelAttribute("userConnection") UserConnection userConnection, HttpServletRequest request) {
    DatabaseConnectionObservable observable = (DatabaseConnectionObservable) request.getSession().getAttribute("observable");
    observable.setState(false);
    observable.notifyObservers();
    return "connection";
  }

  @GetMapping("/schema/compare")
  public String getComparingPage() {
    return "schemaComparison";
  }

  @GetMapping("/schema/show")
  public String getSchemaPage() {
    return "schema";
  }

  @GetMapping("/back")
  public String schemaBack() {
    return "editor";
  }

}
