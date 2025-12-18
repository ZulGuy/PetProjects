package com.studying.fintrack.domain.repositories;

import com.studying.fintrack.domain.entities.User;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
  List<User> findByCreatedAtBetween(Timestamp from, Timestamp to);
}
