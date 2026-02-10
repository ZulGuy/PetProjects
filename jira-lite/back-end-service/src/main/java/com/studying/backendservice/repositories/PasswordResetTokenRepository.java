package com.studying.backendservice.repositories;

import com.studying.backendservice.models.PasswordResetToken;
import com.studying.backendservice.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
  Optional<PasswordResetToken> findByToken(String token);
  void deleteByUser(User user);

}
