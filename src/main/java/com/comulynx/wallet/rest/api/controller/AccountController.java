package com.comulynx.wallet.rest.api.controller;

import java.util.List;

import com.comulynx.wallet.rest.api.service.AccountService;
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
	private final AccountService accountService;

	public AccountController(AccountRepository accountRepository,
							 AccountService accountService){

		this.accountRepository = accountRepository;
		this.accountService = accountService;
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

	@GetMapping("/balance/customerId/{customerId}/accountNo/{accountNo}")
	public ResponseEntity<?> getAccountBalanceByCustomerIdAndAccountNo(@PathVariable String customerId,
																	   @PathVariable String accountNo)
			{

		try {

			// TODO : Add logic to find account balance by CustomerId And
			// AccountNo  /** Handled in service layer **/

			return ResponseEntity.ok().body(accountService.findBalance(customerId, accountNo));
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping("/create")
	public Account createAccount(@RequestBody Account account) throws Exception{

			return accountService.create(account);

	}

}
