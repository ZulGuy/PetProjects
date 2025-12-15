package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
}
