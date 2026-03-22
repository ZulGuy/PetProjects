package com.studying.utilitybillsbot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesRepository extends JpaRepository<Rate, Integer> {

  Optional<Rate> findByChatId(long chatId);
  Optional<Rate> findByMonthAndYear(int month, int year);

}
