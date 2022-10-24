package unit.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Booking;
import dto.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.Booking.BookingServiceException;
import servicelayer.Employee.EmployeeService;
import servicelayer.Employee.EmployeeServiceException;
import servicelayer.Employee.EmployeeServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class EmployeeUnitTest {

    // SUT (System Under Test)
    private EmployeeService employeeService;

    // DOC (Depended-on Component)
    private EmployeeStorage storageMock;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(storageMock);
    }

    @Test
    public void mustCallStorageWhenCreatingEmployee() throws SQLException, EmployeeServiceException {
        // Arrange
        // Act
        var firstName = "a";
        var lastName = "b";
        var birthdate = new Date(123456789l);

        employeeService.createEmployee(new Employee(1, firstName,lastName,birthdate));

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createEmployee' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .createEmployee(
                        argThat(x -> x.firstname.equals(firstName) &&
                                x.lastname.equals(lastName)));
    }
    @Test
    public void getEmployeeFromEmployeeId() throws SQLException, EmployeeServiceException {
        // Arrange
        var id = 1;
        var firstName = "a";
        var lastName = "b";
        var birthdate = new Date(123456789l);

        // Act
        employeeService.createEmployee(new Employee(id, firstName,lastName,birthdate));
        Employee employee = storageMock.getEmployeeWithId(1);

        // Assert
        assertEquals(id, employee.getId());
    }
}
