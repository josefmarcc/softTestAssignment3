package dto;

import java.sql.Date;

public class EmployeeCreation {

    public final String firstname, lastname;
    public final Date birthDate;

    public EmployeeCreation(String firstname, String lastname, Date birthDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
