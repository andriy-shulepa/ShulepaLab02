package Model;

import dao.IDable;
import dao.Versionable;

import java.math.BigInteger;
import java.util.Set;

public class Manager implements IDable, Versionable {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private Set<BigInteger> employees;
    private int version;

    public Manager() {
    }

    public Manager(BigInteger id) {
        this.id = id;
        version =1;
    }

    public Manager(BigInteger id, Manager manager) {
        this(id,manager,1);
    }

    public Manager(BigInteger id, Manager manager, int version) {
        this.id = id;
        firstName = manager.firstName;
        lastName = manager.lastName;
        employees.addAll(manager.employees);
        this.version = version;
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
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
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
