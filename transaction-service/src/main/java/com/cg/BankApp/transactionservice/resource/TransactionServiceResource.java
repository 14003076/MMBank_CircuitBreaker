package com.cg.BankApp.transactionservice.resource;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cg.BankApp.transactionservice.pogo.CurrentDataSet;
import com.cg.BankApp.transactionservice.pogo.Transaction;
import com.cg.BankApp.transactionservice.pogo.TransactionType;
import com.cg.BankApp.transactionservice.service.TransactionService;

@Controller
@RequestMapping("/transactions")
public class TransactionServiceResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		System.out.println("inside deposite ");
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://mmbank/AccountService/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		transaction.getTransactionDate();
		Double updateBalance = transactionService.deposit(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount(), LocalDateTime.now(),
				transaction.getTransactionType());
		restTemplate.put(
				"http://mmbank/AccountService/accounts/" + transaction.getAccountNumber() + "?balance=" + updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		System.out.println("inside withdraw ");
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://mmbank/AccountService/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = transactionService.withdraw(transaction.getAccountNumber(),
				transaction.getTransactionDetails(), currentBalance, transaction.getAmount(), LocalDateTime.now(),
				transaction.getTransactionType());
		restTemplate.put(
				"http://mmbank/AccountService/accounts/" + transaction.getAccountNumber() + "?balance=" + updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/fundTransfer")
	public ResponseEntity<Transaction> fundTransfer(@RequestParam("accountNumber") int sendersAccountNumber,
			@RequestParam("accountNumber") int receiversAccountNumber, @RequestParam("amount") Double amount) {
		ResponseEntity<Double> senderentity = restTemplate
				.getForEntity("http://mmbank/AccountService/accounts/" + sendersAccountNumber + "/balance", Double.class);
		Double senderCurrentBalance = senderentity.getBody();
		Double updateBalance = transactionService.withdraw(sendersAccountNumber, "", senderCurrentBalance, amount,
				LocalDateTime.now(), TransactionType.WITHDRAW);
		restTemplate.put("http://mmbank/AccountService/accounts/" + sendersAccountNumber + "?balance=" + updateBalance, null);
		ResponseEntity<Double> receiverentity = restTemplate
				.getForEntity("http://mmbank/AccountService/accounts/" + receiversAccountNumber + "/balance", Double.class);
		Double receiverCurrentBalance = receiverentity.getBody();
		Double updatedBalance = transactionService.deposit(receiversAccountNumber, "", receiverCurrentBalance, amount,
				LocalDateTime.now(), TransactionType.DEPOSIT);
		restTemplate.put("http://mmbank/AccountService/accounts/" + receiversAccountNumber + "?balance=" + updatedBalance,
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/statements")
	public ResponseEntity<CurrentDataSet> statement() {
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = transactionService.getStatements();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<>(currentDataSet, HttpStatus.CREATED);
	}

	/*
	 * @GetMapping public CurrentDataSet getStatement(@RequestParam("offset") int
	 * offset, @RequestParam("size") int size) { int currentSize = size == 0 ? 5 :
	 * size; int currentOffset = offset == 0 ? 1 : offset; Link previous =
	 * linkTo(methodOn(TransactionServiceResource.class).getStatement(currentOffset
	 * - currentSize, currentSize)).withRel("previous"); Link next =
	 * linkTo(methodOn(TransactionServiceResource.class).getStatement(currentOffset
	 * + currentSize, currentSize)).withRel("next"); List<Transaction> transactions
	 * = transactionService.getStatement(); List<Transaction> currentDataSet = new
	 * ArrayList<Transaction>(); for(int value=currentOffset-1;
	 * value<currentOffset+currentSize-1; value++) { Transaction transaction =
	 * transactions.get(i); currentDataSet.add(transaction); }
	 * System.out.println(currentDataSet); CurrentDataSet dataSet = new
	 * CurrentDataSet(currentDataSet, next, previous); return dataSet;
	 * 
	 * }
	 */
}
