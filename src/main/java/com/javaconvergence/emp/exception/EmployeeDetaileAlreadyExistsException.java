package com.javaconvergence.emp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_ACCEPTABLE)
public class EmployeeDetaileAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = -6331108343681339618L;

	public EmployeeDetaileAlreadyExistsException() {
		super();
	}
	
	public EmployeeDetaileAlreadyExistsException(String msg) {
		super(msg);
	}
	
	public EmployeeDetaileAlreadyExistsException(Throwable ex) {
		super(ex);
	}

}
