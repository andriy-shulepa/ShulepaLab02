package dao.json;

import Model.Qualification;
import Model.Task;

import javax.json.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class TaskDAO extends JsonAbstractDAO<Task> {
    private static final String FILE_PATH = "./xml_files/tasks.xml";
    private static final String NAME = "name";
    private static final String SPRINT_ID = "sprint_id";
    private static final String PARENT_TASK_ID = "parent_task_id";
    private static final String ESTIMATE = "estimate";
    private static final String QUALIFICATION = "qualification";
    private static final String SUBTASKS = "subtasks";
    private static final String EMPLOYEES = "employees";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Task parseObjectFromJson(JsonObject jsonObject) {
        Task task = new Task(new BigInteger(jsonObject.getString(ID)));
        task.setName(jsonObject.getString(NAME));
        task.setSprintId(new BigInteger(jsonObject.getString(SPRINT_ID)));
        task.setParentTaskId(new BigInteger(jsonObject.getString(PARENT_TASK_ID)));
        task.setEstimate(jsonObject.getInt(ESTIMATE));
        task.setQualification(Qualification.valueOf(jsonObject.getString(QUALIFICATION)));
        JsonArray subtasksArray = jsonObject.getJsonArray(SUBTASKS);
        Set<BigInteger> subtasks = new LinkedHashSet<>();
        for (int j = 0; j < subtasksArray.size(); j++) {
            subtasks.add(new BigInteger(subtasksArray.getString(j)));
        }
        task.setSubtasks(subtasks);
        JsonArray employeesArray = jsonObject.getJsonArray(EMPLOYEES);
        Set<BigInteger> employees = new LinkedHashSet<>();
        for (int j = 0; j < employeesArray.size(); j++) {
            employees.add(new BigInteger(employeesArray.getString(j)));
        }
        task.setSubtasks(employees);
        return task;
    }

    @Override
    protected JsonObject createJsonFromObject(Task object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(NAME, object.getName())
                .add(SPRINT_ID, object.getSprintId().toString())
                .add(PARENT_TASK_ID, object.getParentTaskId().toString())
                .add(ESTIMATE, object.getEstimate())
                .add(QUALIFICATION, object.getQualification().toString());
        JsonArrayBuilder subtasksBuilder = Json.createArrayBuilder();
        Set<BigInteger> subtasksID = object.getSubtasks();
        if (subtasksID != null) {
            for (BigInteger subtaskID : subtasksID) {
                subtasksBuilder.add(subtaskID.toString());
            }
        }
        builder.add(SUBTASKS, subtasksBuilder);
        JsonArrayBuilder employeesBuilder = Json.createArrayBuilder();
        Set<BigInteger> employeesID = object.getSubtasks();
        if (employeesID != null) {
            for (BigInteger employeeID : employeesID) {
                employeesBuilder.add(employeeID.toString());
            }
        }
        builder.add(EMPLOYEES, employeesBuilder);
        return builder.build();
    }
}
