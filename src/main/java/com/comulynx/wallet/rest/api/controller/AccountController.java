package com.comulynx.wallet.rest.api.controller;

import java.util.List;
import java.util.Random;

import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.comulynx.wallet.rest.api.util.AppUtils;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(AppUtils.BASE_URL+"/accounts")
public class AccountController {
	private Gson gson = new Gson();

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public AccountController(AccountRepository accountRepository, CustomerRepository customerRepository){
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}
	@GetMapping("/")
	public List<Account> getAllAccount() {
		return accountRepository.findAll();
	}

	@GetMapping("/{searchId}")
	public ResponseEntity<?> getAccountByCustomerIdOrAccountNo(
			@PathVariable(value = "searchId") String customerIdOrAccountNo) throws ResourceNotFoundException {
		Account account = accountRepository
				.findAccountByCustomerIdOrAccountNo(customerIdOrAccountNo, customerIdOrAccountNo)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Account not found for this searchId :: " + customerIdOrAccountNo));

		return ResponseEntity.ok().body(account);
	}

	@GetMapping("/balance")
	public ResponseEntity<?> getAccountBalanceByCustomerIdAndAccountNo(@RequestBody String request)
			throws ResourceNotFoundException {
		try {
			JsonObject response = new JsonObject();

			final JsonObject balanceRequest = gson.fromJson(request, JsonObject.class);
			String customerId = balanceRequest.get("customerId").getAsString();
			String accountNo = balanceRequest.get("accountNo").getAsString();

			// TODO : Add logic to find account balance by CustomerId And
			// AccountNo
			Account account = null;

			response.addProperty("balance", account.getBalance());
			return ResponseEntity.ok().body(gson.toJson(response));
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/")
	public Account createAccount(@RequestBody Account account) throws ResourceNotFoundException{

		if (account.getCustomerId() == null){
			throw new ResourceNotFoundException("Enter value for customerId");
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
