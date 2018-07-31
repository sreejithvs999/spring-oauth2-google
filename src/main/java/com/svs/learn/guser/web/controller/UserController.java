package com.svs.learn.guser.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svs.learn.guser.service.SecurityServices;
import com.svs.learn.guser.service.UserServices;
import com.svs.learn.guser.web.beans.EditUserBean;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserServices userServices;

	@Autowired
	SecurityServices securityService;

	@ResponseBody
	@GetMapping("/info")
	public Object userInfo() {

		String emailId = securityService.getGoogleAuthentication().getGoogleAuthUser().getEmail();
		return userServices.getUserInfo(emailId);
	}

	@GetMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) {

		securityService.invalidateToken((String) request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE));
		return "LOGGED_OUT";
	}

	@PostMapping("/token/refresh")
	public OAuth2AccessToken refreshToken(@RequestParam("refresh_token") String refreshToken) {
		return securityService.refreshToken(refreshToken);
	}

	@GetMapping("/search")
	public Object searchUserDetail(@RequestParam("emailId") String emailId) {

		return userServices.getUserInfo(emailId);
	}

}
