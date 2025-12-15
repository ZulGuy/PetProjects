package com.studying.fintrack.infra.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class AppTennantContext implements Filter {

  private static final String LOGGER_TENANT_ID = "tenant_id";
  private static final String TENANT_HEADER = "X-Tenant-Id";
  public static final String DEFAULT_TENANT = "public";
  private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

  public static String getCurrentTennant() {
    String tenant = currentTenant.get();
    return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
  }

  public static void setCurrentTennant(String tenant) {
    MDC.put(LOGGER_TENANT_ID, tenant);
    currentTenant.set(tenant);
  }

  public static void clear() {
    MDC.clear();
    currentTenant.remove();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String tenant = req.getHeader(TENANT_HEADER);
    if (tenant != null) {
      setCurrentTennant(tenant);
    }
    chain.doFilter(request, response);
    clear();
  }

}
