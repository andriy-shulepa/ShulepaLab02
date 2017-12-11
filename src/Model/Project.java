package Model;

import dao.oracle.IDable;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Set;

public class Project implements IDable {
    private BigInteger id;
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private BigInteger customer_id;
    private Set<BigInteger> sprints;

    public Project() {
    }

    public Project(BigInteger id) {
        this.id = id;
    }

    public Project(BigInteger id, Project project) {
        this.id = id;
        name = project.name;
        startDate = (Calendar) project.startDate.clone();
        endDate = (Calendar) project.endDate.clone();
        customer_id = project.customer_id;
        sprints.addAll(project.sprints);
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
        return customer_id;
    }

    public void setCustomerId(BigInteger customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Project with Name = " + name +
                ", Start Date = " + getDate(startDate) +
                ", End Date = " + getDate(endDate) +
                ", Customer ID = " + customer_id +
                ", Sprints ID = " + sprints;
    }

    private String getDate(Calendar calendar) {
        return String.format("%02d/%02d/%04d", calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
    }

}
