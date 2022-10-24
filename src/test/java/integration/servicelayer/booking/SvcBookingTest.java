package integration.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.Booking.BookingService;
import servicelayer.Booking.BookingServiceException;
import servicelayer.Booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

 @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SvcBookingTest extends ContainerizedDbIntegrationTest {
    private BookingService bookingService;
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;


    @BeforeAll
    public void setup() throws SQLException {
        runMigration(4);
        bookingStorage = new BookingStorageImpl(getConnectionString(),"root", getDbPassword());
        bookingService = new BookingServiceImpl(bookingStorage);

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        createFakeBooking();
    }

    public void createFakeBooking() throws SQLException {
        customerStorage.createCustomer(new CustomerCreation("test", "person", Optional.of("12345678")));
        employeeStorage.createEmployee(new EmployeeCreation("employee", "test", new Date(2000,2,2)));
        bookingStorage.createBooking(new BookingCreation(1,1,new Date(2000,2,2), new Time(20), new Time(20)));
    }

    @Test
    public void mustSaveBookingToDatabaseWhenCallingCreateBooking() throws BookingServiceException, SQLException {
        // Arrange
        int id = bookingService.createBooking(1,1,new Date(2000,2,2), new Time(20), new Time(20));

        // Act
        Collection<Booking> createdBookingCustomerId = bookingService.getBookingsForCustomer(id);
        Collection<Booking> createdBookingEmployeeId = bookingService.getBookingsForEmployee(id);


        // Assert
        assertEquals(1, createdBookingCustomerId.stream().findAny().get().getCustomerId());
        assertEquals(1,createdBookingEmployeeId.stream().findAny().get().getEmployeeId());
    }




}
