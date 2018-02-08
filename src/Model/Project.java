package Model;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class Project extends AbstractDAOObject {
    static {
        attributes.add("Name");
        attributes.add("Start Date");
        attributes.add("End Date");
        attributes.add("Customer ID");
    }

    private Calendar startDate;
    private Calendar endDate;
    private BigInteger customerId;
    private Set<BigInteger> sprints;

    public Project() {
        super();
    }

    public Project(BigInteger id) {
        super(id);
    }

    public Project(BigInteger id, Project project) {
        this(id, project, 1);
    }

    public Project(BigInteger id, Project project, int version) {
        super(id);
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

    private Calendar getCalendar(String date) {
        String[] arr = date.split("/");
        Calendar calendar = new GregorianCalendar();
        calendar.set(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[0]));
        return calendar;
    }

    @Override
    public String getAttribute(String attributeName) {
        switch (attributeName.toLowerCase()) {
            case "name":
                return name;
            case "customer id":
                return customerId.toString();
            case "start date":
                return getDate(startDate);
            case "end date":
                return getDate(endDate);
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
            case "customer id":
                customerId = new BigInteger(attributeValue);
                break;
            case "start date":
                startDate = getCalendar(attributeValue);
                break;
            case "end date":
                endDate = getCalendar(attributeValue);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
