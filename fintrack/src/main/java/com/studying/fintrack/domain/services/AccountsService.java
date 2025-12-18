package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.models.AccountDTO;
import com.studying.fintrack.domain.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

  private AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public Account createAccount(AccountDTO dto) {
    Account account = new Account();
    account.setName(dto.getName());
    account.setCurrency(dto.getCurrency());
    return accountsRepository.save(account);
  }

  public Account getAccountById(Integer id) {
    Account account = accountsRepository.findById(id).orElse(null);
    if (account == null) {
      throw new NullPointerException("Account not found in DB!");
    }
    return account;
  }

  public Account updateAccount(Account account) {
    return accountsRepository.save(account);
  }

  public void deleteAccount(Account account) {
    accountsRepository.delete(account);
  }

  public Iterable<Account> getAllAccounts() {
    return accountsRepository.findAll();
  }
}
