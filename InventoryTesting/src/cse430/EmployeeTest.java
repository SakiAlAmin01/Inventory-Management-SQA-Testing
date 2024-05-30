package cse430;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee(1, "Saki Al Amin", "IT", "Software Engineer", 50000.0);
    }

    @Test
    public void testGetId() {
        assertEquals(1, employee.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Saki Al Amin", employee.getName());
    }

    @Test
    public void testGetDepartment() {
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    public void testGetJobTitle() {
        assertEquals("Software Engineer", employee.getJobTitle());
    }

    @Test
    public void testGetSalary() {
        assertEquals(50000.0, employee.getSalary(), 0.01); // delta is the tolerance for floating point comparison
    }

    @Test
    public void testSetId() {
        employee.setId(2);
        assertEquals(2, employee.getId());
    }

    @Test
    public void testSetName() {
        employee.setName("Jane Doe");
        assertEquals("Jane Doe", employee.getName());
    }

    @Test
    public void testSetDepartment() {
        employee.setDepartment("HR");
        assertEquals("HR", employee.getDepartment());
    }

    @Test
    public void testSetJobTitle() {
        employee.setJobTitle("HR Manager");
        assertEquals("HR Manager", employee.getJobTitle());
    }

    @Test
    public void testSetSalary() {
        employee.setSalary(60000.0);
        assertEquals(60000.0, employee.getSalary(), 0.01); // delta is the tolerance for floating point comparison
    }

    @Test
    public void testEqualityAndHashCode() {
        Employee employee1 = new Employee(1, "Saki Al Amin", "IT", "Software Engineer", 50000.0);
        Employee employee2 = new Employee(1, "Mohit Mojumder", "IT", "HR", 40000.0);

        assertEquals(employee1, employee2);
        assertEquals(employee1.hashCode(), employee2.hashCode());

        // Modifying one attribute should not affect equality
        employee2.setSalary(60000.0);
        assertEquals(employee1, employee2);
        assertEquals(employee1.hashCode(), employee2.hashCode());

        // Changing ID should result in inequality
        employee2.setId(2);
        assertNotEquals(employee1, employee2);
    }

    @Test
    public void testImmutableBehavior() {
        Employee originalEmployee = new Employee(1, "Saki Al Amin", "IT", "Software Engineer", 50000.0);
        
        // Create a modified copy of the original employee with the new ID
        Employee modifiedEmployee = new Employee(2, originalEmployee.getName(), originalEmployee.getDepartment(),
                                                  originalEmployee.getJobTitle(), originalEmployee.getSalary());
        
        // Original object remains unchanged
        assertEquals(1, originalEmployee.getId());
        
        // Modified object has the new ID
        assertEquals(2, modifiedEmployee.getId());
    }


    @Test
    public void testNullAndEmptyStrings() {
        // Test setting null and empty strings for name, department, and job title
        employee.setName(null);
        employee.setDepartment("");
        employee.setJobTitle("  ");

        // Ensure getters return appropriate values
        assertEquals(null, employee.getName());
        assertEquals("", employee.getDepartment());
        assertEquals("  ", employee.getJobTitle());
    }

}
