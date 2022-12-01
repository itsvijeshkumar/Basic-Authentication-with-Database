package com.payment.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payment.gateway.dto.RESTResponse;
import com.payment.gateway.service.LoginService;


@RestController
@CrossOrigin()
@RequestMapping(value = "api/v1")
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, value = "/get/details")
	public RESTResponse getDetails() {
		log.info("<------paymentCreate----->");
		RESTResponse response = new RESTResponse();
		try {
			
			response = loginService.getDetails();
		}catch (Exception e) {
			log.error("<-------Error  in GlobalPayController.paymentCreate Exception--->: " , e);
			response = new RESTResponse();
		}
		return response;
	}
}
