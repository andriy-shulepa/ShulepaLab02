package dao.oracle;


import dao.GenericDAO;
import dao.ObjectContainer;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class OracleAbstractDAO<E extends IDable> implements GenericDAO<E> {
    static final String TABLE_OBJECTS = "LAB02.OBJECTS";
    static final String TABLE_PARAMS = "LAB02.PARAMS";
    static final String OBJECT_ID = "OBJECT_ID";
    static final String OBJECT_NAME = "NAME";
    private Map<BigInteger, ObjectContainer<E>> cache = new HashMap<>();

    abstract String getSelectQuery();

    abstract String getInsertQuery();

    abstract Map<String, String> getUpdateQuery();

    abstract Map<String, String> getDeleteQuery();

    abstract BigInteger prepareStatementForInsert(PreparedStatement statement, E object);

    abstract void prepareStatementForUpdate(Map<String, PreparedStatement> statements, E object);

    abstract void prepareStatementForDelete(Map<String, PreparedStatement> statements, E object);

    abstract Set<E> parseResultSet(ResultSet rs);

    @Override
    public E getByPK(BigInteger id) {
        if (cache.containsKey(id) && cache.get(id).isActual()) {
            return cache.get(id).getObject();
        }
        Set<E> list = null;
        String sql = getSelectQuery();
        sql += " WHERE o.object_id = ?";
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        E object = list.iterator().next();
        cache.put(object.getId(), new ObjectContainer<>(object));
        return object;
    }


    @Override
    public Set<E> getAll() {
        Set<E> list = null;
        String sql = getSelectQuery();
        sql += " ORDER BY o.OBJECT_ID";
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list != null) {
            for (E item : list) {
                cache.put(item.getId(), new ObjectContainer<>(item));
            }
        }
        return list;
    }

    @Override
    public void update(E object) {
        Map<String, String> sql = getUpdateQuery();
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
            cache.put(object.getId(), new ObjectContainer<>(object));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(E object) {
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

            cache.remove(object.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BigInteger insert(E object) {

//        E instance = null;
        String sql = getInsertQuery();
        BigInteger ID = null;
        try (Connection connection = OracleDAOFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ID = prepareStatementForInsert(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

}
