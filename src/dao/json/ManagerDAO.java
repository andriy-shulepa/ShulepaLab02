package dao.json;

import Model.Manager;

import javax.json.*;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public class ManagerDAO extends JsonAbstractDAO<Manager> {
    private static final String FILE_PATH = "./json_files/managers.json";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMPLOYEES = "employees";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Manager parseObjectFromJson(JsonObject jsonObject) {
        Manager manager = new Manager(new BigInteger(jsonObject.getString(ID)));
        manager.setFirstName(jsonObject.getString(FIRST_NAME));
        manager.setLastName(jsonObject.getString(LAST_NAME));
        JsonArray employeesArray = jsonObject.getJsonArray(EMPLOYEES);
        Set<BigInteger> employees = new LinkedHashSet<>();
        for (int j = 0; j < employeesArray.size(); j++) {
            employees.add(new BigInteger(employeesArray.getString(j)));
        }
        manager.setEmployees(employees);
        return manager;
    }

    @Override
    protected JsonObject createJsonFromObject(Manager object, BigInteger id) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add(ID, id.toString())
                .add(FIRST_NAME, object.getFirstName())
                .add(LAST_NAME, object.getLastName());
        JsonArrayBuilder employeesBuilder = Json.createArrayBuilder();
        Set<BigInteger> employeesID = object.getEmployees();
        if (employeesID != null) {
            for (BigInteger employeeID : employeesID) {
                employeesBuilder.add(employeeID.toString());
            }
        }
        builder.add(EMPLOYEES, employeesBuilder);
        return builder.build();
    }
}
