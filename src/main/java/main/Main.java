package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.BookingCreation;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import dto.EmployeeCreation;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;

public class Main {

    private static final String conStr = "jdbc:mysql://localhost:3307/booking";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl storage = new CustomerStorageImpl(conStr, user, pass);
        EmployeeStorageImpl storage2 = new EmployeeStorageImpl(conStr, user, pass);
        BookingStorageImpl storage3 = new BookingStorageImpl(conStr, user, pass);
        storage.createCustomer(new CustomerCreation("josef", "marc", Optional.empty()));

        storage2.createEmployee(new EmployeeCreation("linnea", "jahn",new Date(2014, 02, 11)));

        storage3.createBooking(new BookingCreation(1,2,new Date(2014, 2,0), new Time(2), new Time(2)));

        System.out.println(storage3.getBookingsForCustomer(1));
        System.out.println("Got customers: ");
        for(Customer c : storage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
        System.out.println("Got employee: ");

        System.out.println(storage2.getEmployeeWithId(1).getFirstname());
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }
}
