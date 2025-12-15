package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.models.AccountDTO;
import com.studying.fintrack.domain.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  @Autowired
  private AccountsRepository accountsRepository;

  public Account createAccount(AccountDTO dto) {
    Account account = new Account();
    account.setName(dto.getName());
    account.setCurrency(dto.getCurrency());
    return accountsRepository.save(account);
  }
}
