package com.comulynx.wallet.rest.api.service.impl;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.service.CustomerService;
import com.comulynx.wallet.rest.api.service.security.HashService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Random;

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
    public Customer findByCustomerId(String customerId) throws ResourceNotFoundException {
         Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

         customer.setPin(hashService.getHashedValue(customer.getPin(), encodedSalt));

         return customer;
    }

    @Override
    public Customer create(Customer customer) throws Exception{
       if (customerRepository.existsByCustomerIdOrEmail(customer.getCustomerId(), customer.getEmail())){
           throw new Exception("Customer already exists.");
       }
       else {

           SecureRandom random = new SecureRandom();
           byte[] salt = new byte[16];
           random.nextBytes(salt);
           String encodedSalt = Base64.getEncoder().encodeToString(salt);
           String hashedPin = hashService.getHashedValue(customer.getPin(), encodedSalt);

           customer.setPin(hashedPin);

           return customerRepository.save(customer);
       }
    }

    @Override
    public List<Customer> list() {
        List<Customer> customerList = customerRepository.findAll();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        customerList.forEach(customer ->
                customer.setPin(hashService.getHashedValue(customer.getPin(), encodedSalt)));

        return customerRepository.findAll();
    }

    @Override
    public Customer update(String customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customer.setEmail(customer.getEmail());
        customer.setLastName(customer.getLastName());
        customer.setFirstName(customer.getFirstName());

        return customerRepository.save(customer);
    }

    @Override
    public void delete(String customerId) throws ResourceNotFoundException {
       Customer customer =  customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customerRepository.delete(customer);
    }

    @Override
    public String generateAccountNo(String customerId) throws ResourceNotFoundException {
        customerRepository.findByCustomerId(customerId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
