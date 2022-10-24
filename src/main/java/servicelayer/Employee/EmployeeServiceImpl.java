package servicelayer.Employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }


    @Override
    public int createEmployee(Employee employee) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(employee.getFirstname(), employee.getLastname(), employee.getBirthdate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException {
        return employeeStorage.getEmployeeWithId(employeeId);
    }
}
