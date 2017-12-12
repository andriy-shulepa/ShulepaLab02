package Model;

import dao.IDable;
import dao.Versionable;

import java.math.BigInteger;
import java.util.Set;

public class Customer implements IDable, Versionable {
    private BigInteger id;
    private String name;
    private String description;
    private Set<BigInteger> projects;
    private int version;

    public Customer() {
        version =1;

    }

    public Customer(BigInteger id) {
        this.id = id;
        version =1;
    }

    public Customer(BigInteger id, Customer customer) {
        this(id,customer,1);
    }

    public Customer(BigInteger id, Customer customer, int version) {
        this.id = id;
        name = customer.name;
        description = customer.description;
        projects.addAll(customer.projects);
        this.version = version;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version=version;
    }

    public BigInteger getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BigInteger> getProjects() {
        return projects;
    }

    public void setProjects(Set<BigInteger> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {

        return "Customer with ID = " + id +
                ", Name = " + name +
                ", Description = " + description +
                ", Projects ID = " + projects;
    }

}
