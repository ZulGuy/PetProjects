package com.studying.fintrack.domain.specifications;

import com.studying.fintrack.domain.entities.Transaction;
import java.sql.Timestamp;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

  public static Specification<Transaction> byDate(Timestamp booked_at) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("booked_at"), booked_at);
  }

}
