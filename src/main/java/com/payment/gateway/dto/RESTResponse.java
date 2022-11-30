package com.payment.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Service
@JsonInclude(Include.NON_NULL)
@Data
public class RESTResponse {

	@JsonInclude(Include.NON_DEFAULT)
	private int statusCode;
	private String globalPayCode;
	private String globalPayDescription;
	private String globalPayDescriptionAr;
	@JsonInclude(Include.NON_DEFAULT)
	String respMessage;
	String respMessageAr;
	@JsonInclude(Include.NON_DEFAULT)
	int pageNumber;
	@JsonInclude(Include.NON_DEFAULT)
	int pageSize;
	@JsonInclude(Include.NON_DEFAULT)
	long totalRecordCount;
	@JsonInclude(Include.NON_DEFAULT)
	String sortBy;
	@JsonInclude(Include.NON_DEFAULT)
	String filterBy;
	Object result = null;
	Object response = null;
	
}
