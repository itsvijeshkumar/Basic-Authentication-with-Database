package com.payment.gateway.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.payment.gateway.bo.LoginProfile;

@Repository
public interface LoginRepository extends PagingAndSortingRepository<LoginProfile, Long> {

	@Query(value = "SELECT *  from Login_Profile where user_name=?1", nativeQuery = true)
	LoginProfile findIdByUsername(String username);

}

