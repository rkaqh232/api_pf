package com.pf.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		
		//List<String> authList = accountMapper.getAuthority(account.getId());
		List<String> authList = new ArrayList<String>();
		List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		
		if(authList.size() != 0) {
			for(String auth : authList)
				roleList.add(new SimpleGrantedAuthority(auth));
		}else {
			roleList.add(new SimpleGrantedAuthority("USER"));
		}
		
		account.setAuthorities(roleList);
		return account;
	}

	public Object findByConpanySn(String replaceAll) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
