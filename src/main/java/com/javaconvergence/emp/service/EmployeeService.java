package com.javaconvergence.emp.service;

import java.util.List;

import com.javaconvergence.emp.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public boolean addEmployee(Employee emp);
	
	public boolean saveEmployee(Employee emp);
	
	public boolean deleteEmployee(Employee emp);
	
}
