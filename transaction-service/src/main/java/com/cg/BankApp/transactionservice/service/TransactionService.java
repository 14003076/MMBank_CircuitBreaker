package com.cg.BankApp.transactionservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.BankApp.transactionservice.pogo.Transaction;

@Service
public interface TransactionService {

	

	Double withdraw(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount,LocalDateTime now, String transactionType);

	Double deposit(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount,
			LocalDateTime now, String transactionType);

	List<Transaction> getStatements();

	/* List<Transaction> getStatement(); */

}
