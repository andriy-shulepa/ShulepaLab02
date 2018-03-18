package Model;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class Manager extends AbstractDAOObject {

    static {
        attributes.add("First Name");
        attributes.add("Last Name");

        foreignAttributes.add(new ForeignAttributeType("Employees", "Manager ID", "Employee"));
    }

    private String firstName;
    private String lastName;
    private Set<BigInteger> employees = new LinkedHashSet<>();

    public Manager() {
        super();
    }

    public Manager(BigInteger id) {
        super(id);
    }

    public Manager(BigInteger id, Manager manager) {
        this(id, manager, 1);
    }

    public Manager(BigInteger id, Manager manager, int version) {
        super(id);
        firstName = manager.firstName;
        lastName = manager.lastName;
        employees.addAll(manager.employees);
        this.version = version;
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

    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "first name":
                return firstName;
            case "last name":
                return lastName;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void setAttribute(String attributeName, String attributeValue) {
        switch (attributeName.toLowerCase()) {
            case "first name":
                firstName = attributeValue;
                break;
            case "last name":
                lastName = attributeValue;
                break;
            case "employees":
                employees = stringToSet(attributeValue);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
