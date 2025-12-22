package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {

  Optional<List<Account>> findByUserId(int userId);
  Optional<Account> findByIdAndUserId(int id, int userId);
}
