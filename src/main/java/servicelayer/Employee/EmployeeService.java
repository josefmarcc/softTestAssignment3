package servicelayer.Employee;

import dto.Employee;

import java.sql.SQLException;

public interface EmployeeService {
    int createEmployee(Employee employee) throws EmployeeServiceException;
    Employee getEmployeeById(int employeeId) throws SQLException;
}
