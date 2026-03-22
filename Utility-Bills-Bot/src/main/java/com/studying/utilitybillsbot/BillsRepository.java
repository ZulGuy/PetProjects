package com.studying.utilitybillsbot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillsRepository extends JpaRepository<Bill, Integer> {

  Optional<List<Bill>> findByChatId(long chatId);
  Optional<Bill> findByMonthAndYear(int month, int year);

}
