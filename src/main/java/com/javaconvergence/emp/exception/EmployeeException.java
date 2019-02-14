package com.javaconvergence.emp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class EmployeeException extends RuntimeException {
	private static final long serialVersionUID = 3586825313746138587L;

	
	public EmployeeException() {
		super();
	}
	public EmployeeException(String msg) {
		super(msg);
	}
	
}