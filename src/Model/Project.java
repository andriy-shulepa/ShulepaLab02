package Model;

import dao.IDable;
import dao.Versionable;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Set;

public class Project implements IDable, Versionable {
    private BigInteger id;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private BigInteger customerId;
    private Set<BigInteger> sprints;
    private int version;

    public Project() {
        version =1;
    }

    public Project(BigInteger id) {
        this.id = id;
        version =1;
    }

    public Project(BigInteger id, Project project) {
        this(id,project,1);
    }

    public Project(BigInteger id, Project project, int version) {
        this.id = id;
        name = project.name;
        startDate = (Calendar) project.startDate.clone();
        endDate = (Calendar) project.endDate.clone();
        customerId = project.customerId;
        sprints.addAll(project.sprints);
        this.version = version;
    }

    public Set<BigInteger> getSprints() {
        return sprints;
    }

    public void setSprints(Set<BigInteger> sprints) {
        this.sprints = sprints;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customer_id) {
        this.customerId = customer_id;
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
        return "Project with Name = " + name +
                ", Start Date = " + getDate(startDate) +
                ", End Date = " + getDate(endDate) +
                ", Customer ID = " + customerId +
                ", Sprints ID = " + sprints;
    }

    private String getDate(Calendar calendar) {
        return String.format("%02d/%02d/%04d", calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
    }

}
