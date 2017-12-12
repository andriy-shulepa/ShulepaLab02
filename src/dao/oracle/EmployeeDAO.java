package dao.oracle;

import Model.Employee;
import dao.DAOUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmployeeDAO extends OracleAbstractDAO<Employee> {

    private static final String OBJECT_TYPE = "Employee";
    private static final String ATTRIBUTE_FIRST_NAME = "First Name";
    private static final String ATTRIBUTE_LAST_NAME = "Last Name";
    private static final String ATTRIBUTE_MANAGER_ID = "Manager ID";
    private static final String EMPLOYEE_ID = "Employee ID";
    private static final String TASK_ID = "Task ID";

    public EmployeeDAO(Roles role) {
        super(role);
    }

    @Override
    String getSelectQuery() {
        return "select o.OBJECT_ID, o.NAME,  fn_p.TEXT_VALUE as \"" + ATTRIBUTE_FIRST_NAME + "\", ln_p.TEXT_VALUE as \"" + ATTRIBUTE_LAST_NAME + "\", mgr_p.NUMBER_VALUE as \"" + ATTRIBUTE_MANAGER_ID + "\", tsk_p.OBJECT_ID as \"" + TASK_ID + "\"\n" +
                "from lab02.objects o\n" +
                "join lab02.object_types ot  on o.OBJECT_TYPE_ID=ot.OBJECT_TYPE_ID and ot.NAME = '" + OBJECT_TYPE + "' \n" +
                "join lab02.attributes fn_a on fn_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and fn_a.NAME = \'" + ATTRIBUTE_FIRST_NAME + "\' \n" +
                "left join lab02.params fn_p on fn_a.ATTRIBUTE_ID = fn_p.ATTRIBUTE_ID and fn_p.OBJECT_ID = o.OBJECT_ID\n" +
                "join lab02.attributes ln_a on ln_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and ln_a.NAME = \'" + ATTRIBUTE_LAST_NAME + "\' \n" +
                "left join lab02.params ln_p on ln_a.ATTRIBUTE_ID = ln_p.ATTRIBUTE_ID and ln_p.OBJECT_ID = o.OBJECT_ID\n" +
                "join lab02.attributes mgr_a on ln_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and mgr_a.NAME = \'" + ATTRIBUTE_MANAGER_ID + "\' \n" +
                "left join lab02.params mgr_p on mgr_a.ATTRIBUTE_ID = mgr_p.ATTRIBUTE_ID and mgr_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes tsk_a on tsk_a.name = \'" + EMPLOYEE_ID + "\' \n" +
                "left join lab02.params tsk_p on tsk_a.ATTRIBUTE_ID = tsk_p.ATTRIBUTE_ID and o.OBJECT_ID = tsk_p.NUMBER_VALUE\n";
    }

    @Override
    String getInsertQuery() {
        return "INSERT ALL " +
                "INTO LAB02.OBJECTS (OBJECT_ID, NAME, OBJECT_TYPE_ID) VALUES (?, ?, " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + ")) " +


                "INTO LAB02.PARAMS (TEXT_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_FIRST_NAME + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "INTO LAB02.PARAMS (TEXT_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_LAST_NAME + "\'" + " AND OBJECT_TYPE_ID = " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + OBJECT_TYPE + "\'" + "))) " +

                "INTO LAB02.PARAMS (NUMBER_VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                "(SELECT ATTRIBUTE_ID " +
                "FROM LAB02.ATTRIBUTES " +
                "WHERE NAME = " + "\'" + ATTRIBUTE_MANAGER_ID + "\'" + " AND OBJECT_TYPE_ID = " +
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
        String firstNameUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET TEXT_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_FIRST_NAME + "\')";
        String lastNameUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET TEXT_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_LAST_NAME + "\')";
        String managerIDUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET NUMBER_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_MANAGER_ID + "\')";
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectUpdate);
        queries.put(ATTRIBUTE_FIRST_NAME, firstNameUpdate);
        queries.put(ATTRIBUTE_LAST_NAME, lastNameUpdate);
        queries.put(ATTRIBUTE_MANAGER_ID, managerIDUpdate);
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
    BigInteger prepareStatementForInsert(PreparedStatement statement, Employee object) {
        BigInteger objectID = DAOUtils.generateID(1);
        try {
            statement.setObject(1, objectID);
            statement.setString(2, object.getFirstName() + " " + object.getLastName());
            statement.setString(3, object.getFirstName());
            statement.setObject(4, objectID);
            statement.setString(5, object.getLastName());
            statement.setObject(6, objectID);
            statement.setObject(7, object.getManagerId());
            statement.setObject(8, objectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }


    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Employee object) {
        try {
            PreparedStatement statement = statements.get(TABLE_OBJECTS);
            statement.setString(1, object.getFirstName() + " " + object.getLastName());
            statement.setObject(2, object.getId());

            statement = statements.get(ATTRIBUTE_FIRST_NAME);
            statement.setString(1, object.getFirstName());
            statement.setObject(2, object.getId());

            statement = statements.get(ATTRIBUTE_LAST_NAME);
            statement.setString(1, object.getLastName());
            statement.setObject(2, object.getId());

            statement = statements.get(ATTRIBUTE_MANAGER_ID);
            statement.setObject(1, object.getManagerId());
            statement.setObject(2, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Employee object) {
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
    Set<Employee> parseResultSet(ResultSet rs) {
        Set<Employee> employeeSet = new LinkedHashSet<>();
        Employee employee = null;
        BigInteger previousID = null;
        Set<BigInteger> tasks = null;

        try {
            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    employee = new Employee(currentID);
                    employee.setFirstName(rs.getString(ATTRIBUTE_FIRST_NAME));
                    employee.setLastName(rs.getString(ATTRIBUTE_LAST_NAME));
                    employee.setManagerId(((BigDecimal) rs.getObject(ATTRIBUTE_MANAGER_ID)).toBigInteger());
                    tasks = new HashSet<>();

                    if (rs.getObject(TASK_ID) != null) {
                        tasks.add(((BigDecimal) rs.getObject(TASK_ID)).toBigInteger());
                    }
                } else {
                    tasks.add(((BigDecimal) rs.getObject(TASK_ID)).toBigInteger());
                }
                previousID = currentID;
                employee.setTasks(tasks);
                employeeSet.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeSet;
    }
    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }
}
