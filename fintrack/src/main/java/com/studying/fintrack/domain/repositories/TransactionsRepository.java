package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Transaction;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

  List<Transaction> findByAccountId(int accountId);
  List<Transaction> findByBookedAtBetween(Timestamp from, Timestamp to);
  List<Transaction> findByBookedAt(Timestamp bookedAt);
  List<Transaction> findByCategoryId(int categoryId);
}
