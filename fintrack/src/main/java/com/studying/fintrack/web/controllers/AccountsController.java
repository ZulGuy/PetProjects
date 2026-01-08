package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.models.AccountDTO;
import com.studying.fintrack.domain.services.AccountsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

  AccountsService accountsService;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @GetMapping
  public List<Account> getAllAccounts() {
    return accountsService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public Account getAccountById(@PathVariable int id) {
    return accountsService.getAccountById(id);
  }

  @GetMapping("/for-user/{id}")
  public Account getAccountByIdForUser(@PathVariable int id) {
    return accountsService.getAccountById(id);
  }

  @GetMapping("/users/{id}")
  public List<Account> getAccountsByUserId(@PathVariable int id) {
    return accountsService.getAccountsByUserId(id);
  }

  @PostMapping
  public Account createAccount(@RequestBody AccountDTO dto) {
    return accountsService.createAccount(dto);
  }

  @PatchMapping("/{id}")
  public Account updateAccount(@PathVariable int id, @RequestBody AccountDTO dto) {
    return accountsService.updateAccount(id, dto);
  }

  @DeleteMapping("/{id}")
  public void deleteAccount(@PathVariable int id) {
    accountsService.deleteAccount(id);
  }

}
