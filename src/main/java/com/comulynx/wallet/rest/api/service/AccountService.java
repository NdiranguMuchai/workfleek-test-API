package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;

public interface AccountService {
    double findBalance(String customerId, String accountNo) throws ResourceNotFoundException;
}
