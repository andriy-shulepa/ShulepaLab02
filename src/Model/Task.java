package Model;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class Task extends AbstractDAOObject {
    static {
        attributes.add("Name");
        attributes.add("Sprint ID");
        attributes.add("Parent Task ID");
        attributes.add("Estimate");
        attributes.add("Qualification");
        attributes.add("Employee ID");

        foreignAttributes.add(new ForeignAttributeType("Subtasks", "Parent Task ID", "Task"));
    }

    private BigInteger sprintId;
    private BigInteger parentTaskId;
    private Set<BigInteger> subtasks = new LinkedHashSet<>();
    private int estimate;
    private Qualification qualification;
    private Set<BigInteger> employees = new LinkedHashSet<>();

    public Task() {
        super();
    }

    public Task(BigInteger id) {
        super(id);
    }

    public Task(BigInteger id, Task task) {
        this(id, task, 1);
    }

    public Task(BigInteger id, Task task, int version) {
        super(id);
        name = task.name;
        sprintId = task.sprintId;
        subtasks.addAll(task.subtasks);
        estimate = task.estimate;
        qualification = task.qualification;
        employees.addAll(task.employees);
        this.version = version;
    }


    public BigInteger getSprintId() {
        return sprintId;
    }

    public void setSprintId(BigInteger sprintId) {
        this.sprintId = sprintId;
    }

    public BigInteger getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(BigInteger parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Set<BigInteger> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Set<BigInteger> subtasks) {
        this.subtasks = subtasks;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Set<BigInteger> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<BigInteger> employees) {
        this.employees = employees;
    }


    @Override
    public String toString() {
        return "Task with " +
                "id = " + id +
                ", name = " + name +
                ", parent task ID = " + parentTaskId +
                ", estimate = " + estimate +
                ", needed qualification  = " + qualification +
                ", subtasks = " + subtasks +
                ", employees" + employees;
    }


    @Override
    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "name":
                return name;
            case "sprint id":
                return sprintId.toString();
            case "parent task id":
                return parentTaskId.toString();
            case "estimate":
                return Integer.toString(estimate);
            case "qualification":
                return qualification.toString();
            case "employee id":
                return setToString(employees);
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
            case "sprint id":
                sprintId = new BigInteger(attributeValue);
                break;
            case "parent task id":
                parentTaskId = new BigInteger(attributeValue);
                break;
            case "estimate":
                estimate = Integer.parseInt(attributeValue);
                break;
            case "qualification":
                qualification = Qualification.valueOf(attributeValue);
                break;
            case "subtasks":
                subtasks = stringToSet(attributeValue);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
