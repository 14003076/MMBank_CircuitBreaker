package com.cg.MMBank.accountservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.MMBank.accountservice.entity.Account;
import com.cg.MMBank.accountservice.entity.SavingsAccount;
@Service
public interface AccountService{

	List<Account> getAllSavingsAccount();

	Account getAccountById(int accountNumber);

	Double getAccountBalance(int accountNumber);

	void updateBalance(Account account);

	void deleteAccount(int accountNumber);

	void createNewAccount(SavingsAccount account);

	 void updateAccount(SavingsAccount savingsAccount); 

}
