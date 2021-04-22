package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.model.Transaction;
import com.comulynx.wallet.rest.api.repository.TransactionRepository;
import com.comulynx.wallet.rest.api.service.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getMiniStatement(String customerId, String accountNo, Pageable pageable) {
        return transactionRepository.findByCustomerIdAndAccountNoOrderByIdDesc(customerId, accountNo, PageRequest.of(0,5));
    }

    @Override
    public Optional<List<Transaction>> findMiniStatement(String customerId, String accountNo) {
        return transactionRepository.getMiniStatementUsingCustomerIdAndAccountNo(customerId, accountNo);
    }
}
