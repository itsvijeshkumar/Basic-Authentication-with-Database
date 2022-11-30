package com.payment.gateway.exceptions;

/**
 * 
 * @author Vijesh
 *
 */
public class BusinessException extends PaymentGatewayApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4943050678129288148L;

	public BusinessException(int errorCode, String message, Exception x) {
		super(errorCode,message, x);
	}

}