package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer findById(Long customerId)throws ResourceNotFoundException;
    Customer create(Customer customer);
    List<Customer> list();
    Customer update(Long customerId) throws ResourceNotFoundException;
    void delete(Long customerId) throws ResourceNotFoundException;


}
