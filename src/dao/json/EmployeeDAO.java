package dao.json;

import Model.Employee;

import javax.json.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class EmployeeDAO extends JsonAbstractDAO<Employee> {
    private static final String FILE_PATH = "./json_files/employees.json";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MANAGER_ID = "manager_id";
    private static final String TASKS = "tasks";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Employee parseObjectFromJson(JsonObject jsonObject) {
        Employee employee = new Employee(new BigInteger(jsonObject.getString(ID)));
        employee.setFirstName(jsonObject.getString(FIRST_NAME));
        employee.setLastName(jsonObject.getString(LAST_NAME));
        employee.setManagerId(new BigInteger(jsonObject.getString(MANAGER_ID)));
        JsonArray tasksArray = jsonObject.getJsonArray(TASKS);
        Set<BigInteger> projects = new LinkedHashSet<>();
        for (int j = 0; j < tasksArray.size(); j++) {
            projects.add(new BigInteger(tasksArray.getString(j)));
        }
        employee.setTasks(projects);
        return employee;
    }

    @Override
    protected JsonObject createJsonFromObject(Employee object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(FIRST_NAME, object.getFirstName())
                .add(LAST_NAME, object.getLastName())
                .add(MANAGER_ID, object.getManagerId().toString());
        JsonArrayBuilder tasksBuilder = Json.createArrayBuilder();
        Set<BigInteger> tasksID = object.getTasks();
        if (tasksID != null) {
            for (BigInteger taskID : tasksID) {
                tasksBuilder.add(taskID.toString());
            }
        }
        builder.add(TASKS, tasksBuilder);
        return builder.build();
    }
}
