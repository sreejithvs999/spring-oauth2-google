package com.svs.learn.guser.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class EditUserBean {

	@NotNull(message = "userId invalid.")
	@Min(value = 1, message = "userId invalid")
	private Integer userId;

	@Size(min = 0, max = 100, message = "designation invalid.")
	private String designation;

	@Size(min = 0, max = 100, message = "projectRole invalid.")
	private String projectRole;

	@Pattern(regexp = "^[A|N]$", message = "status invalid.")
	@NotNull(message = "status invalid.")
	private String status;

	@NotNull(message = "userRoles list empty.")
	@NotEmpty(message = "userRoles list empty.")
	private List<String> userRoles = new ArrayList<>();

	@NotNull(message = "groupIds list empty.")
	@NotEmpty(message = "groupIds list empty.")
	private List<Integer> groupIds = new ArrayList<>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	
	
	
}
