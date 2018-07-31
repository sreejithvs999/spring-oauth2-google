package com.svs.learn.guser.security.config;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svs.learn.guser.service.SecurityServices;
import com.svs.learn.guser.service.UserServices;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/", "/public/**", "/login**")
				.permitAll().anyRequest().authenticated().and()
				.addFilterBefore(googleFilter(), UsernamePasswordAuthenticationFilter.class).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER).and().exceptionHandling()
				.authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());

	}

	@Autowired
	UserServices userServices;

	Filter googleFilter() {
		GoogleAuthFilter filter = new GoogleAuthFilter("/login/google");
		GoogleUserAuthenticationManager googleAuthMgr = new GoogleUserAuthenticationManager();
		googleAuthMgr.setGoogleClientId(googleClientId);
		googleAuthMgr.setUserServices(userServices);
		filter.setAuthenticationManager(googleAuthMgr);
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		return filter;
	}

	@Value("${google.client.clientId}")
	private String googleClientId;

	@Autowired
	SecurityServices secServices;

	ObjectMapper om = new ObjectMapper();

	@Bean
	AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				userServices.saveUserLoginSuccess();

				OAuth2AccessToken token = secServices.createToken(authentication);

				try {
					response.getWriter().write(om.writeValueAsString(token));
					response.flushBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
	}

}
