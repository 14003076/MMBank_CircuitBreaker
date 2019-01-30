package com.cg.BankApp.transactionservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.BankApp.transactionservice.pogo.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
