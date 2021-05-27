package com.exercise.model;

import org.hibernate.validator.constraints.NotEmpty;

public class StudentBean {
	@NotEmpty(message="Student Id must not be null!!")
	private String id;
	@NotEmpty(message="Student Name must not be null!!")
	private String name;
	
	private String day;
	private String month;
	private String year;
	@NotEmpty(message="Status  must not be null!!")
	private String status;
	@NotEmpty(message="Class Name  must not be null!!")
	private String className;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
