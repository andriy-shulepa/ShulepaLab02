package dao.binary;

import Model.Task;

import java.math.BigInteger;

public class TaskDAO extends BinaryAbstractDAO<Task> {
    @Override
    String getFileName() {
        return "tasks.ser";
    }

    @Override
    Task prepareObjectWithId(Task object, BigInteger id) {
        return new Task(id, object);
    }
}
