package com.studying.utilitybillsbot;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

  Optional<User> findByChatId(long chatId);

}
