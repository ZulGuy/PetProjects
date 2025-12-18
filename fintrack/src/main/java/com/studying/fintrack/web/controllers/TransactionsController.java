package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.Transaction;
import com.studying.fintrack.domain.repositories.TransactionsRepository;
import com.studying.fintrack.domain.services.TransactionsService;
import com.studying.fintrack.domain.specifications.TransactionSpecification;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
  TransactionsRepository transactionsRepository;

  @Autowired
  public TransactionsController(TransactionsService transactionsService,
      TransactionsRepository transactionsRepository) {
    this.transactionsService = transactionsService;
    this.transactionsRepository = transactionsRepository;
  }

  @GetMapping
  public ResponseEntity<List<Transaction>> getTransactions() {
    return transactionsService.getAllTransactions().isEmpty()
        || transactionsService.getAllTransactions() == null ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(transactionsService.getAllTransactions());
  }

  @GetMapping("{id}")
  public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
    return transactionsService.getTransactionById(id) == null ? ResponseEntity.notFound().build()
        : ResponseEntity.ok(transactionsService.getTransactionById(id));
  }

  @GetMapping("/get-by-user")
  public ResponseEntity<List<Transaction>> getTransactionsByUserId(@RequestParam int userId) {
    return transactionsService.getTransactionsByUserId(userId) == null
        || transactionsService.getTransactionsByUserId(userId).isEmpty()
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(transactionsService.getTransactionsByUserId(userId));
  }

  @GetMapping("/get-by-date")
  public List<Transaction> getTransactionsByDate(@RequestParam String date) {
    return null;
  }

  @GetMapping("/get-by-date-range")
  public List<Transaction> getTransactionsByDateRange(@RequestParam String from,
      @RequestParam String to) {
    return null;
  }

  @GetMapping("/get-by-category")
  public ResponseEntity<List<Transaction>> getTransactionsByCategoryId(
      @RequestParam int categoryId) {
    return transactionsService.getTransactionsByCategoryId(categoryId).isEmpty()
        || transactionsService.getTransactionsByCategoryId(categoryId) == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(transactionsService.getTransactionsByCategoryId(categoryId));
  }

  @PostMapping
  public Transaction createTransaction(Transaction transaction) {
    Transaction newTransaction = transactionsService.createTransaction(transaction);
    if(transactionsService.getTransactionById(newTransaction.getId()) != null) {
      return newTransaction;
    }
    return null;
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

  @GetMapping("/search")
  public List<Transaction> searchTransactions(
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false) String bookedAt,
      @RequestParam(required = false) Integer userId,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) List<Integer> categories
  ) {
    Specification<Transaction> spec = Specification.unrestricted();

    if (categoryId != null) {
      spec = spec.and(TransactionSpecification.byCategoryId(categoryId));
    }
    if (bookedAt != null) {
      spec = spec.and(TransactionSpecification.byDate(Timestamp.valueOf(bookedAt)));
    }
    if (userId != null) {
      spec = spec.and(TransactionSpecification.byUserId(userId));
    }
    if (categories != null && !categories.isEmpty()) {
      spec = spec.and(TransactionSpecification.byCategories(categories));
    }
    if (from != null && to != null) {
      spec = spec.and(
          TransactionSpecification.betweenDates(Timestamp.valueOf(from), Timestamp.valueOf(to)));
    }
    var transactions = transactionsRepository.findAll();
    if (transactions.stream().toList().isEmpty())
      throw new RuntimeException("No transactions found");
    return transactions.stream().toList();
  }

}
