package dao.oracle;

import Model.Task;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

public class TaskDAO extends OracleAbstractDAO<Task> {

    @Override
    String getSelectQuery() {
        return null;
    }

    @Override
    String getInsertQuery() {
        return null;
    }

    @Override
    Map<String, String> getUpdateQuery() {
        return null;
    }

    @Override
    Map<String, String> getDeleteQuery() {
        return null;
    }

    @Override
    BigInteger prepareStatementForInsert(PreparedStatement statement, Task object) {
        return null;
    }


    @Override
    void prepareStatementForUpdate(Map<String, PreparedStatement> statements, Task object) {

    }

    @Override
    void prepareStatementForDelete(Map<String, PreparedStatement> statements, Task object) {

    }

    @Override
    Set<Task> parseResultSet(ResultSet rs) {
        return null;
    }
}
