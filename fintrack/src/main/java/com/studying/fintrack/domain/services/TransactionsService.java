package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.Transaction;
import com.studying.fintrack.domain.entities.User;
import com.studying.fintrack.domain.models.TransactionSearchFilter;
import com.studying.fintrack.domain.repositories.TransactionsRepository;
import com.studying.fintrack.domain.specifications.TransactionSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

  TransactionsRepository transactionsRepository;

  @Autowired
  public TransactionsService(TransactionsRepository transactionsRepository) {
    this.transactionsRepository = transactionsRepository;
  }

  /** Це основа, потрібно усі сервісні методи зробити по аналогії цього,
   * так як тут вже прописано 2 сценарії в залежності від ролі юзера.
   * Також треба подивитись наскільки сек'юрно написаний цей метод.
   * @return
   */
  public List<Transaction> getAllTransactions() {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      return transactionsRepository.findAll();
    return transactionsRepository.findByUserId(user.getId());
  }

  public Transaction getTransactionById(int id) {
    Transaction transaction;
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin")) {
      transaction = transactionsRepository.findById(id).orElse(null);
    }
    transaction = transactionsRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
        () -> new EntityNotFoundException("Transaction not found in DB!")
    );
    if (transaction == null) {
      throw new NullPointerException("Transaction not found in DB!");
    }
    return transaction;
  }

  public Transaction createTransaction(Transaction transaction) {
    return transactionsRepository.save(transaction);
  }

  public void deleteTransaction(int id) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      transactionsRepository.deleteById(id);
    transactionsRepository.deleteByIdAndUserId(id, user.getId());
  }

  public Transaction updateTransaction(int id, Transaction transaction) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin")) {
      Transaction updatedTransaction = transactionsRepository.findById(id).orElseThrow(
          () -> new EntityNotFoundException("Transaction not found in DB!")
      );
      updatedTransaction.setBookedAt(transaction.getBookedAt());
      updatedTransaction.setAccount(transaction.getAccount());
      updatedTransaction.setAmountDecimal(transaction.getAmountDecimal());
      updatedTransaction.setCategory(transaction.getCategory());
      updatedTransaction.setCurrency(transaction.getCurrency());
      updatedTransaction.setRawDescription(transaction.getRawDescription());
      updatedTransaction.setNote(transaction.getNote());
      return transactionsRepository.save(updatedTransaction);
    }
    Transaction updatedTransaction = transactionsRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
        () -> new EntityNotFoundException("Transaction not found in DB!")
    );
    updatedTransaction.setBookedAt(transaction.getBookedAt());
    updatedTransaction.setAccount(transaction.getAccount());
    updatedTransaction.setAmountDecimal(transaction.getAmountDecimal());
    updatedTransaction.setCategory(transaction.getCategory());
    updatedTransaction.setCurrency(transaction.getCurrency());
    updatedTransaction.setRawDescription(transaction.getRawDescription());
    updatedTransaction.setNote(transaction.getNote());
    return transactionsRepository.save(updatedTransaction);
  }

  public List<Transaction> getTransactionsByAccountId(int accountId) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      return transactionsRepository.findByAccountId(accountId);
    return transactionsRepository.findByAccountIdAndUserId(accountId, user.getId());
  }

  public List<Transaction> getTransactionsByDate(Timestamp bookedAt) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      return transactionsRepository.findByBookedAt(bookedAt);
    return transactionsRepository.findByBookedAtAndUserId(bookedAt, user.getId());
  }

  public List<Transaction> getTransactionsByDateBetween(Timestamp from, Timestamp to) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      return transactionsRepository.findByBookedAtBetween(from, to);
    return transactionsRepository.findByBookedAtBetweenAndUserId(from, to, user.getId());
  }

  public List<Transaction> getTransactionsByCategoryId(int categoryId) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(user.getAuthorities().contains("Admin"))
      return transactionsRepository.findByCategoryId(categoryId);
    return transactionsRepository.findByCategoryIdAndUserId(categoryId, user.getId());
  }

  public List<Transaction> searchTransactions(TransactionSearchFilter filter) {
    Specification<Transaction> spec = Specification.unrestricted();

    if (filter.categoryId() != null) {
      spec = spec.and(TransactionSpecification.byCategoryId(filter.categoryId()));
    }
    if (filter.bookedAt() != null) {
      spec = spec.and(TransactionSpecification.byDate(Timestamp.valueOf(filter.bookedAt())));
    }
    if (filter.accountId() != null) {
      spec = spec.and(TransactionSpecification.byAccountId(filter.accountId()));
    }
    if (filter.categories() != null && !filter.categories().isEmpty()) {
      spec = spec.and(TransactionSpecification.byCategories(filter.categories()));
    }
    if (filter.from() != null && filter.to() != null) {
      spec = spec.and(
          TransactionSpecification.betweenDates(Timestamp.valueOf(filter.from()), Timestamp.valueOf(filter.to())));
    }
    var transactions = transactionsRepository.findAll(spec);
    if (transactions.stream().toList().isEmpty())
      throw new EntityNotFoundException("No transactions found");
    return transactions.stream().toList();
  }

}
