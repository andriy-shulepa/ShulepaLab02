package dao.oracle;

import Model.Project;
import dao.DAOUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProjectDAO extends OracleAbstractDAO<Project> {

    private static final String OBJECT_TYPE = "Project";
    private static final String ATTRIBUTE_START_DATE = "Start Date";
    private static final String ATTRIBUTE_END_DATE = "End Date";
    private static final String ATTRIBUTE_CUSTOMER_ID = "Customer ID";
    private static final String PROJECT_ID = "Project ID";
    private static final String SPRINT_ID = "Sprint ID";


    @Override
    String getSelectQuery() {
        return "select o.OBJECT_ID, o.NAME,  sd_p.DATE_VALUE as \""+ATTRIBUTE_START_DATE+"\", ed_p.DATE_VALUE as \""+ATTRIBUTE_END_DATE+"\", ci_p.NUMBER_VALUE as \""+ATTRIBUTE_CUSTOMER_ID+"\", sp_p.NUMBER_VALUE \""+SPRINT_ID+"\"\n" +
                "from lab02.objects o\n" +
                "join lab02.object_types ot  on o.OBJECT_TYPE_ID=ot.OBJECT_TYPE_ID and ot.NAME = '"+OBJECT_TYPE+"' \n" +
                "join lab02.attributes sd_a on sd_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and sd_a.NAME = \'" + ATTRIBUTE_START_DATE + "\' \n" +
                "left join lab02.params sd_p on sd_a.ATTRIBUTE_ID = sd_p.ATTRIBUTE_ID and sd_p.OBJECT_ID = o.OBJECT_ID\n" +
                "join lab02.attributes ed_a on ed_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and ed_a.NAME = \'" + ATTRIBUTE_END_DATE + "\' \n" +
                "left join lab02.params ed_p on ed_a.ATTRIBUTE_ID = ed_p.ATTRIBUTE_ID and ed_p.OBJECT_ID = o.OBJECT_ID\n" +
                "join lab02.attributes ci_a on ci_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and ci_a.NAME = \'" + ATTRIBUTE_CUSTOMER_ID + "\' \n" +
                "left join lab02.params ci_p on ci_a.ATTRIBUTE_ID = ci_p.ATTRIBUTE_ID and ci_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes sp_a on sp_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and sp_a.NAME = \'" + PROJECT_ID + "\' \n" +
                "left join lab02.params sp_p on sp_a.ATTRIBUTE_ID = sp_p.ATTRIBUTE_ID and sp_p.OBJECT_ID = o.OBJECT_ID";
    }

    @Override
    String getInsertQuery() {

        return "INSERT ALL " +
                "INTO LAB02.OBJECTS (OBJECT_ID, NAME, OBJECT_TYPE_ID) VALUES (?, ?, " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + ")) " +

                "INTO LAB02.PARAMS (DATE_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_START_DATE + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "INTO LAB02.PARAMS (DATE_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_END_DATE + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "INTO LAB02.PARAMS (NUMBER_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_CUSTOMER_ID + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "SELECT * FROM DUAL";
    }

    @Override
    Map<String, String> getUpdateQuery() {
        String objectUpdate = "UPDATE LAB02.OBJECTS \n" +
                "SET NAME = ?\n" +
                "WHERE OBJECT_ID = ?";
        String startDateUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET DATE_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_START_DATE + "\')";
        String endDateUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET DATE_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_END_DATE + "\')";
        String customerIDUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET NUMBER_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_CUSTOMER_ID + "\')";
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectUpdate);
        queries.put(ATTRIBUTE_START_DATE, startDateUpdate);
        queries.put(ATTRIBUTE_END_DATE, endDateUpdate);
        queries.put(ATTRIBUTE_CUSTOMER_ID, customerIDUpdate);
        return queries;

    }


    @Override
    Map<String, String> getDeleteQuery() {
        String objectDelete = "DELETE LAB02.OBJECTS \n" +
                "WHERE OBJECT_ID = ?";
        String paramsDelete = "DELETE LAB02.PARAMS\n" +
                "WHERE OBJECT_ID = ?";
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectDelete);
        queries.put(TABLE_PARAMS, paramsDelete);
        return queries;
    }


    @Override
    BigInteger prepareStatementForInsert(PreparedStatement statement, Project object) {
        BigInteger objectID = DAOUtils.generateID(1);
        try {
            statement.setObject(1, objectID);
            statement.setString(2, object.getName());
            statement.setDate(3, new Date(object.getStartDate().getTimeInMillis()));
            statement.setObject(4, objectID);
            statement.setDate(5, new Date(object.getEndDate().getTimeInMillis()));
            statement.setObject(6, objectID);
            statement.setObject(7, object.getCustomerId());
            statement.setObject(8, objectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }


    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Project object) {
        try {
            PreparedStatement statement = statements.get(TABLE_OBJECTS);
            statement.setString(1, object.getName());
            statement.setObject(2, object.getId());

            statements.get(ATTRIBUTE_START_DATE);
            statement.setDate(1, new Date(object.getStartDate().getTimeInMillis()));
            statement.setObject(2, object.getId());

            statements.get(ATTRIBUTE_END_DATE);
            statement.setDate(1, new Date(object.getEndDate().getTimeInMillis()));
            statement.setObject(2, object.getId());

            statements.get(ATTRIBUTE_CUSTOMER_ID);
            statement.setObject(1, object.getCustomerId());
            statement.setObject(2, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Project object) {
        try {
            PreparedStatement statement = statements.get(TABLE_OBJECTS);
            statement.setObject(1, object.getId());

            statement = statements.get(TABLE_PARAMS);
            statement.setObject(1, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    Set<Project> parseResultSet(ResultSet rs) {
        Set<Project> projectSet = new HashSet<>();
        Project project = null;
        BigInteger previousID = null;
        Set<BigInteger> sprints = null;
        try {

            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    project = new Project(currentID);
                    project.setName(rs.getString(OBJECT_NAME));

                    Date date = rs.getDate(ATTRIBUTE_START_DATE);
                    Calendar startDate = new GregorianCalendar();
                    startDate.setTime(date);
                    project.setStartDate(startDate);

                    date = rs.getDate(ATTRIBUTE_END_DATE);
                    Calendar endDate = new GregorianCalendar();
                    endDate.setTime(date);
                    project.setEndDate(endDate);

                    BigInteger customerID = ((BigDecimal) rs.getObject(ATTRIBUTE_CUSTOMER_ID)).toBigInteger();
                    project.setCustomerId(customerID);

                    sprints = new HashSet<>();

                    if(rs.getObject(SPRINT_ID)!=null) {
                        sprints.add(((BigDecimal) rs.getObject(SPRINT_ID)).toBigInteger());
                    }
                } else {
                    sprints.add(((BigDecimal) rs.getObject(SPRINT_ID)).toBigInteger());
                }

                previousID = currentID;
                project.setSprints(sprints);
                projectSet.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectSet;
    }
}
