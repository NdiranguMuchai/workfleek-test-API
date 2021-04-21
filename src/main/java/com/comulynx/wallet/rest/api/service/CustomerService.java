package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer findByCustomerId(String customerId)throws ResourceNotFoundException;
    Customer create(Customer customer) ;
    List<Customer> list();
    Customer update(String customerId) throws ResourceNotFoundException;
    void delete(String customerId) throws ResourceNotFoundException;

    //should probably move this to account service
    String generateAccountNo(String customerId) throws ResourceNotFoundException;


}
