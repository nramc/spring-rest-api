package com.javacovergence.learning.employeeserviceapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.javacovergence.emp.controller.EmployeeControllerTest;
import com.javacovergence.emp.service.EmployeeServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ EmployeeControllerTest.class, EmployeeServiceTest.class })
public class AllTests {

}
