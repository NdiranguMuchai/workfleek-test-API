package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List <Transaction>getMiniStatement(String customerId, String accountNo, Pageable pageable) ;

    Optional<List<Transaction>> findMiniStatement(String customerId, String accountNo);
}
