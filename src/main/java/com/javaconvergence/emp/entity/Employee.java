package com.javaconvergence.emp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Employee {

	public Employee() {
		super();
	}
	public Employee(Long empId, String firstName, String lastName, String mobile) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
	}
	
	@Id
	private Long empId;
	private String firstName;
	private String lastName;
	private String mobile;
}
