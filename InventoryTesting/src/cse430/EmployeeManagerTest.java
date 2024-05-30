package cse430;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;

    @Before
    public void setUp() {
        employeeManager = new EmployeeManager();
        employeeManager.addEmployee(new Employee(1, "Saki alamin", "IT", "Software Engineer", 50000));
        employeeManager.addEmployee(new Employee(2, "Mohit", "HR", "Manager", 60000));
        employeeManager.addEmployee(new Employee(3, "Rifat", "Finance", "Accountant", 55000));
    }

    @Test
    public void testAddEmployee() {
        employeeManager.addEmployee(new Employee(4, "Fazle", "Marketing", "Marketing Manager", 70000));
        List<Employee> employees = employeeManager.getEmployees();
        assertEquals(4, employees.size());
    }

    @Test
    public void testRemoveEmployee() {
        assertTrue(employeeManager.removeEmployee(2));
        assertFalse(employeeManager.removeEmployee(5)); // Employee with ID 5 doesn't exist
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = employeeManager.findEmployeeById(1);
        assertNotNull(employee);
        assertEquals("Saki alamin", employee.getName());
    }

    @Test
    public void testFindEmployeesByDepartment() {
        List<Employee> employees = employeeManager.findEmployeesByDepartment("HR");
        assertEquals(1, employees.size());
        assertEquals("Mohit", employees.get(0).getName());
    }

    @Test
    public void testCalculateTotalSalary() {
        double totalSalary = employeeManager.calculateTotalSalary();
        assertEquals(165000, totalSalary, 0.01);
    }

    @Test
    public void testGetEmployeesWithHighestSalary() {
        List<Employee> highestPaidEmployees = employeeManager.getEmployeesWithHighestSalary();
        assertEquals(1, highestPaidEmployees.size());
        assertEquals("Mohit", highestPaidEmployees.get(0).getName());
    }

    @Test
    public void testFindEmployeesByJobTitle() {
        List<Employee> employees = employeeManager.findEmployeesByJobTitle("Software Engineer");
        assertEquals(1, employees.size());
        assertEquals("Saki alamin", employees.get(0).getName());
    }

    // Additional test cases

    @Test
    public void testAddRemoveEdgeCases() {
        // Test adding and removing employees when the list is empty
        EmployeeManager emptyManager = new EmployeeManager();
        assertTrue(emptyManager.addEmployee(new Employee(1, "Rafi", "IT", "Developer", 60000)));
        assertTrue(emptyManager.removeEmployee(1));

        // Test adding and removing multiple employees at once
        assertTrue(emptyManager.addEmployee(new Employee(1, "Adri", "IT", "Developer", 60000)));
        assertTrue(emptyManager.addEmployee(new Employee(2, "Mojumder", "HR", "Manager", 70000)));
        assertTrue(emptyManager.removeEmployee(1));
        assertTrue(emptyManager.removeEmployee(2));

        // Test adding an employee with the same ID as an existing employee (should fail)
        assertFalse(employeeManager.addEmployee(new Employee(1, "Anonto", "Marketing", "Marketing Manager", 70000)));

        // Test removing an employee that doesn't exist
        assertFalse(employeeManager.removeEmployee(5));
    }

    @Test
    public void testFindEmployeeByIdEdgeCases() {
        // Test finding an employee by ID when the ID does not exist
        Employee employee = employeeManager.findEmployeeById(10);
        assertNull(employee);

        // Test finding an employee by ID when there are multiple employees with the same ID (should return the first occurrence)
        employeeManager.addEmployee(new Employee(1, "Adri", "IT", "Developer", 60000));
        Employee foundEmployee = employeeManager.findEmployeeById(1);
        assertNotNull(foundEmployee);
        assertEquals("Saki alamin", foundEmployee.getName());
    }

    @Test
    public void testFindEmployeesByDepartmentEdgeCases() {
        // Test finding employees by a department that doesn't exist
        List<Employee> employees = employeeManager.findEmployeesByDepartment("Sales");
        assertEquals(0, employees.size());

        // Test finding employees when there are multiple employees in the same department
        employeeManager.addEmployee(new Employee(4, "Authay", "HR", "Assistant", 40000));
        employees = employeeManager.findEmployeesByDepartment("HR");
        assertEquals(2, employees.size());
    }

    @Test
    public void testCalculateTotalSalaryEdgeCases() {
        // Test calculating the total salary when the list is empty
        EmployeeManager emptyManager = new EmployeeManager();
        double totalSalary = emptyManager.calculateTotalSalary();
        assertEquals(0.0, totalSalary, 0.01);

        // Test calculating the total salary when all employees have zero salary
        EmployeeManager zeroSalaryManager = new EmployeeManager();
        zeroSalaryManager.addEmployee(new Employee(1, "Rakib", "IT", "Developer", 0));
        zeroSalaryManager.addEmployee(new Employee(2, "Alamin", "HR", "Manager", 0));
        totalSalary = zeroSalaryManager.calculateTotalSalary();
        assertEquals(0.0, totalSalary, 0.01);
    }

}
