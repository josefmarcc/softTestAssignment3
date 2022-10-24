package integration.servicelayer.employee;

import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.Employee.EmployeeService;
import servicelayer.Employee.EmployeeServiceException;
import servicelayer.Employee.EmployeeServiceImpl;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SvcEmployeeTest extends ContainerizedDbIntegrationTest {
    private EmployeeService employeeService;
    private EmployeeStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(4);
        storage = new EmployeeStorageImpl(getConnectionString(),"root", getDbPassword());
        employeeService = new EmployeeServiceImpl(storage);
    }

    @Test
    public void mustSaveEmployeeToDatabaseWhenCallingCreateEmployee() throws SQLException, EmployeeServiceException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        Employee employee = new Employee(1, firstName, lastName, new Date(2000,2,2));
        int id = employeeService.createEmployee(employee);

        // Act
        var createdEmployee = storage.getEmployeeWithId(id);

        // Assert
        assertEquals(firstName, createdEmployee.getFirstname());
        assertEquals(lastName, createdEmployee.getLastname());
    }


    @Test
    public void getEmployeeById() throws CustomerServiceException, SQLException, EmployeeServiceException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var id = employeeService.createEmployee(new Employee(1,firstName,lastName,new Date(2000,2,2)));

        // Act
        var e = employeeService.getEmployeeById(id);

        // Assert
        assertEquals(firstName, e.getFirstname());
        assertEquals(lastName, e.getLastname());
    }





}
