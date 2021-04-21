package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public double findBalance(String customerId, String accountNo) throws ResourceNotFoundException{
        Account account = accountRepository.findAccountByCustomerIdAndAccountNo(customerId, accountNo)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Account with customer ID "+customerId+" and Account number "+ accountNo +" does not exist"));

        return account.getBalance();
    }

    @Override
    public Account create(Account account) throws Exception {
        if (account.getCustomerId() == null){
            throw new Exception("Enter value for customerId");
        }
        else {
            customerRepository.findByCustomerId(account.getCustomerId())
                    .orElseThrow(()->
                            new ResourceNotFoundException("Customer not found for this id :: " + account.getCustomerId()));

            //random a/c no
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();

            String randomAcNo = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            account.setAccountNo(randomAcNo);

            return accountRepository.save(account);
        }
    }
}
