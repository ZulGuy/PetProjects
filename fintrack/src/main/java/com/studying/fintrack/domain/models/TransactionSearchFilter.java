package com.studying.fintrack.domain.models;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

public record TransactionSearchFilter(
    Integer categoryId,
    String bookedAt,
    Integer accountId,
    String from,
    String to,
    List<Integer> categories
) {}
