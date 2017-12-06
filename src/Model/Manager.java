package Model;

import dao.oracle.IDable;

import java.math.BigInteger;
import java.util.Set;

public class Manager implements IDable {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private Set<BigInteger> employees;

    public Manager() {
    }

    public Manager(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BigInteger> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<BigInteger> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Manager with " +
                "id=" + id +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", employees=" + employees;
    }
}
