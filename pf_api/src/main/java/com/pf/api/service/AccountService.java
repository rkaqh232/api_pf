package com.pf.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pf.api.entity.Account;
import com.pf.api.repository.mapper.AccountMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {
	@Autowired
	private AccountMapper accountMapper;
	
	public Account findByUsername(String username) {
		Account account = new Account();
		account.setName("asdf");
		account.setPassword("1234");
		return account;
	}

	public Object findByConpanySn(String replaceAll) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
