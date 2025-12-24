package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Account;
import com.studying.fintrack.domain.entities.Transaction;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer>,
    JpaSpecificationExecutor<Transaction> {

  Transaction findByIdAndAccount(int id, Account account);
  List<Transaction> findByAccountId(int accountId);
  List<Transaction> findByBookedAtBetween(Timestamp from, Timestamp to);
  List<Transaction> findByBookedAt(Timestamp bookedAt);
  List<Transaction> findByCategoryId(int categoryId);
}
