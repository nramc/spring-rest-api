package com.javacovergence.emp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaconvergence.emp.controller.EmployeeServiceController;
import com.javaconvergence.emp.entity.Employee;
import com.javaconvergence.emp.exception.EmployeeDetaileAlreadyExistsException;
import com.javaconvergence.emp.exception.EmployeeDetailsNotFound;
import com.javaconvergence.emp.service.EmployeeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = { EmployeeServiceController.class })
public class EmployeeControllerTest {

	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	ObjectMapper jsonMapper = new ObjectMapper();

	private List<Employee> listOfEmployees = new ArrayList<Employee>(Arrays.asList(
			new Employee(1001l, "Ram", "Chandran", "01234567890"), new Employee(1001l, "Mark", "Johns", "01234567891"),
			new Employee(1001l, "Sharon", "Mukley", "01234567892"), new Employee(1001l, "Kam", "Ajula", "01234567893"),
			new Employee(1001l, "Dheeps", "Shika", "01234567894")));

	@BeforeEach
	public void testSetup() {
		Mockito.when(employeeService.getAllEmployees()).thenReturn(listOfEmployees);
	}

	@Test
	public void getEmployeesTest() throws Exception {

		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/employees");
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

		List<Employee> result = jsonMapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<Employee>>() {
				});

		assertEquals(listOfEmployees.size(), result.size());

	}

	@Test
	public void addEmployeeTest() throws Exception {
		/* Positive Scenarios */
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeService.addEmployee(newEmploye)).thenReturn(true);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/employees")
				.content(jsonMapper.writeValueAsString(newEmploye))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

		/* Negative Scenarios */
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		Mockito.when(employeeService.addEmployee(existingEmploye))
				.thenThrow(EmployeeDetaileAlreadyExistsException.class);
		reqBuilder = MockMvcRequestBuilders.post("/employees").content(jsonMapper.writeValueAsString(existingEmploye))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
		mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void updateEmployeeTest() throws Exception {
		/* Positive Scenarios */
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		Mockito.when(employeeService.saveEmployee(existingEmploye)).thenReturn(true);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.put("/employees/1001/")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(existingEmploye));
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

		/* Negative Scenarios */
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeService.saveEmployee(newEmploye))
				.thenThrow(EmployeeDetailsNotFound.class);
		reqBuilder = MockMvcRequestBuilders.put("/employees/1011/").content(jsonMapper.writeValueAsString(newEmploye))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
		mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.NOT_MODIFIED.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void deleteEmployeeTest() throws Exception {
		/* Positive Scenarios */
		Employee existingEmploye = new Employee(1001l, "Ram", "Chandran", "01234567890");
		Mockito.when(employeeService.deleteEmployee(existingEmploye)).thenReturn(true);

		RequestBuilder reqBuilder = MockMvcRequestBuilders.delete("/employees/1001/")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.content(jsonMapper.writeValueAsString(existingEmploye));
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

		/* Negative Scenarios */
		Employee newEmploye = new Employee(1011l, "Sam", "Pradeep", "01234567811");
		Mockito.when(employeeService.deleteEmployee(newEmploye))
				.thenThrow(EmployeeDetailsNotFound.class);
		reqBuilder = MockMvcRequestBuilders.delete("/employees/1011/").content(jsonMapper.writeValueAsString(newEmploye))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);
		mvcResult = mockMvc.perform(reqBuilder).andReturn();

		LOGGER.info(mvcResult.getResponse().toString());

		assertEquals(HttpStatus.NOT_MODIFIED.value(), mvcResult.getResponse().getStatus());
	}

}
