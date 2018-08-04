package com.svs.learn.guser.dao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "GUser")
@Table(name = "g_user")
public class GUser {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "designation")
	private String designation;

	@Column(name = "project_role")
	private String projectRole;

	@Column(name = "locale")
	private String locale;

	@Column(name = "picture_url")
	private String pictureUrl;

	@Column(name = "user_status")
	private String userStatus;

	@Column(name = "created_dt")
	private LocalDateTime createdDt;

	@Column(name = "updated_dt")
	private LocalDateTime updatedDt;

	@ElementCollection
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	private List<UserRole> roles = new ArrayList<>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public LocalDateTime getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(LocalDateTime createdDt) {
		this.createdDt = createdDt;
	}

	public LocalDateTime getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(LocalDateTime updatedDt) {
		this.updatedDt = updatedDt;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	
}
