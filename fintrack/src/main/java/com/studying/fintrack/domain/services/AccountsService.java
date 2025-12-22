package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.models.AccountDTO;
import com.studying.fintrack.domain.repositories.AccountsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

  public Account getAccountById(int id) {
    Account account = accountsRepository.findById(id).orElse(null);
    if (account == null) {
      throw new EntityNotFoundException("Account not found in DB!");
    }
    return account;
  }

//  public Account getAccountByIdForUser(int id) {
//    ;
//    Account account = accountsRepository.findByIdAndUserId(id, ).orElse(null);
//    if (account == null) {
//      throw new EntityNotFoundException("Account not found in DB!");
//    }
//    return account;
//  }

  public Account updateAccount(int id, AccountDTO dto) {
    Account account = accountsRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Account not found in DB!")
    );
    account.setName(dto.getName());
    account.setCurrency(dto.getCurrency());
    return accountsRepository.save(account);
  }

  public void deleteAccount(int id) {
    Account account = accountsRepository.findById(id).orElse(null);
    if (account == null) {
      throw new EntityNotFoundException("Account not found in DB!");
    }
    accountsRepository.delete(account);
  }

  public List<Account> getAllAccounts() {
    return accountsRepository.findAll();
  }

  public List<Account> getAccountsByUserId(int userId) {
    return accountsRepository.findByUserId(userId).orElseThrow(
        () -> new EntityNotFoundException("Accounts or User author of this accounts not found in DB!")
    );
  }
}
