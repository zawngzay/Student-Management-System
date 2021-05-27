package com.exercise.model;

import org.hibernate.validator.constraints.NotEmpty;

public class ClassBean {
	@NotEmpty(message="Class Id  must not be null!!")
	private String id;
	@NotEmpty(message="Class Name must not be null!!")
	private String name;
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
}
