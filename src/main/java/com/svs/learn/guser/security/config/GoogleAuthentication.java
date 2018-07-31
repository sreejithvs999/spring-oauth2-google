package com.svs.learn.guser.security.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class GoogleAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private GoogleAuthUser googleAuthUser;

	public GoogleAuthentication(Collection<? extends GrantedAuthority> authorities, GoogleAuthUser googleAuthUser) {
		super(authorities);
		this.googleAuthUser = googleAuthUser;
	}

	@Override
	public Object getCredentials() {
		return googleAuthUser.getEmail();
	}

	@Override
	public Object getPrincipal() {

		return googleAuthUser;
	}

	public GoogleAuthUser getGoogleAuthUser() {
		return googleAuthUser;
	}
}
