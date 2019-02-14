package com.javaconvergence.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaconvergence.emp.entity.Employee;
import com.javaconvergence.emp.service.EmployeeService;

@RestController
public class EmployeeServiceController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = { "/home", "/" })
	public String getHomePage() {
		return "Hello World from Java Convergence..";
	}

	@GetMapping(value = "/employees")
	public ResponseEntity<Object> getEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	@PostMapping(value = "/employees")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee emp) {
		employeeService.addEmployee(emp);
		return new ResponseEntity<>("Employee detailed added successfully.", HttpStatus.OK);
	}

	@PutMapping(value = "/employees/{empId}")
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee emp, @PathVariable long empId) {
		emp.setEmpId(empId);
		employeeService.saveEmployee(emp);
		return new ResponseEntity<>("Employee Details updated successfully.", HttpStatus.OK);
	}

	@DeleteMapping(value = "/employees/{empId}")
	public ResponseEntity<Object> deleteEmployee(@RequestBody Employee emp, @PathVariable long empId) {
		emp.setEmpId(empId);
		employeeService.deleteEmployee(emp);
		return new ResponseEntity<>("Employee detail removed successfully.", HttpStatus.OK);
	}

}
