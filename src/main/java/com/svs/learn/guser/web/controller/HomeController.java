package com.svs.learn.guser.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.svs.learn.guser.service.SecurityServices;
import com.svs.learn.guser.service.UserServices;

@Controller
public class HomeController {

	Logger log = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public String showIndex(Authentication auth, Model model) {

		if (auth != null && auth.isAuthenticated()) {
			model.addAttribute("authentication", auth);
		}

		return "index";
	}

	

}
