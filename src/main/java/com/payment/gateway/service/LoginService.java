package com.payment.gateway.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.gateway.bo.LoginProfile;
import com.payment.gateway.dto.RESTResponse;
import com.payment.gateway.jpa.LoginRepository;


@Service
public class LoginService{

	private static final Logger log = LoggerFactory.getLogger(LoginService.class);

	
	@Autowired
	LoginRepository loginRepository;
	
	
	public RESTResponse getDetails() throws Exception {
		// TODO Auto-generated method stub
		log.info("<------createPayment Method Called----->");
		RESTResponse response = new RESTResponse();
		long id = 1;
		Optional<LoginProfile> loginProfile = loginRepository.findById(id);
		
		if(loginProfile.isPresent()) {
			response.setResult(loginProfile.get());
			//response.setStatusCode(ServiceEnum.RequestStatusCodes.SUCCESS.getSymbolicValue());
			//response.setRespMessage(ServiceEnum.ResponseMessage.SUCCESS.name());
		
		}else {
			log.info("Not Found");
		}
		
		return response;
	}

}
