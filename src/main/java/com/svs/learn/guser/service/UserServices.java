package com.svs.learn.guser.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.svs.learn.guser.dao.GUserDao;
import com.svs.learn.guser.dao.entity.GUser;
import com.svs.learn.guser.dao.entity.UserRole;
import com.svs.learn.guser.exception.GUserAppException;
import com.svs.learn.guser.security.config.GoogleAuthUser;
import com.svs.learn.guser.security.config.GoogleAuthentication;
import com.svs.learn.guser.service.bean.UserBean;
import com.svs.learn.guser.util.L7Constants;

@Service
public class UserServices {

	private Logger log = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	GUserDao l7UserDao;

	@Transactional
	public void saveUserLoginSuccess() {

		GoogleAuthentication googleAuth = (GoogleAuthentication) SecurityContextHolder.getContext().getAuthentication();
		GoogleAuthUser googleUser = googleAuth.getGoogleAuthUser();

		GUser l7user = l7UserDao.getUserByEmail(googleUser.getEmail());

		if (l7user == null) {
			l7user = new GUser();
			l7user.setEmailId(googleUser.getEmail());
			l7user.setFirstName(googleUser.getGivenName());
			l7user.setLastName(googleUser.getFamilyName());
			l7user.setPictureUrl(googleUser.getPictureUrl());
			l7user.setLocale(googleUser.getLocale());
			l7user.setUserStatus("A");
			l7user.getRoles().add(new UserRole("ROLE_USER"));
			l7user.setCreatedDt(LocalDateTime.now());
			l7user = l7UserDao.saveL7User(l7user);
		}

	}

	@Transactional(readOnly = true)
	public UserBean getUserInfo(String email) {

		GUser l7user = l7UserDao.getUserByEmail(email);

		if (l7user == null) {
			throw new GUserAppException("User details are missing.");
		}

		UserBean userBean = new UserBean();
		BeanUtils.copyProperties(l7user, userBean);

		userBean.setRoles(l7user.getRoles().stream().map(ub -> ub.getRoleName()).collect(Collectors.toList()));

		return userBean;
	}

	public List<String> getRoleByEmail(String emailId) {
		return l7UserDao.getUserRoles(emailId).stream().map(ur -> ur.getRoleName()).collect(Collectors.toList());
	}

}
