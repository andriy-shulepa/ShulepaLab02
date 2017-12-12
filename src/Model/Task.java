package Model;

import dao.IDable;
import dao.Versionable;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Set;

public class Task implements IDable, Versionable {
    private BigInteger id;
    private String name;
    private BigInteger sprintId;
    private BigInteger parentTaskId;
    private Set<BigInteger> subtasks;
    private Calendar estimate;
    private Qualification qualification;
    private Set<BigInteger> employees;
    private int version;

    public Task() {
        version =1;
    }

    public Task(BigInteger id) {
        this.id = id;
        version =1;
    }
    public Task(BigInteger id, Task task) {
        this(id,task,1);
    }
    public Task(BigInteger id, Task task, int version) {
        this.id = id;
        name = task.name;
        sprintId=task.sprintId;
        subtasks.addAll(task.subtasks);
        estimate=(Calendar) task.estimate.clone();
        qualification=task.qualification;
        employees.addAll(task.employees);
        this.version = version;
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

    public Calendar getEstimate() {
        return estimate;
    }

    public void setEstimate(Calendar estimate) {
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
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
