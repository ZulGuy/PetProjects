package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.Transaction;
import com.studying.fintrack.domain.services.TransactionsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

  TransactionsService transactionsService;

  public TransactionsController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  @GetMapping
  public ResponseEntity<List<Transaction>> getTransactions() {
    return transactionsService.getAllTransactions().isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(transactionsService.getAllTransactions());
  }

  @GetMapping("{id}")
  public Transaction getTransactionById(@PathVariable int id) {
    return transactionsService.getTransactionById(id);
  }

  @GetMapping("/get-by-user")
  public List<Transaction> getTransactionsByUserId(@RequestParam int userId) {
    return transactionsService.getTransactionsByUserId(userId);
  }

  @GetMapping("/get-by-date")
  public List<Transaction> getTransactionsByDate(@RequestParam String date) {
    return null;
  }

  @GetMapping("/get-by-date-range")
  public List<Transaction> getTransactionsByDateRange(@RequestParam String from, @RequestParam String to) {
    return null;
  }

  @GetMapping("/get-by-category")
  public List<Transaction> getTransactionsByCategoryId(@RequestParam int categoryId) {
    return transactionsService.getTransactionsByCategoryId(categoryId);
  }

  @PostMapping
  public Transaction createTransaction(Transaction transaction) {
    return transactionsService.createTransaction(transaction);
  }

  @PutMapping("{id}")
  public Transaction updateTransaction(@PathVariable int id, Transaction transaction) {
    transaction.setId(id);
    return transactionsService.updateTransaction(transaction);
  }

  @DeleteMapping("{id}")
  public void deleteTransaction(@PathVariable int id) {
    transactionsService.deleteTransaction(id);
  }

}
