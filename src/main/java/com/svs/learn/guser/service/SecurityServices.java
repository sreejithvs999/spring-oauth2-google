package com.svs.learn.guser.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svs.learn.guser.dao.GUserDao;
import com.svs.learn.guser.dao.entity.GUser;
import com.svs.learn.guser.security.config.GoogleAuthentication;
import com.svs.learn.guser.service.bean.UserBean;

@Service
public class SecurityServices {

	@Autowired
	GUserDao l7userDao;

	@Autowired
	DefaultTokenServices tokenServices;

	Logger log = LoggerFactory.getLogger(SecurityServices.class);

	public UserBean getCurrentUser() {

		String emailId = getGoogleAuthentication().getGoogleAuthUser().getEmail();
		GUser l7user = l7userDao.getUserByEmail(emailId);

		UserBean userBean = new UserBean();
		BeanUtils.copyProperties(l7user, userBean);
		return userBean;
	}

	public GoogleAuthentication getGoogleAuthentication() {

		OAuth2Authentication oauth2Auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		return (GoogleAuthentication) oauth2Auth.getUserAuthentication();
	}

	public void invalidateToken(String accessToken) {
		tokenServices.revokeToken(accessToken);
		log.info("Logging out {}", getGoogleAuthentication());
	}

	@Autowired
	DefaultTokenServices tokenService;

	public OAuth2AccessToken createToken(Authentication authentication) {

		Map<String, String> params = new HashMap<>();

		OAuth2Request oauth2Request = new OAuth2Request(params, "guser_app", null, true, Collections.singleton("all"),
				null, null, null, null);

		OAuth2Authentication oauth2Auth = new OAuth2Authentication(oauth2Request, authentication);

		OAuth2AccessToken token = tokenService.createAccessToken(oauth2Auth);
		return token;
	}

	public OAuth2AccessToken refreshToken(String refreshToken) {

		Map<String, String> params = new HashMap<>();

		TokenRequest tokenReq = new TokenRequest(params, "guser_app", Collections.singleton("all"), "refresh_token");

		OAuth2AccessToken accessToken = tokenService.refreshAccessToken(refreshToken, tokenReq);

		return accessToken;

	}
}
