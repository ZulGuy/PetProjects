package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Transaction;
import com.studying.fintrack.domain.repositories.TransactionsRepository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

  TransactionsRepository transactionsRepository;

  public TransactionsService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  public List<Transaction> getAllTransactions() {
    return transactionsRepository.findAll();
  }

  public Transaction getTransactionById(int id) {
    return transactionsRepository.findById(id).orElse(null);
  }

  public Transaction createTransaction(Transaction transaction) {
    return transactionsRepository.save(transaction);
  }

  public void deleteTransaction(int id) {
    transactionsRepository.deleteById(id);
  }

  public Transaction updateTransaction(Transaction transaction) {
    return transactionsRepository.save(transaction);
  }

  public List<Transaction> getTransactionsByUserId(int userId) {
    return transactionsRepository.findByUserId(userId);
  }

  public List<Transaction> getTransactionsByDate(Timestamp bookedAt) {
    return transactionsRepository.findByBookedAt(bookedAt);
  }

  public List<Transaction> getTransactionsByDateBetween(Timestamp from, Timestamp to) {
    return transactionsRepository.findByBookedAtBetween(from, to);
  }

  public List<Transaction> getTransactionsByCategoryId(int categoryId) {
    return transactionsRepository.findByCategoryId(categoryId);
  }

}
