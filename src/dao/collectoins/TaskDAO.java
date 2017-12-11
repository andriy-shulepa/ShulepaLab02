package dao.collectoins;

import Model.Task;

import java.math.BigInteger;

public class TaskDAO extends CollectionsAbsractDAO<Task> {
    @Override
    protected Task cloneObjectWithId(BigInteger id, Task object) {
        return new Task(id,object);
    }
}
