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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	
}
