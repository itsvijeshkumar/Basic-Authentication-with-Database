package com.payment.gateway.controller;

import java.util.ArrayList;
import java.util.Objects;

import com.payment.gateway.dto.AuthenticationResponseDTO;
import com.payment.gateway.exceptions.AuthenticationException;
import com.payment.gateway.jpa.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.gateway.bo.LoginProfile;
import com.payment.gateway.config.JwtTokenUtil;
import com.payment.gateway.constant.IConstants;
import com.payment.gateway.model.JwtRequest;
import com.payment.gateway.security.UtilRSA;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private Environment environment;
	
	@Autowired
	LoginRepository loginRepository;
	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public AuthenticationResponseDTO createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
		//with db
		/*
		 * factch from db using username
		 * decrypt password
		 * match with decrypted which we have as input
		 */
		LoginProfile loginProfile = loginRepository.findIdByUsername(authenticationRequest.getUsername());
		if(null == loginProfile) {
			throw new AuthenticationException(IConstants.PASSWORD_MISMATCH);
		}
		//String passwordCredential = "AqU8ISe3iF/GJzaA2ZmqS+HIENEpuX+ObrVmmTHWblePKtGm8v3bdEpWipX/DpKSKrPmpU6AyyAqE+jkcJM3WBqMYTEu13++eM0gUHBe9sB6Wej5FV6juZvbeIk3bBhnPRlONRy6koMx8IIchwbPKaTjzRZVDU5kFacBp7y7xa8=";
		//String userNameCredential = "Vijesh";
		
		String decryptedPasswordFatchedDB = UtilRSA.decrypt(loginProfile.getuPassword(),
				environment.getProperty("rsa.private.key"));
		
		String decryptedPassword = UtilRSA.decrypt(authenticationRequest.getPassword(),
				environment.getProperty("rsa.private.key"));
		
		if(!decryptedPasswordFatchedDB.equalsIgnoreCase(decryptedPassword) || !loginProfile.getUsername().equalsIgnoreCase(authenticationRequest.getUsername())) {
			throw new AuthenticationException(IConstants.PASSWORD_MISMATCH);
		}
		
		//authenticate(authenticationRequest.getUsername(), decryptedPassword);

		final UserDetails userDetails = new User(authenticationRequest.getUsername(), decryptedPassword,
				new ArrayList<>());
		
		
//		final UserDetails userDetails = jwtInMemoryUserDetailsService
//				.loadUserByUsername(authenticationRequest.getUsername()+","+decryptedPassword);

		final String token = jwtTokenUtil.generateToken(userDetails);
		final String refreshtoken = jwtTokenUtil.refreshToken(userDetails);
		authenticationResponseDTO.setAccessToken(token);
		authenticationResponseDTO.setRefreshToken(refreshtoken);
		authenticationResponseDTO.setType("Bearer");
		
		return authenticationResponseDTO;
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
