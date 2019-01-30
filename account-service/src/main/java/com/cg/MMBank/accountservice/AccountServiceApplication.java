package com.cg.MMBank.accountservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;

import com.cg.MMBank.accountservice.entity.CurrentAccount;
import com.cg.MMBank.accountservice.entity.SavingsAccount;
import com.cg.MMBank.accountservice.repository.AccountRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public CommandLineRunner commandLineRunner(AccountRepository repository)
	{
		return (args)->{
		repository.save(new SavingsAccount(101,"sai",1343.00,true));
		repository.save(new SavingsAccount(102,"si",1354.00,true));
		repository.save(new CurrentAccount(103,"i",13.00,2353.00));
		repository.save(new SavingsAccount(104,"ai",134.00,true));
		repository.save(new CurrentAccount(105,"s",1.00,35464.00));
		repository.save(new SavingsAccount(106,"sri",133.00,true));
		repository.save(new SavingsAccount(109,"shabby",303.00,true));
	};
}
}

