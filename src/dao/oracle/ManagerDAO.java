package dao.oracle;

import Model.Manager;
import dao.DAOUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ManagerDAO extends OracleAbstractDAO<Manager> {

    private static final String OBJECT_TYPE = "Manager";
    private static final String ATTRIBUTE_FIRST_NAME = "First Name";
    private static final String ATTRIBUTE_LAST_NAME = "Last Name";
    private static final String MANAGER_ID = "Manager ID";
    private static final String EMPLOYEE_ID = "Employee ID";

    public ManagerDAO(Roles role) {
        super(role);
    }

    @Override
    String getSelectQuery() {
        return "select o.OBJECT_ID, o.NAME,  fn_p.TEXT_VALUE as \"" + ATTRIBUTE_FIRST_NAME + "\", ln_p.TEXT_VALUE as \"" + ATTRIBUTE_LAST_NAME + "\", emp_p.OBJECT_ID as \"" + EMPLOYEE_ID + "\"\n" +
                "from lab02.objects o\n" +
                "join lab02.object_types ot  on o.OBJECT_TYPE_ID=ot.OBJECT_TYPE_ID and ot.NAME = '"+OBJECT_TYPE+"' \n" +
                "join lab02.attributes fn_a on fn_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and fn_a.NAME = \'" + ATTRIBUTE_FIRST_NAME + "\' \n" +
                "left join lab02.params fn_p on fn_a.ATTRIBUTE_ID = fn_p.ATTRIBUTE_ID and fn_p.OBJECT_ID = o.OBJECT_ID\n" +
                "join lab02.attributes ln_a on ln_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and ln_a.NAME = \'" + ATTRIBUTE_LAST_NAME + "\' \n" +
                "left join lab02.params ln_p on ln_a.ATTRIBUTE_ID = ln_p.ATTRIBUTE_ID and ln_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes emp_a on emp_a.name = \'" + MANAGER_ID + "\' \n" +
                "left join lab02.params emp_p on emp_a.ATTRIBUTE_ID = emp_p.ATTRIBUTE_ID and o.OBJECT_ID = emp_p.NUMBER_VALUE\n";

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
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectUpdate);
        queries.put(ATTRIBUTE_FIRST_NAME, firstNameUpdate);
        queries.put(ATTRIBUTE_LAST_NAME, lastNameUpdate);
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
    BigInteger prepareStatementForInsert(PreparedStatement statement, Manager object) {
        BigInteger objectID = DAOUtils.generateID(1);
        try {
            statement.setObject(1, objectID);
            statement.setString(2, object.getFirstName() + " " + object.getLastName());
            statement.setString(3, object.getFirstName());
            statement.setObject(4, objectID);
            statement.setString(5, object.getLastName());
            statement.setObject(6, objectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }


    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Manager object) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Manager object) {
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
    Set<Manager> parseResultSet(ResultSet rs) {
        Set<Manager> customerSet = new LinkedHashSet<>();
        Manager manager = null;
        BigInteger previousID = null;
        Set<BigInteger> employees = null;

        try {
            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    manager = new Manager(currentID);
                    manager.setFirstName(rs.getString(ATTRIBUTE_FIRST_NAME));
                    manager.setLastName(rs.getString(ATTRIBUTE_LAST_NAME));
                    employees = new HashSet<>();

                    if (rs.getObject(EMPLOYEE_ID) != null) {
                        employees.add(((BigDecimal) rs.getObject(EMPLOYEE_ID)).toBigInteger());
                    }
                } else {
                    employees.add(((BigDecimal) rs.getObject(EMPLOYEE_ID)).toBigInteger());
                }
                previousID = currentID;
                manager.setEmployees(employees);
                customerSet.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerSet;

    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }
}
