package dao.oracle;

import Model.Customer;
import dao.DAOUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomerDAO extends OracleAbstractDAO<Customer> {
    private static final String OBJECT_TYPE = "Customer";
    private static final String ATTRIBUTE_DESCRIPTION = "Description";
    private static final String CUSTOMER_ID = "Customer ID";
    private static final String PROJECT_ID = "Project ID";


    @Override
    String getSelectQuery() {
        return "select o.OBJECT_ID, o.NAME,  d_p.TEXT_VALUE as \""+ATTRIBUTE_DESCRIPTION+"\", pr_p.OBJECT_ID as \""+PROJECT_ID+"\"\n" +
                "from lab02.objects o\n" +
                "join lab02.object_types ot  on o.OBJECT_TYPE_ID=ot.OBJECT_TYPE_ID and ot.NAME = '" + OBJECT_TYPE + "' \n" +
                "join lab02.attributes d_a on d_a.OBJECT_TYPE_ID = ot.OBJECT_TYPE_ID and d_a.NAME = \'" + ATTRIBUTE_DESCRIPTION + "\' \n" +
                "left join lab02.params d_p on d_a.ATTRIBUTE_ID = d_p.ATTRIBUTE_ID and d_p.OBJECT_ID = o.OBJECT_ID\n" +
                "left join lab02.attributes pr_a on pr_a.name = \'" + CUSTOMER_ID + "\' \n" +
                "left join lab02.params pr_p on pr_a.ATTRIBUTE_ID = pr_p.ATTRIBUTE_ID and o.OBJECT_ID = pr_p.NUMBER_VALUE\n";

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
                "WHERE NAME = " + "\'" + ATTRIBUTE_DESCRIPTION + "\'" + " AND OBJECT_TYPE_ID = " +
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
        String descriptionUpdate = "UPDATE LAB02.PARAMS\n" +
                "SET TEXT_VALUE = ?" +
                "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + ATTRIBUTE_DESCRIPTION + "\')";
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectUpdate);
        queries.put(ATTRIBUTE_DESCRIPTION, descriptionUpdate);
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
    BigInteger prepareStatementForInsert(PreparedStatement statement, Customer object) {
        BigInteger objectID = DAOUtils.generateID(1);
        try {
            statement.setObject(1, objectID);
            statement.setString(2, object.getName());
            statement.setString(3, object.getDescription());
            statement.setObject(4, objectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }

    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Customer object) {
        try {
            PreparedStatement statement = statements.get(TABLE_OBJECTS);
            statement.setString(1, object.getName());
            statement.setObject(2, object.getId());

            statement = statements.get(TABLE_PARAMS);
            statement.setString(1, object.getDescription());
            statement.setObject(2, object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Customer object) {
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
    Set<Customer> parseResultSet(ResultSet rs) {
        Set<Customer> customerSet = new LinkedHashSet<>();
        Customer customer = null;
        BigInteger previousID = null;
        Set<BigInteger> projects = null;

        try {

            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    customer = new Customer(currentID);
                    customer.setName(rs.getString(OBJECT_NAME));
                    customer.setDescription(rs.getString(ATTRIBUTE_DESCRIPTION));
                    projects = new HashSet<>();

                    if(rs.getObject(PROJECT_ID)!=null) {
                        projects.add(((BigDecimal) rs.getObject(PROJECT_ID)).toBigInteger());
                    }
                } else {
                    projects.add(((BigDecimal) rs.getObject(PROJECT_ID)).toBigInteger());
                }
                previousID = currentID;
                customer.setProjects(projects);
                customerSet.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerSet;
    }


}
