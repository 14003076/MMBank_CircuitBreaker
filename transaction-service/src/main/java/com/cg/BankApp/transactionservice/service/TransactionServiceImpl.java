package com.cg.BankApp.transactionservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.BankApp.transactionservice.pogo.Transaction;
import com.cg.BankApp.transactionservice.pogo.TransactionType;
import com.cg.BankApp.transactionservice.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Double withdraw(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount,
			LocalDateTime now, String transactionType) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setAmount(amount);
		currentBalance -= amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(now);
		transaction.setTransactionDetails("ATM");
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transactionRepository.save(transaction);
		return currentBalance;
	}

	@Override
	public Double deposit(Integer accountNumber, String transactionDetails, Double currentBalance, Double amount,
			LocalDateTime now, String transactionType) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setAmount(amount);
		currentBalance += amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(now);
		transaction.setTransactionDetails("ATM");
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transactionRepository.save(transaction);
		return currentBalance;
	}

	@Override
	public List<Transaction> getStatements() {
		return transactionRepository.findAll();
	}

	/*
	 * @Override public List<Transaction> getStatement() { // TODO Auto-generated
	 * method stub return transactionRepository.findAll(); }
	 */
}