package com.svs.learn.guser.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
