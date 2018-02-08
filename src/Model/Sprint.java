package Model;

import java.math.BigInteger;
import java.util.Set;

public class Sprint extends AbstractDAOObject {
    private BigInteger projectId;
    private BigInteger previousSprintId;
    private Set<BigInteger> tasks;

    static {
        attributes.add("Name");
        attributes.add("Project ID");
        attributes.add("Previous Sprint ID");
    }

    public Sprint() {
        super();
    }

    public Sprint(BigInteger id) {
        super(id);
    }

    public Sprint(BigInteger id, Sprint sprint) {
        this(id,sprint,1);
    }

    public Sprint(BigInteger id, Sprint sprint, int version) {
        super(id);
        name = sprint.name;
        projectId = sprint.projectId;
        previousSprintId = sprint.previousSprintId;
        tasks.addAll(sprint.tasks);
        this.version = version;
    }

    public BigInteger getProjectId() {
        return projectId;
    }

    public void setProjectId(BigInteger projectId) {
        this.projectId = projectId;
    }

    public BigInteger getPreviousSprintId() {
        return previousSprintId;
    }

    public void setPreviousSprintId(BigInteger previousSprintId) {
        this.previousSprintId = previousSprintId;
    }

    public Set<BigInteger> getTasks() {
        return tasks;
    }

    public void setTasks(Set<BigInteger> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Sprint with " +
                "id = " + id +
                ", name = '" + name +
                ", projectId = " + projectId +
                ", previousSprintId = " + previousSprintId +
                ", tasks = " + tasks;
    }

    @Override
    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "name":
                return name;
            case "project id":
                return projectId.toString();
            case "previous sprint id":
                return previousSprintId.toString();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void setAttribute(String attributeName, String attributeValue) {
        switch (attributeName.toLowerCase()) {
            case "name":
                name = attributeValue;
                break;
            case "project id":
                projectId = new BigInteger(attributeValue);
                break;
            case "previous sprint id":
                previousSprintId = new BigInteger(attributeValue);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
