package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.service.CustomerService;
import com.comulynx.wallet.rest.api.service.security.HashService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final HashService hashService;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               AccountRepository accountRepository,
                               HashService hashService) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.hashService = hashService;
    }

    @Override
    public Customer findById(Long customerId) throws ResourceNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));


    }

    @Override
    public Customer create(Customer customer) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPin = hashService.getHashedValue(customer.getPin(), encodedSalt);

        customer.setPin(hashedPin);

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Long customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customer.setEmail(customer.getEmail());
        customer.setLastName(customer.getLastName());
        customer.setFirstName(customer.getFirstName());

        return customerRepository.save(customer);
    }

    @Override
    public void delete(Long customerId) throws ResourceNotFoundException {
        customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customerRepository.deleteById(customerId);
    }
}
