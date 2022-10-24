package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class BookingTest extends ContainerizedDbIntegrationTest {

    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;


    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), Optional.empty());
            customerStorage.createCustomer(c);
        }
    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), new Date(2000,11,2));
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    public void mustSaveBookingInDatabaseWhenCallingCreateBooking() throws SQLException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(1,1, new Date(2011,2,2), new Time(22), new Time(22)));

        // Assert
        var bookings = bookingStorage.getBookingsForCustomer(1);
        assertTrue(bookings.stream().anyMatch(x ->
                x.getCustomerId() == 1
                ));
    }

    @Test
    public void getBookingByIdWithExistingCustomer() throws SQLException {
        // Arrange
        // Act
        Collection<Booking> bookings = bookingStorage.getBookingsForCustomer(1);

        // Assert
        assertTrue(bookings.stream().anyMatch(x ->
                x.getCustomerId() == 1
        ));
    }
}