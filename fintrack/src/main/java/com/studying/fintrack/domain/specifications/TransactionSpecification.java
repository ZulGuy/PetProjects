package com.studying.fintrack.domain.specifications;

import com.studying.fintrack.domain.entities.Transaction;
import java.sql.Timestamp;
import java.util.Collection;
import org.springframework.data.jpa.domain.Specification;

//Додати перевірку на isEmpty та null до усіх методів
public class TransactionSpecification {

  public static Specification<Transaction> byDate(Timestamp booked_at) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("booked_at"), booked_at);
  }

  public static Specification<Transaction> betweenDates(Timestamp from, Timestamp to) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.between(root.get("booked_at"), from, to);
  }

  public static Specification<Transaction> byAccountId(int accountId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("account_id"), accountId);
  }

  public static Specification<Transaction> byCategoryId(int categoryId) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("category_id"), categoryId);
  }

  public static Specification<Transaction> byCategories(Collection<Integer> ids) {
    return (root, query, criteriaBuilder) ->
        root.get("category_id").in(ids);
  }

}
