package com.comulynx.wallet.rest.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comulynx.wallet.rest.api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.util.AppUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(AppUtils.BASE_URL+"/customers")
public class CustomerController {

	private final CustomerService customerService;
	private final AccountRepository accountRepository;

	public CustomerController(CustomerService customerService, AccountRepository accountRepository){
		this.customerService = customerService;
		this.accountRepository = accountRepository;
	}

	/**
	 *
	 * Login
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> customerLogin(@RequestBody String request) {
		try {

			return ResponseEntity.status(200).body(HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}


	@GetMapping("/")
	public List<Customer> getAllCustomers() {
		return customerService.list();
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomerByCustomerId(@PathVariable String customerId)
			throws ResourceNotFoundException {
		Customer customer =  customerService.findByCustomerId(customerId);
		return ResponseEntity.ok().body(customer);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) throws Exception{


		try {
			// TODO : Add logic to Hash Customer PIN here
			// TODO : Add logic to check if Customer with provided username, or
			// customerId exists. If exists, throw a Customer with [?] exists
			// Exception.


//				String accountNo = generateAccountNo(customer.getCustomerId());
//				Account account = new Account();
//				account.setCustomerId(customer.getCustomerId());
//				account.setAccountNo(accountNo);
//				account.setBalance(0.0);
//				accountRepository.save(account);

				return ResponseEntity.ok().body(customerService.create(customer));

		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId) throws ResourceNotFoundException {

		Customer updatedCustomer = customerService.update(customerId);

		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/{customerId}")
	public Map<String, Boolean> deleteCustomer(@PathVariable String customerId) throws ResourceNotFoundException {

		customerService.delete(customerId);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/**
	 * generate a random but unique Account No (NB: Account No should be unique
	 * in your accounts table)
	 *
	 */
	private String generateAccountNo(String customerId) throws Exception{
		// TODO : Add logic here - generate a random but unique Account No (NB:
		// Account No should be unique in the accounts table)

		return customerService.generateAccountNo(customerId);

	}
}
