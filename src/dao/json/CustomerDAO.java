package dao.json;

import Model.Customer;

import javax.json.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomerDAO extends JsonAbstractDAO<Customer> {
    private static final String FILE_PATH = "./json_files/customers.json";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PROJECTS = "projects";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Customer parseObjectFromJson(JsonObject jsonObject) {
        Customer customer = new Customer(new BigInteger(jsonObject.getString(ID)));
        customer.setName(jsonObject.getString(NAME));
        customer.setDescription(jsonObject.getString(DESCRIPTION));
        JsonArray projectsArray = jsonObject.getJsonArray(PROJECTS);
        Set<BigInteger> projects = new LinkedHashSet<>();
        for (int j = 0; j < projectsArray.size(); j++) {
            projects.add(new BigInteger(projectsArray.getString(j)));
        }
        customer.setProjects(projects);
        return customer;
    }

    @Override
    protected JsonObject createJsonFromObject(Customer object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(NAME, object.getName())
                .add(DESCRIPTION, object.getDescription());
        JsonArrayBuilder projectsBuilder = Json.createArrayBuilder();
        Set<BigInteger> projectsID = object.getProjects();
        if (projectsID != null) {
            for (BigInteger projectID : projectsID) {
                projectsBuilder.add(projectID.toString());
            }
        }
        builder.add(PROJECTS, projectsBuilder);
        return builder.build();
    }
}
