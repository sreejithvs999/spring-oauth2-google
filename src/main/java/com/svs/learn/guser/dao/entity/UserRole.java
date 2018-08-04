package com.svs.learn.guser.dao.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;


@Embeddable
@Table(name = "user_role")
public class UserRole {

	public UserRole() {
	}

	public UserRole(String role) {
		this.roleName = role;
	}

	@Column(name = "role")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
