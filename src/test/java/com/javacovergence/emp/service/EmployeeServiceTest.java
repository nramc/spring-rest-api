package com.javacovergence.emp.service;

import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaconvergence.emp.entity.Employee;
import com.javaconvergence.emp.exception.EmployeeDetaileAlreadyExistsException;
import com.javaconvergence.emp.exception.EmployeeDetailsNotFound;
import com.javaconvergence.emp.repository.EmployeeRepository;
import com.javaconvergence.emp.service.EmployeeService;
import com.javaconvergence.emp.service.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EmployeeServiceImpl.class })
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	private List<Employee> listOfEmployees = new ArrayList<Employee>(Arrays.asList(
			new Employee(1001l, "Ram", "Chandran", "01234567890"), new Employee(1001l, "Mark", "Johns", "01234567891"),
			new Employee(1001l, "Sharon", "Mukley", "01234567892"), new Employee(1001l, "Kam", "Ajula", "01234567893"),
			new Employee(1001l, "Dheeps", "Shika", "01234567894")));

	@Test
	public void getAllEmployeesTest() {
		Mockito.when(employeeRepository.findAll()).thenReturn(listOfEmployees);

		List<Employee> result = employeeService.getAllEmployees();
		Assert.assertTrue(CollectionUtils.isEqualCollection(listOfEmployees, result));

	}

	@Test
	public void addEmployeeTest() {
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeRepository.save(newEmploye)).thenReturn(newEmploye);
		Mockito.when(employeeRepository.findById(newEmploye.getEmpId())).thenReturn(Optional.empty());
		employeeService.addEmployee(newEmploye);
		Assert.assertTrue(1011l == newEmploye.getEmpId());
	}

	@Test(expected = EmployeeDetaileAlreadyExistsException.class)
	public void addEmployee_withExceptionTest() {
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		Mockito.when(employeeRepository.findById(existingEmploye.getEmpId())).thenReturn(Optional.of(existingEmploye));
		Mockito.when(employeeRepository.save(existingEmploye)).thenReturn(existingEmploye);
		employeeService.addEmployee(existingEmploye);
	}

	@Test
	public void saveEmployeeTest() {
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		Mockito.when(employeeRepository.save(existingEmploye)).thenReturn(existingEmploye);
		Mockito.when(employeeRepository.findById(existingEmploye.getEmpId())).thenReturn(Optional.of(existingEmploye));
		employeeService.saveEmployee(existingEmploye);
		Assert.assertTrue(1001l == existingEmploye.getEmpId());
	}

	@Test(expected = EmployeeDetailsNotFound.class)
	public void saveEmployee_withExceptionTest() {
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeRepository.findById(newEmploye.getEmpId())).thenReturn(Optional.empty());
		Mockito.when(employeeRepository.save(newEmploye)).thenReturn(newEmploye);
		employeeService.saveEmployee(newEmploye);
	}

	@Test
	public void deleteEmployeeTest() {
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		doNothing().when(employeeRepository).delete(existingEmploye);
		Mockito.when(employeeRepository.findById(existingEmploye.getEmpId())).thenReturn(Optional.of(existingEmploye));
		employeeService.saveEmployee(existingEmploye);
		Assert.assertTrue(1001l == existingEmploye.getEmpId());
	}

	@Test(expected = EmployeeDetailsNotFound.class)
	public void deleteEmployee_withExceptionTest() {
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeRepository.findById(newEmploye.getEmpId())).thenReturn(Optional.empty());
		doNothing().when(employeeRepository).delete(newEmploye);
		employeeService.saveEmployee(newEmploye);
	}

}
