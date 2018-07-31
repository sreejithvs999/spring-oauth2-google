package com.svs.learn.guser.security.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableResourceServer
public class GUserResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigSource()).and().antMatcher("/api/**").authorizeRequests()
				.antMatchers("/api/user/token/refresh").permitAll().anyRequest().authenticated();
		http.setSharedObject(ClientDetailsService.class, clientDetailsService());
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenService());
	}

	@Bean
	DefaultTokenServices tokenService() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(clientDetailsService());
		return tokenServices;
	}

	@Autowired
	DataSource dataSource;

	TokenStore tokenStore() {

		// JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);

		return new InMemoryTokenStore();
	}

	private CorsConfigurationSource corsConfigSource() {

		UrlBasedCorsConfigurationSource configSrc = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedHeaders(Collections.singletonList("*"));
		corsConfig.setAllowedMethods(Collections.singletonList("*"));
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));

		configSrc.registerCorsConfiguration("/**", corsConfig);
		return configSrc;
	}

	@Bean
	ClientDetailsService clientDetailsService() {

		InMemoryClientDetailsService cs = new InMemoryClientDetailsService();
		BaseClientDetails clientDetails = new BaseClientDetails();
		clientDetails.setClientId("guser_app");
		clientDetails.setClientSecret("secret");
		clientDetails.setScope(Collections.singleton("all"));
		clientDetails.setAuthorizedGrantTypes(new HashSet<>(Arrays.asList("implicit", "refresh_token")));
		HashMap<String, ClientDetails> map = new HashMap<>();
		map.put(clientDetails.getClientId(), clientDetails);
		cs.setClientDetailsStore(map);
		return cs;
	}
}
