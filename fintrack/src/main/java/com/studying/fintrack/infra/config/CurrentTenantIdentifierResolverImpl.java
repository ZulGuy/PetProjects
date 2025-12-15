package com.studying.fintrack.infra.config;

import com.studying.fintrack.infra.filters.AppTennantContext;
import java.util.Objects;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenant = AppTennantContext.getCurrentTennant();
    System.out.println("Resolving tenant identifier: " + tenant);
    return Objects.requireNonNullElse(tenant, "public");
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
