package Model;

import dao.oracle.IDable;

import java.math.BigInteger;
import java.util.Set;

public class Customer implements IDable {
    private BigInteger id;
    private String name;
    private String description;
    private Set<BigInteger> projects;

    public Customer() {

    }

    public Customer(BigInteger id) {
        this.id = id;
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
