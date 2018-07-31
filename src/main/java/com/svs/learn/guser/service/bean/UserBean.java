package com.svs.learn.guser.service.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {

	private int userId;

	private String emailId;

	private String firstName;

	private String lastName;

	private String designation;

	private String projectRole;

	private String locale;

	private String pictureUrl;

	private LocalDateTime createdDt;

	private LocalDateTime updateDt;

	private String userStatus;

	private List<String> roles = new ArrayList<>();

}
