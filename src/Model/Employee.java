package Model;

import dao.IDable;
import dao.Versionable;

import java.math.BigInteger;
import java.util.Set;

public class Employee implements IDable, Versionable {
    private BigInteger id;
    private String firstName;
    private String lastName;
    private BigInteger managerId;
    private Set<BigInteger> tasks;
    private int version;

    public Employee() {
        version =1;
    }

    public Employee(BigInteger id) {
        this.id = id;
        version = 1;
    }

    public Employee(BigInteger id, Employee employee) {
        this(id,employee,1);
    }

    public Employee(BigInteger id, Employee employee, int version) {
        this.id = id;
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
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
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

}
