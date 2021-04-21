package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public double findBalance(String customerId, String accountNo) throws ResourceNotFoundException{
        Account account = accountRepository.findAccountByCustomerIdAndAccountNo(customerId, accountNo)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Account with customer ID "+customerId+" and Account number "+ accountNo +" does not exist"));

        return account.getBalance();
    }
}
