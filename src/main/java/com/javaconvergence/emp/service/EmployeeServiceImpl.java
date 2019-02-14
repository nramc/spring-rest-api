package com.javaconvergence.emp.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.javaconvergence.emp.entity.Employee;
import com.javaconvergence.emp.exception.EmployeeDetaileAlreadyExistsException;
import com.javaconvergence.emp.exception.EmployeeDetailsNotFound;
import com.javaconvergence.emp.repository.EmployeeRepository;

@Service
@SuppressWarnings("unchecked")
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger("com.javacovergence.learning.employeeserviceapi");

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> listOfEmployees = IteratorUtils.toList(employeeRepository.findAll().iterator());
		LOGGER.info("EmployeeServiceImpl > getAllEmployees() > FETCH ALL EMPLYOEES : [{}]",
				CollectionUtils.isEmpty(listOfEmployees) ? 0 : listOfEmployees.size());
		return listOfEmployees;
	}

	@Override
	public boolean addEmployee(Employee emp) {
		Optional<Employee> empOpt = employeeRepository.findById(emp.getEmpId());
		if (empOpt.isPresent()) {
			throw new EmployeeDetaileAlreadyExistsException("Employee details already exists with same ID");
		} else {
			employeeRepository.save(emp);
			LOGGER.info("EmployeeServiceImpl > addEmployee() > EMPLOYEE DETAILS ADDED SUCCESSFULLY| EMP_ID : [{}]",
					emp.getEmpId());
		}
		return true;

	}

	@Override
	public boolean saveEmployee(Employee emp) {
		Optional<Employee> empOpt = employeeRepository.findById(emp.getEmpId());
		if (empOpt.isPresent()) {
			employeeRepository.save(emp);
			LOGGER.info("EmployeeServiceImpl > saveEmployee() | EMPLOYEE DETAILS SAVED SUCCESSFULLY | EMP_ID:[{}]",
					emp.getEmpId());
		} else {
			throw new EmployeeDetailsNotFound("Employee is not exists to update.");
		}
		return true;
	}

	@Override
	public boolean deleteEmployee(Employee emp) {
		Optional<Employee> empOpt = employeeRepository.findById(emp.getEmpId());
		if (empOpt.isPresent()) {
			employeeRepository.deleteById(empOpt.get().getEmpId());
			LOGGER.info("EmployeeServiceImpl > deleteEmployee() | EMPLOYEE DETAILS REMOVED SUCCESSFULLY | EMP_ID:[{}]",
					emp.getEmpId());
		} else {
			throw new EmployeeDetailsNotFound("Employee details is not exists to remove.");
		}
		return true;
	}

}
