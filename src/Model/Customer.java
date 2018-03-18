package Model;


import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class Customer extends AbstractDAOObject {

    static {
        attributes.add("Name");
        attributes.add("Description");

        foreignAttributes.add(new ForeignAttributeType("Projects", "Customer ID", "Project"));
    }

    private String description;
    private Set<BigInteger> projects = new LinkedHashSet<>();

    public Customer() {
        super();

    }

    public Customer(BigInteger id) {
        super(id);
    }

    public Customer(BigInteger id, Customer customer) {
        this(id, customer, 1);
    }

    public Customer(BigInteger id, Customer customer, int version) {
        super(id);
        name = customer.name;
        description = customer.description;
        projects.addAll(customer.projects);
        this.version = version;
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

    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "name":
                return name;
            case "description":
                return description;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void setAttribute(String attributeName, String attributeValue) {
        switch (attributeName.toLowerCase()) {
            case "name":
                name = attributeValue;
                break;
            case "description":
                description = attributeValue;
                break;
            case "projects":
                projects = stringToSet(attributeValue);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

}
