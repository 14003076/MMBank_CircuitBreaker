package com.cg.MMBank.accountservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.MMBank.accountservice.entity.Account;
import com.cg.MMBank.accountservice.entity.SavingsAccount;
import com.cg.MMBank.accountservice.repository.AccountRepository;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repo;
	@Override
	public List<Account> getAllSavingsAccount() {
		
		return repo.findAll();
	}
	@Override
	public Account getAccountById(int accountNumber) {
		
		return repo.findById(accountNumber).get();
	}
	@Override
	public Double getAccountBalance(int accountNumber) {
		
		return repo.findById(accountNumber).get().getCurrentBalance();
	}
	@Override
	public void updateBalance(Account account) {
		repo.save(account);
		
	}
	@Override
	public void deleteAccount(int accountNumber) {
		
	  
	repo.deleteById(accountNumber);
	}
	@Override
	public void createNewAccount(SavingsAccount account) {
		repo.insert(account);
		
	}
	
	  @Override 
	  public void updateAccount(SavingsAccount savingsAccount) {
	  repo.save(savingsAccount);
	  
	  }
	 

}
