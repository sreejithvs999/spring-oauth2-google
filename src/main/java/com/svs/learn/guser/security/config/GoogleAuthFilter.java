package com.svs.learn.guser.security.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class GoogleAuthFilter extends AbstractAuthenticationProcessingFilter {

	Logger log = LoggerFactory.getLogger(GoogleAuthFilter.class);

	public GoogleAuthFilter(String processUrl) {
		super(processUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		log.info("In Google auth filter ... ");

		try {

			String idToken = request.getParameter("idtoken");

			if (idToken != null) {
				Authentication authentication = new PreAuthenticatedAuthenticationToken(idToken, idToken);

				return getAuthenticationManager().authenticate(authentication);

			}

		} catch (Exception e) {

			log.error("Google verify error ", e);
			throw new AuthenticationServiceException("Invalid token");
		}

		return null;
	}

}
