package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Customer findById(Long customerId) throws ResourceNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));


    }

    @Override
    public Customer create(Customer customer) {
        return null;
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
