package com.studying.backendservice.configurations;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

  private static final String DEFAULT_TENANT = "public";


  @Override
  public @UnknownKeyFor @Nullable @Initialized Object resolveCurrentTenantIdentifier() {
    String tenantId = TenantContext.getTenantId();
    System.out.println("Resolver: using tenant " + tenantId);
    return tenantId != null ? tenantId : DEFAULT_TENANT;
  }

  @Override
  public @UnknownKeyFor @NonNull @Initialized boolean validateExistingCurrentSessions() {
    return true;
  }
}
