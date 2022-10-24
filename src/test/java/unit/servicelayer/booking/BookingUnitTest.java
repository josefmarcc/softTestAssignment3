package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.Assert.*;


import servicelayer.Booking.BookingService;
import servicelayer.Booking.BookingServiceException;
import servicelayer.Booking.BookingServiceImpl;


import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class BookingUnitTest {

    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;

    @BeforeAll
    public void beforeAll() throws BookingServiceException {
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);

    }

    @Test
    public void mustCallBookingStorageWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        var customerId = 1;
        var employeeId = 2;
        var date = new Date(2014, 3, 3);
        var start = new Time(2000);
        var end = new Time(4000);

        // Act
        bookingService.createBooking(customerId, employeeId, date, start, end);

        // Assert
        verify(storageMock, times(1)).createBooking(argThat(x -> x.getCustomerId() == customerId));
    }

    @Test
    public void getBookingsFromCustomerId() throws BookingServiceException, SQLException {
        Collection<Booking> bookings = bookingService.getBookingsForCustomer(1);
        // Assert
        assertEquals(1, bookings.size());
    }





}
