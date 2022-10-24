package integration.datalayer.employee;


import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class EmployeeTest extends ContainerizedDbIntegrationTest {

    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(3);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

    }


    @Test
    public void mustSaveEmployeeInDatabaseWhenCallingCreateEmployee() throws SQLException {
        // Arrange
        // Act
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn", new Date(2000,2,2)));

        // Assert
        var employees = employeeStorage.getEmployees();
        assertTrue(
                employees.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                                x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = employeeStorage.createEmployee(new EmployeeCreation("a", "b", new Date(2000,2,2)));
        var id2 = employeeStorage.createEmployee(new EmployeeCreation("c", "d", new Date(2000,2,2)));

        // Assert
        assertEquals(1, id2 - id1);
    }

    @Test
    public void getEmployeeById() throws SQLException {
        // Arrange
        //Act
        int empId = employeeStorage.createEmployee(new EmployeeCreation("Josef", "j", new Date(2000,2,2)));
        Employee emp = employeeStorage.getEmployeeWithId(empId);

        // Assert
        assertEquals(empId, emp.getId());



    }

}
