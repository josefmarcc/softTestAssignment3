package dto;

import java.util.Optional;

public class CustomerCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public final String firstname, lastname;

    public CustomerCreation(String firstname, String lastname, Optional<String> phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
