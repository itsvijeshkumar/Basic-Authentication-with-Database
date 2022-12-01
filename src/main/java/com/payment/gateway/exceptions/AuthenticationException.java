package com.payment.gateway.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Vijesh
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthenticationException extends Exception {
	/**
	 *
	 */
	@Autowired
	Environment env;
	private static final long serialVersionUID = 1L;


	private static int ERROR_CODE=1100;


	public AuthenticationException(String exception) {
		super(exception);
	}

	public AuthenticationException() {
		super();
	}
	
	public AuthenticationException(int errorCode) {
		super();
	}
}
