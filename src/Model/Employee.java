package Model;

import java.math.BigInteger;
import java.util.Set;

public class Employee extends AbstractDAOObject {
    static {
        attributes.add("First Name");
        attributes.add("Last Name");
        attributes.add("Manager ID");
        attributes.add("Task ID");
    }

    private String firstName;
    private String lastName;
    private BigInteger managerId;
    private Set<BigInteger> tasks;

    public Employee() {
        super();
    }

    public Employee(BigInteger id) {
        super(id);
    }

    public Employee(BigInteger id, Employee employee) {
        this(id, employee, 1);
    }

    public Employee(BigInteger id, Employee employee, int version) {
        super(id);
        firstName = employee.firstName;
        lastName = employee.lastName;
        managerId = new BigInteger(employee.managerId.toString());
        tasks.addAll(employee.tasks);
        this.version = version;
    }

    public Set<BigInteger> getTasks() {
        return tasks;
    }

    public void setTasks(Set<BigInteger> tasks) {
        this.tasks = tasks;
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

    public BigInteger getManagerId() {
        return managerId;
    }

    public void setManagerId(BigInteger managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Employee with " +
                "id=" + id +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", manager Id=" + managerId +
                ", tasks=" + tasks;
    }

    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "first name":
                return firstName;
            case "last name":
                return lastName;
            case "manager id":
                return managerId.toString();
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
            case "manager id":
                managerId = new BigInteger(attributeValue);
                break;
            case "task id":
                tasks.add(new BigInteger(attributeValue));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

}
