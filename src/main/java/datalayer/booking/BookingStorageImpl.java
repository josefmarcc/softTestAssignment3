package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage{

    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String username, String password) {
        this.connectionString = conStr;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    @Override
    public int createBooking(BookingCreation bookingToCreate) throws SQLException{
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingToCreate.getCustomerId());
            stmt.setInt(2, bookingToCreate.getEmployeeId());
            stmt.setDate(3, bookingToCreate.getDate());
            stmt.setTime(4, bookingToCreate.getStart());
            stmt.setTime(5, bookingToCreate.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }


    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
         try (var con = getConnection();
                          var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings where customerId = customerId")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int cId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking b = new Booking(id, cId,employeeId,date, start, end);
                    results.add(b);
                }
            }

            return results;
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings where employeeId = employeeId")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int cId = resultSet.getInt("customerId");
                    int eId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking b = new Booking(id, cId,eId,date, start, end);
                    results.add(b);
                }
            }

            return results;
        }
    }
}


