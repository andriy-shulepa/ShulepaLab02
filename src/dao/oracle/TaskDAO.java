package dao.oracle;

import Model.Task;

import java.math.BigInteger;

public class TaskDAO extends OracleAbstractDAO<Task> {

    private static final String OBJECT_TYPE = "Task";

    public TaskDAO(Roles role) {
        super(role);
    }
    
    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Task getObject(BigInteger id) {
        return new Task(id);
    }
}
