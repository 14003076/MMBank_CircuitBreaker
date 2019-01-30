package com.cg.MMBank.accountservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.MMBank.accountservice.entity.Account;
import com.cg.MMBank.accountservice.entity.SavingsAccount;
import com.cg.MMBank.accountservice.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
	@GetMapping 
	public List<Account> getAllSavingsAccount() {
		
		return service.getAllSavingsAccount();
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountById(@PathVariable int accountNumber)
	{
		Account account = service.getAccountById(accountNumber);
		
		if(account==null)
		{
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Account>(account,HttpStatus.OK);
		
	}
	
	@GetMapping("/{accountNumber}/balance")
	public Double getAccountBalance(@PathVariable int accountNumber)
	{
		return service.getAccountBalance(accountNumber);
	}
	
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Account>updateBalance(@PathVariable int accountNumber, @RequestParam("balance") Double currentBalance){
	 Account  account = service.getAccountById(accountNumber);
		if(account==null) {
			return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		/* currentBalance=account.getCurrentBalance()+currentBalance; */
		account.setCurrentBalance(currentBalance);
		service.updateBalance(account);
		return new ResponseEntity<Account>(account,HttpStatus.OK);
	}
	
   @DeleteMapping("/{accountNumber}")
   public void deleteAccount(@PathVariable int accountNumber)
   {
	 service.deleteAccount(accountNumber);
	   
   }
   
   @PostMapping
   public void createNewAccount(@RequestBody SavingsAccount account) {
	   service.createNewAccount(account);
	   
   }
   
   @PutMapping
   public void updateAccount(@RequestBody SavingsAccount savingsAccount)
   {
	   service.updateAccount(savingsAccount);
   }
  }
   
