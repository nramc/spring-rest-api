package com.javaconvergence.emp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_MODIFIED)
public class EmployeeDetailsNotFound extends RuntimeException {
	
	private static final long serialVersionUID = -2385951509021315648L;

	public EmployeeDetailsNotFound() {
		super();
	}
	
	public EmployeeDetailsNotFound(String msg) {
		super(msg);
	}
	
	public EmployeeDetailsNotFound(Throwable ex) {
		super(ex);
	}

}
