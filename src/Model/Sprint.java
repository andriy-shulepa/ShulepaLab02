package Model;

import dao.oracle.IDable;

import java.math.BigInteger;
import java.util.Set;

public class Sprint implements IDable {
    private BigInteger id;
    private String name;
    private BigInteger projectId;
    private BigInteger previousSprintId;
    private Set<BigInteger> tasks;

    public Sprint() {
    }

    public Sprint(BigInteger id) {

        this.id = id;
    }

    public Sprint(BigInteger id, Sprint sprint) {
        this.id = id;
        name = sprint.name;
        projectId = sprint.projectId;
        previousSprintId = sprint.previousSprintId;
        tasks.addAll(sprint.tasks);
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
                ", name = '" + name + '\'' +
                ", projectId = " + projectId +
                ", previousSprintId = " + previousSprintId +
                ", tasks = " + tasks;
    }

}
