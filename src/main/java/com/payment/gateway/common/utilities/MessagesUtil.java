package com.payment.gateway.common.utilities;

import java.util.Locale;

import com.payment.gateway.dto.RESTResponse;
import com.payment.gateway.constant.IConstants;
import com.payment.gateway.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Vijesh
 *
 */
@Component
public class MessagesUtil {

	private static MessageSourceAccessor accessor;

	private static Environment env;

	@SuppressWarnings("static-access")
	@Autowired
	private void autoWiredEnvironment(Environment env) {
		this.env= env;
	}

	public static String getMessageEn(String code) {

		try {
			return env.getProperty(code+"-en");
		} catch (Exception e) {
			e.printStackTrace();
			return code;
		}

	}

	public static String getMessageAr(String code) {
		try {
			return env.getProperty(code+"-ar");
		} catch (Exception e) {
			e.printStackTrace();
			return code;
		}
	}

	public static String getMessageEn(String resource,String code,Object[] params) {
		if (accessor == null){
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasenames(resource);
			accessor = new MessageSourceAccessor(messageSource, Locale.ENGLISH);
		}
		return accessor.getMessage(code,new Object[] {params});
	}

	public static String getMessageAr(String resource,String code,Object[] params) {
		if (accessor == null){
			ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			messageSource.setBasenames(resource);
			accessor = new MessageSourceAccessor(messageSource, new Locale("ar"));
		}
		return accessor.getMessage(code,new Object[] {params});
	}

	public static void constructResponse(RESTResponse response, Exception exception) {

		if (exception instanceof BusinessException) {
			BusinessException bussexception = (BusinessException)exception;
			String errorCode = String.valueOf(bussexception.getErrorCode());
			response.setGlobalPayCode(errorCode);
			if(Integer.parseInt(errorCode) == IConstants.ALREADY_EXIST_WITH_SAME_LICENSE) {
				response.setGlobalPayDescription(exception.getMessage()+" "+getMessageEn(errorCode));
				response.setGlobalPayDescriptionAr(exception.getMessage()+" "+getMessageAr(errorCode));
			}else if(Integer.parseInt(errorCode) == IConstants.USER_ALREADY_EXIST) {
				response.setGlobalPayDescription(exception.getMessage()+" "+getMessageEn(errorCode));
				response.setGlobalPayDescriptionAr(exception.getMessage()+" "+getMessageAr(errorCode));
			}else {
				response.setGlobalPayDescription(getMessageEn(errorCode));
				response.setGlobalPayDescriptionAr(getMessageAr(errorCode));
			}
		}
		else {
			response.setGlobalPayCode(IConstants.PAYMENT_ERROR_CODE);
			//response.setNotaryDescription(exception.getMessage()); // commenting this on Hossam's suggestion
			response.setGlobalPayDescription(getMessageEn(IConstants.PAYMENT_ERROR_CODE));
			response.setGlobalPayDescriptionAr(getMessageAr(IConstants.PAYMENT_ERROR_CODE));
		}
	}

	public static void constructResponse(RESTResponse response, String errorCode) {

		response.setGlobalPayCode(errorCode);
		response.setGlobalPayDescription(getMessageEn(errorCode));
		response.setGlobalPayDescriptionAr(getMessageAr(errorCode));
	}

}
