package dao.json;

import Model.Project;

import javax.json.*;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProjectDAO extends JsonAbstractDAO<Project> {
    private static final String FILE_PATH = "./json_files/projects.json";
    private static final String NAME = "name";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String SPRINTS = "sprints";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Project parseObjectFromJson(JsonObject jsonObject) {
        Project project = new Project(new BigInteger(jsonObject.getString(ID)));
        project.setName(jsonObject.getString(NAME));
        Calendar startDate = new GregorianCalendar();
        startDate.setTimeInMillis(Long.parseLong(jsonObject.getString(START_DATE)));
        project.setStartDate(startDate);
        Calendar endDate = new GregorianCalendar();
        startDate.setTimeInMillis(Long.parseLong(jsonObject.getString(END_DATE)));
        project.setEndDate(endDate);
        project.setCustomerId(new BigInteger(jsonObject.getString(CUSTOMER_ID)));
        JsonArray sprintsArray = jsonObject.getJsonArray(SPRINTS);
        Set<BigInteger> projects = new LinkedHashSet<>();
        for (int j = 0; j < sprintsArray.size(); j++) {
            projects.add(new BigInteger(sprintsArray.getString(j)));
        }
        project.setSprints(projects);
        return project;
    }

    @Override
    protected JsonObject createJsonFromObject(Project object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(NAME, object.getName())
                .add(START_DATE, Long.toString(object.getStartDate().getTimeInMillis()))
                .add(END_DATE, Long.toString(object.getEndDate().getTimeInMillis()))
                .add(CUSTOMER_ID, object.getCustomerId().toString());
        JsonArrayBuilder sprintsBuilder = Json.createArrayBuilder();
        Set<BigInteger> sprintsID = object.getSprints();
        if (sprintsID != null) {
            for (BigInteger sprintID : sprintsID) {
                sprintsBuilder.add(sprintID.toString());
            }
        }
        builder.add(SPRINTS, sprintsBuilder);
        return builder.build();
    }
}
