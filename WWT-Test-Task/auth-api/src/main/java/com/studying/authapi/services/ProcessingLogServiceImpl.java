package com.studying.authapi.services;

import com.studying.authapi.models.ProcessingLog;
import com.studying.authapi.models.User;
import com.studying.authapi.repositories.ProcessingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProcessingLogServiceImpl implements ProcessingLogService {

  private final ProcessingLogRepository processingLogRepository;
  private final UserDetailsServiceImpl userDetailsService;

  @Autowired
  public ProcessingLogServiceImpl(ProcessingLogRepository processingLogRepository, UserDetailsServiceImpl userDetailsService) {
    this.processingLogRepository = processingLogRepository;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void save(String inputText, String outputText) {
    ProcessingLog log = new ProcessingLog();
    log.setInputText(inputText);
    log.setOutputText(outputText);
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.setUser(user);
    processingLogRepository.save(log);
  }
}
