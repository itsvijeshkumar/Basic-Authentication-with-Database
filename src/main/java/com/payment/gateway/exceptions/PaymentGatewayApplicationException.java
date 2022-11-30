package com.payment.gateway.exceptions;

import com.payment.gateway.common.utilities.MessagesUtil;

/**
 * 
 * @author Vijesh
 *
 */
@SuppressWarnings("unused")
public class PaymentGatewayApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause;
	private int    errorCode;
	private String resource;
	private String    key;
	private Object[]  params;

	public PaymentGatewayApplicationException() {
		super();
	}

	public PaymentGatewayApplicationException(String message) {
		super(message);
	}
	protected PaymentGatewayApplicationException(int errorCode, String message, Exception exception)
	{
		super(message);
		this.errorCode = errorCode;
		this.cause = exception;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return super.getMessage();
	}
	/**
	 * Return underlying exception.
	 * @return underlying exception <b>null</b> if none
	 */
	public Throwable getCause()
	{
		return cause;
	}
	
	private String getFormattedMessageEn()
	{
		return MessagesUtil.getMessageEn(this.resource,this.key, this.params);
	}

	private String getFormattedMessageAr()
	{
		return MessagesUtil.getMessageAr(this.resource,this.key, this.params);
	}

	
}
