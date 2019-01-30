package com.cg.BankApp.transactionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

	/*
	 * private String password;
	 * 
	 * @Bean public DataSource dataSource(String username, String password, String
	 * url, String driverClass){ System.out.println(driverClass+" "+
	 * url+" "+username+" "+password); DriverManagerDataSource source = new
	 * DriverManagerDataSource(); source.setDriverClassName(driverClass);
	 * source.setUrl(url); source.setUsername(username);
	 * source.setPassword(password); return source; }
	 * 
	 * @Bean public NamedParameterJdbcTemplate namedParameterJdbcTemplate(String
	 * driverClass, String url, String username){ NamedParameterJdbcTemplate
	 * namedParameterJdbcTemplate = new
	 * NamedParameterJdbcTemplate(this.dataSource(driverClass, url, username,
	 * password)); return namedParameterJdbcTemplate; }
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
