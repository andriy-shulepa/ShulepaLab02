package dao.oracle;


import Model.AbstractDAOObject;
import Model.ForeignAttributeType;
import dao.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class OracleAbstractDAO<E extends AbstractDAOObject> implements GenericDAO<E> {
    private static final String TABLE_OBJECTS = "LAB02.OBJECTS";
    private static final String TABLE_PARAMS = "LAB02.PARAMS";
    private static final String OBJECT_ID = "OBJECT_ID";

    private Roles ROLE;
    private Cache<E> cache = new Cache<>();

    public OracleAbstractDAO(Roles role) {
        this.ROLE = role;
    }

    @Override
    public E getByPK(BigInteger id) throws IllegalRoleException {
        checkReadGrant(id.toString());
        if (cache.isActual(id)) {
            return cache.get(id);
        }
        Set<E> set;
        E object = null;
        String sql = getSelectQuery();
        sql += " WHERE o.object_id = ? \n" +
                "ORDER BY p.attribute_id";
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            set = parseResultSet(rs);

            if (set == null || set.size() == 0) {
                return null;
            }
            object = set.iterator().next();
            fillWithForeignAttributes(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        cache.add(object);
        return object;
    }


    @Override
    public Set<E> getAll() throws IllegalRoleException {
        checkReadGrant("(select o.OBJECT_ID" +
                " from lab02.objects" +
                " where OBJECT_TYPE_ID = (select OBJECT_TYPE_ID" +
                " from lab02.object_types" +
                " where name = '" + getObjectType() + "') )");
        Set<E> set = null;
        String sql = getSelectQuery();
        sql += " where o.object_type_id = (Select object_type_id from lab02.object_types where name ='" + getObjectType() + "')" +
                " ORDER BY o.OBJECT_ID";
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            set = parseResultSet(rs);

            for (E object : set) {
                fillWithForeignAttributes(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (set != null) {
            for (E item : set) {
                cache.add(item);
            }
        }
        return set;
    }

    @Override
    public BigInteger insert(E object) throws IllegalRoleException {
        checkWriteGrant(object.getId());

        Map<String, String> sql = getInsertQuery(object.getAttributesSet());

        BigInteger ID = null;

        try (Connection connection = OracleDAOFactory.createConnection()) {
            connection.setAutoCommit(false);

            Map<String, PreparedStatement> statements = new HashMap<>();
            for (String key : sql.keySet()) {
                statements.put(key, connection.prepareStatement(sql.get(key)));
            }
            ID = prepareStatementForInsert(statements, object);

            for (PreparedStatement statement : statements.values()) {
                statement.executeUpdate();
            }
            connection.commit();

            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

    @Override
    public void update(E object) throws OutdatedObjectVersionException, IllegalRoleException {
        checkWriteGrant(object.getId());

        if (!cache.isCorrectVersion(object)) {
            throw new OutdatedObjectVersionException();
        }

        Map<String, String> sql = getUpdateQuery(object.getAttributesSet());
        try (Connection connection = OracleDAOFactory.createConnection()) {
            connection.setAutoCommit(false);

            Map<String, PreparedStatement> statements = new HashMap<>();
            for (String key : sql.keySet()) {
                statements.put(key, connection.prepareStatement(sql.get(key)));
            }

            prepareStatementForUpdate(statements, object);
            for (PreparedStatement statement : statements.values()) {
                statement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
            cache.update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(E object) throws IllegalRoleException {
        checkWriteGrant(object.getId());
        Map<String, String> sql = getDeleteQuery();
        try (Connection connection = OracleDAOFactory.createConnection()) {
            connection.setAutoCommit(false);

            Map<String, PreparedStatement> statements = new HashMap<>();
            for (String key : sql.keySet()) {
                statements.put(key, connection.prepareStatement(sql.get(key)));
            }

            prepareStatementForDelete(statements, object);
            for (PreparedStatement statement : statements.values()) {
                statement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);

            cache.remove(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BigInteger prepareStatementForInsert(Map<String, PreparedStatement> statements, E object) {
        BigInteger objectID = DAOUtils.generateID(1);
        Set<String> keys = statements.keySet();
        try {
            for (String key : keys) {
                PreparedStatement statement = statements.get(key);
                if (key.equals(TABLE_OBJECTS)) {
                    statement.setObject(1, object.getName());
                } else {
                    statement.setObject(1, object.getAttribute(key));
                }

                statement.setObject(2, objectID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectID;
    }

    private void prepareStatementForUpdate(Map<String, PreparedStatement> statements, E object) {
        Set<String> keys = statements.keySet();
        try {
            for (String key : keys) {
                PreparedStatement statement = statements.get(key);
                if (key.equals(TABLE_OBJECTS)) {
                    statement.setObject(1, object.getName());
                } else {
                    statement.setObject(1, object.getAttribute(key));
                }

                statement.setObject(2, object.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void prepareStatementForDelete(Map<String, PreparedStatement> statements, E object) {
        Set<String> keySet = statements.keySet();
        try {
            for (String key : keySet) {
                PreparedStatement statement = statements.get(key);
                statement.setObject(1, object.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Set<E> parseResultSet(ResultSet rs) {
        Set<E> objectsSet = new LinkedHashSet<>();
        E object = null;
        BigInteger previousID = null;
        try {
            while (rs.next()) {
                BigInteger currentID = ((BigDecimal) rs.getObject(OBJECT_ID)).toBigInteger();
                if (!currentID.equals(previousID)) {
                    if (object != null) {
                        objectsSet.add(object);
                    }
                    object = getObject(currentID);
                }
                String attributeName = rs.getString("NAME");
                String attributeValue = rs.getString("VALUE");
                object.setAttribute(attributeName, attributeValue);
                previousID = currentID;
            }
            objectsSet.add(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objectsSet;
    }

    private String getSelectQuery() {
        return "select o.object_id, attr.name, p.VALUE\n" +
                "from lab02.objects o\n" +
                "join lab02.attributes attr on attr.object_type_id = (Select object_type_id from lab02.object_types where name =  '" + getObjectType() + "')\n" +
                "left join lab02.params p on p.attribute_id = attr.attribute_id\n" +
                "and p.object_id = o.object_id";
    }

    private Map<String, String> getInsertQuery(Set<String> attributesSet) {
        Map<String, String> queries = new HashMap<>();
        String objectInsert = "INSERT INTO LAB02.OBJECTS (NAME, OBJECT_ID, OBJECT_TYPE_ID) VALUES (?, ?, " +
                "(SELECT OBJECT_TYPE_ID " +
                "FROM LAB02.OBJECT_TYPES " +
                "WHERE NAME=" + "\'" + getObjectType() + "\'" + ")) ";
        queries.put(TABLE_OBJECTS, objectInsert);
        for (String attribute : attributesSet) {
            String attributeInsert = "INSERT INTO LAB02.PARAMS (VALUE, OBJECT_ID, ATTRIBUTE_ID) VALUES (?, ?, " +
                    "(SELECT ATTRIBUTE_ID " +
                    "FROM LAB02.ATTRIBUTES " +
                    "WHERE NAME = " + "\'" + attribute + "\'" + " AND OBJECT_TYPE_ID = " +
                    "(SELECT OBJECT_TYPE_ID " +
                    "FROM LAB02.OBJECT_TYPES " +
                    "WHERE NAME=" + "\'" + getObjectType() + "\'" + "))) ";
            queries.put(attribute, attributeInsert);
        }

        return queries;
    }

    private Map<String, String> getUpdateQuery(Set<String> attributesSet) {
        Map<String, String> queries = new HashMap<>();
        String objectUpdate = "UPDATE LAB02.OBJECTS \n" +
                "SET NAME = ?\n" +
                "WHERE OBJECT_ID = ?";
        queries.put(TABLE_OBJECTS, objectUpdate);
        for (String attribute : attributesSet) {
            String attributeUpdate = "UPDATE LAB02.PARAMS\n" +
                    "SET VALUE = ?" +
                    "WHERE OBJECT_ID = ? AND ATTRIBUTE_ID = " +
                    "(SELECT ATTRIBUTE_ID FROM LAB02.ATTRIBUTES WHERE NAME = \'" + attribute + "\')";
            queries.put(attribute, attributeUpdate);
        }

        return queries;
    }

    private Map<String, String> getDeleteQuery() {
        String objectDelete = "DELETE LAB02.OBJECTS \n" +
                "WHERE OBJECT_ID = ?";
        String paramsDelete = "DELETE LAB02.PARAMS\n" +
                "WHERE OBJECT_ID = ?";
        Map<String, String> queries = new HashMap<>();
        queries.put(TABLE_OBJECTS, objectDelete);
        queries.put(TABLE_PARAMS, paramsDelete);
        return queries;
    }

    private void fillWithForeignAttributes(E object) {
        Set<ForeignAttributeType> foreignAttributeTypes = object.getForeignAttributes();
        for (ForeignAttributeType attributeType : foreignAttributeTypes) {
            String values = selectForeignAttribute(attributeType.table, attributeType.relatedAttributeName, object.getId());
            object.setAttribute(attributeType.attributeName, values);
        }
    }

    private String selectForeignAttribute(String fromTable, String foreignAttributeName, BigInteger id) {
        String query = getForeignAttributeQuery();

        StringJoiner joiner = new StringJoiner(",");
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fromTable);
            statement.setString(2, foreignAttributeName);
            statement.setString(3, id.toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                joiner.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joiner.toString();
    }

    private String getForeignAttributeQuery() {
        return "select o.object_id\n" +
                "from lab02.objects o\n" +
                "join lab02.attributes attr on o.object_type_id = (Select object_type_id from lab02.object_types where name = ?)\n" +
                "left join lab02.params p on p.attribute_id = attr.attribute_id\n" +
                " and p.object_id = o.object_id\n" +
                " where attr.name = ? and p.VALUE = ?";
    }


    private void checkWriteGrant(BigInteger id) throws IllegalRoleException {
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(getWriteGrantQuery())) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("Object Grant") == -1 ||
                        rs.getInt("Object Type Grant") != 1) {
                    throw new IllegalRoleException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkReadGrant(String param) throws IllegalRoleException {
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(getReadGrantQuery())) {
            statement.setObject(1, param);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("Object Grant") == -1 ||
                        rs.getInt("Object Type Grant") != 1) {
                    throw new IllegalRoleException();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getReadGrantQuery() {
        return "select ot.read \"Object Type Grant\", o.read \"Object Grant\" \n" +
                "from lab02.object_type_grants ot\n" +
                "left join LAB02.OBJECT_GRANTS o on ot.role =o.role and o.OBJECT_ID =?\n" +
                "where ot.role = '" + ROLE + "'  and ot.OBJECT_TYPE_ID = (select object_type_id from lab02.object_types where name ='" + getObjectType() + "')";
    }

    private String getWriteGrantQuery() {
        return "select ot.write \"Object Type Grant\", o.write \"Object Grant\" \n" +
                "from lab02.object_type_grants ot\n" +
                "left join LAB02.OBJECT_GRANTS o on ot.role =o.role and o.OBJECT_ID =?\n" +
                "where ot.role = '" + ROLE + "'  and ot.OBJECT_TYPE_ID = (Select object_type_id from lab02.object_types where name ='" + getObjectType() + "')";
    }

    abstract String getObjectType();

    abstract E getObject(BigInteger id);

    public enum Roles {
        Administrator,
        Customer,
        Employee,
        ProjectManager
    }

}
