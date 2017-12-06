package dao.oracle;

import Model.Sprint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SprintDAO extends OracleAbstractDAO<Sprint> {

    private static final String OBJECT_TYPE = "Sprint";
    private static final String ATTRIBUTE_PREVIOUS_SPRINT_ID = "Previous Sprint ID";
    private static final String ATTRIBUTE_PROJECT_ID = "Project ID";
    private static final String SPRINT_ID = "Sprint ID";
    private static final String TASK_ID = "Task ID";

    @Override
    String getSelectQuery() {
        return "select /*+ RESULT_CACHE */ o.OBJECT_ID, o.NAME,  prs_p.NUMBER_VALUE as \"" + ATTRIBUTE_PREVIOUS_SPRINT_ID + "\", prj_p.NUMBER_VALUE as \"" + ATTRIBUTE_PROJECT_ID + "\", tsk_p.OBJECT_ID as \"" + TASK_ID + "\"\n" +
                "from lab02.objects o\n" +
                "join lab02.object_types ot  on o.OBJECT_TYPE_ID=ot.OBJECT_TYPE_ID and ot.NAME = '" + OBJECT_TYPE + "' \n" +
                "join lab02.attributes prs_a on prs_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and prs_a.NAME = '" + ATTRIBUTE_PREVIOUS_SPRINT_ID + "' \n" +
                "left join lab02.params prs_p on prs_a.ATTRIBUTE_ID = prs_p.ATTRIBUTE_ID and prs_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes prj_a on prj_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and prj_a.NAME = '" + ATTRIBUTE_PROJECT_ID + "' \n" +
                "left join lab02.params prj_p on prj_a.ATTRIBUTE_ID = prj_p.ATTRIBUTE_ID and prj_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes tsk_a on tsk_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and tsk_a.NAME = '" + SPRINT_ID + "' \n" +
                "left join lab02.params tsk_p on tsk_a.ATTRIBUTE_ID = tsk_p.ATTRIBUTE_ID and tsk_p.OBJECT_ID = o.OBJECT_ID";
    }

    @Override
    String getInsertQuery() {
        return "INSERT ALL " +
                "INTO LAB02.OBJECTS (OBJECT_ID, NAME, OBJECT_TYPE_ID) VALUES (?, ?, " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + ")) " +


                "INTO LAB02.PARAMS (NUMBER_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_PREVIOUS_SPRINT_ID + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "INTO LAB02.PARAMS (NUMBER_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_PROJECT_ID + "\'" + " AND OBJECT_TYPE_ID = " +
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
        String previousSprintUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET NUMBER_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_PREVIOUS_SPRINT_ID + "\')";
        String projectIDUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET NUMBER_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_PROJECT_ID + "\')";

        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectUpdate);
        queries.put(ATTRIBUTE_PREVIOUS_SPRINT_ID, previousSprintUpdate);
        queries.put(ATTRIBUTE_PROJECT_ID, projectIDUpdate);

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
    BigInteger prepareStatementForInsert(PreparedStatement statement, Sprint object) {
        BigInteger objectID = generateID(1);
        try {
            statement.setObject(1, objectID);
            statement.setString(2, object.getName());
            statement.setObject(3, object.getPreviousSprintId());
            statement.setObject(4, objectID);
            statement.setObject(5, object.getProjectId());
            statement.setObject(6, objectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }


    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Sprint object) {
        try {
            PreparedStatement statement = statements.get(TABLE_OBJECTS);
            statement.setString(1, object.getName());
            statement.setObject(2, object.getId());

            statements.get(ATTRIBUTE_PREVIOUS_SPRINT_ID);
            statement.setObject(1, object.getPreviousSprintId());
            statement.setObject(2, object.getId());

            statements.get(ATTRIBUTE_PROJECT_ID);
            statement.setObject(1, object.getProjectId());
            statement.setObject(2, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Sprint object) {
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
    Set<Sprint> parseResultSet(ResultSet rs) {
        Set<Sprint> sprintSet = new LinkedHashSet<>();
        Sprint sprint = null;
        BigInteger previousID = null;
        Set<BigInteger> tasks = null;

        try {

            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    sprint = new Sprint(currentID);
                    sprint.setName(rs.getString(OBJECT_NAME));
                    if (rs.getObject(ATTRIBUTE_PREVIOUS_SPRINT_ID) != null) {
                        sprint.setPreviousSprintId(((BigDecimal) rs.getObject(ATTRIBUTE_PREVIOUS_SPRINT_ID)).toBigInteger());
                    }
                    sprint.setProjectId(((BigDecimal) rs.getObject(ATTRIBUTE_PROJECT_ID)).toBigInteger());
                    tasks = new HashSet<>();
                    if (rs.getObject(TASK_ID) != null) {
                        tasks.add(((BigDecimal) rs.getObject(TASK_ID)).toBigInteger());
                    }
                } else {
                    tasks.add(((BigDecimal) rs.getObject(TASK_ID)).toBigInteger());
                }
                previousID = currentID;
                sprint.setTasks(tasks);
                sprintSet.add(sprint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sprintSet;
    }
}
