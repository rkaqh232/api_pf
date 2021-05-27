package com.pf.api.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pf.api.entity.Account;

@Repository
@Mapper
public interface AccountMapper {
	Account findByUsername(@Param("username")String username);

	List<String> getAuthority(int id);
}
