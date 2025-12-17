package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.Transaction;
import com.studying.fintrack.domain.services.TransactionsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
