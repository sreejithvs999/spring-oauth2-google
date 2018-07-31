package com.svs.learn.guser.security.config;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.svs.learn.guser.service.UserServices;

public class GoogleUserAuthenticationManager implements AuthenticationManager {

	Logger log = LoggerFactory.getLogger(GoogleUserAuthenticationManager.class);

	String googleClientId;

	UserServices userServices;

	@Override
	public Authentication authenticate(Authentication preAuth) throws AuthenticationException {
		GoogleIdTokenVerifier tokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
				JacksonFactory.getDefaultInstance()).setAudience(Collections.singleton(googleClientId)).build();

		try {
			GoogleIdToken googleIdToken = tokenVerifier.verify((String) preAuth.getPrincipal());

			log.info("googleIdToken = {}", googleIdToken);
			if (googleIdToken != null) {

				GoogleIdToken.Payload payload = googleIdToken.getPayload();

				log.info("Payload == {}", payload);

				GoogleAuthUser user = new GoogleAuthUser();
				// user.setEmail(payload.get);
				user.setEmail(payload.getEmail());
				user.setEmailVerified(payload.getEmailVerified());
				user.setUserId(payload.getSubject());
				user.setName((String) payload.get("name"));
				user.setPictureUrl((String) payload.get("picture"));
				user.setLocale((String) payload.get("locale"));
				user.setFamilyName((String) payload.get("family_name"));
				user.setGivenName((String) payload.get("given_name"));
				user.setIdToken((String) preAuth.getPrincipal());

				Collection<GrantedAuthority> roles = userServices.getRoleByEmail(user.getEmail()).stream()
						.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

				if (roles.isEmpty()) {
					roles.add(new SimpleGrantedAuthority("ROLE_USER"));
				}

				GoogleAuthentication googleAuth = new GoogleAuthentication(roles, user);
				googleAuth.setAuthenticated(true);

				return googleAuth;

			} else {

				throw new AuthenticationServiceException("Failed to validate google token");
			}

		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception e) {
			log.error("Google id token verifying.", e);
			throw new AuthenticationServiceException("Failed to validate google token");
		}

	}

	public void setGoogleClientId(String googleClientId) {
		this.googleClientId = googleClientId;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}
}
