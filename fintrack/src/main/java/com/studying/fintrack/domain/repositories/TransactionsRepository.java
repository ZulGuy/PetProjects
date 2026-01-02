package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.entities.Transaction;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer>,
    JpaSpecificationExecutor<Transaction> {

  Optional<Transaction> findByIdAndUserId(int id, int userId);
  List<Transaction> findByUserId(int userId);
  Optional<Transaction> findByIdAndAccount(int id, Account account);
  List<Transaction> findByAccountId(int accountId);
  List<Transaction> findByBookedAtBetween(Timestamp from, Timestamp to);
  List<Transaction> findByBookedAt(Timestamp bookedAt);
  List<Transaction> findByCategoryId(int categoryId);
  void deleteByIdAndUserId(int id, int userId);
  List<Transaction> findByAccountIdAndUserId(int accountId, int userId);
  List<Transaction> findByBookedAtBetweenAndUserId(Timestamp from, Timestamp to, int userId);
  List<Transaction> findByBookedAtAndUserId(Timestamp bookedAt, int userId);
  List<Transaction> findByCategoryIdAndUserId(int categoryId, int userId);
}
