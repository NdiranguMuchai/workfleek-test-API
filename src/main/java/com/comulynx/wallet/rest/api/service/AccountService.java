package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;

public interface AccountService {
    double findBalance(String customerId, String accountNo) throws ResourceNotFoundException;
    Account create(Account account) throws Exception;
}
