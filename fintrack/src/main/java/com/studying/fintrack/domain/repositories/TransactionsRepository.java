package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

}
