package com.cg.MMBank.accountservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.MMBank.accountservice.entity.Account;
@Repository
public interface AccountRepository extends MongoRepository<Account, Integer>{

}
