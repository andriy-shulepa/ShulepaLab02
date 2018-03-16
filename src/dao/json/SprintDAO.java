package dao.json;

import Model.Sprint;

import javax.json.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class SprintDAO extends JsonAbstractDAO<Sprint> {
    private static final String FILE_PATH = "./xml_files/sprints.xml";
    private static final String NAME = "name";
    private static final String PREVIOUS_SPRINT_ID = "previous_sprint_id";
    private static final String PROJECT_ID = "project_id";
    private static final String TASKS = "tasks";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Sprint parseObjectFromJson(JsonObject jsonObject) {
        Sprint sprint = new Sprint(new BigInteger(jsonObject.getString(ID)));
        sprint.setName(jsonObject.getString(NAME));
        sprint.setPreviousSprintId(new BigInteger(jsonObject.getString(PREVIOUS_SPRINT_ID)));
        sprint.setProjectId(new BigInteger(jsonObject.getString(PROJECT_ID)));
        JsonArray tasksArray = jsonObject.getJsonArray(TASKS);
        Set<BigInteger> tasks = new LinkedHashSet<>();
        for (int j = 0; j < tasksArray.size(); j++) {
            tasks.add(new BigInteger(tasksArray.getString(j)));
        }
        sprint.setTasks(tasks);
        return sprint;
    }

    @Override
    protected JsonObject createJsonFromObject(Sprint object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(NAME, object.getName())
                .add(PREVIOUS_SPRINT_ID, object.getPreviousSprintId().toString())
                .add(PROJECT_ID, object.getProjectId().toString());
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
