package com.exercise.model;

import org.hibernate.validator.constraints.NotEmpty;

public class UserBean {
	@NotEmpty(message="User Id must not be null!!")
	private String id;
	@NotEmpty(message="User Name must not be null!!")
	private String name;
	@NotEmpty(message="User Password  must not be null!!")
	private String password;
	@NotEmpty(message="User Confirm Password must not be null!!")
	private String confirm;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
}
