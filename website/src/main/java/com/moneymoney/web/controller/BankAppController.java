package com.moneymoney.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.CurrentDataSet;
import com.moneymoney.web.entity.Transaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableCircuitBreaker
@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping("/deposit")
	public String depositForm() {
		return "DepositForm";
	}
	
	@HystrixCommand(fallbackMethod="fail")
	@RequestMapping("/depositForm")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		System.out.println("Inside Deposit");
		restTemplate.postForEntity("http://mmbank/Transactions/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		System.out.println("Send to view");
		return "DepositForm";
	}
	
	public String fail(@ModelAttribute Transaction transaction,
			Model model) {
		
		return "error";
	}
	
	@RequestMapping("/withdrawl")
	public String withdrawForm() {
		return "withdraw";
	}
	
	@HystrixCommand(fallbackMethod="lose")
	@RequestMapping("/withdrawForm")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://mmbank/Transactions/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "withdraw";
	}
	
	public String lose(@ModelAttribute Transaction transaction,
			Model model) {
		return "error";
	}
	
	@RequestMapping("/FundTransfer")
	public String fundTransferForm() {
		return "fundTransferForm";
	}
	
	@HystrixCommand(fallbackMethod="dataLose")
	@RequestMapping("/fundTransfer")
	public String fundTransfer(@RequestParam("sendersAccountNumber") int sendersAccountNumber,@RequestParam("receiversAccountNumber") int receiversAccountNumber,@RequestParam("amount") Double amount,@ModelAttribute Transaction transaction,Model model) {
		transaction.setAccountNumber(sendersAccountNumber);
		restTemplate.postForEntity("http://mmbank/Transactions/transactions/withdraw", 
				transaction, null);
		transaction.setAccountNumber(receiversAccountNumber);
		
		restTemplate.postForEntity("http://mmbank/Transactions/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "fundTransferForm";
	}
	
	public String dataLose(@RequestParam("sendersAccountNumber") int sendersAccountNumber,@RequestParam("receiversAccountNumber") int receiversAccountNumber,@RequestParam("amount") Double amount,@ModelAttribute Transaction transaction,Model model) {
		return "error";
	}
	
	@RequestMapping("/Statement")
	public String getStatementForm() {
		return "getStatement";
	}
	
	@HystrixCommand(fallbackMethod="dataLosing")
	@RequestMapping("/statement")
	public ModelAndView getStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		int currentSize = size==0?5:size;
		int currentOffset = offset==0?1:offset;
		Link previous = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController.class).getStatement(currentOffset-currentSize, currentSize)).withRel("previous");
		Link next = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BankAppController.class).getStatement(currentOffset+currentSize, currentSize)).withRel("next");
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://mmbank/Transactions/transactions/statements", CurrentDataSet.class);
		List<Transaction> transactionList = currentDataSet.getTransactions();
		List<Transaction> transactions = new ArrayList<Transaction>();
		for(int value=currentOffset-1; value<currentOffset+currentSize-1; value++) {
			if((transactionList.size() <= value && value > 0) || currentOffset < 1)
				break;
			Transaction transaction = transactionList.get(value);
			transactions.add(transaction);		
		}
		currentDataSet.setPreviousLink(previous);
		currentDataSet.setNextLink(next);
		currentDataSet.setTransactions(transactions);
		return new ModelAndView("DepositForm", "currentDataSet", currentDataSet);

	}
	public String dataLosing(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		return "error";
	}
}
