package dao.xml;

import Model.Employee;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDAO extends XMLAbstractDAO<Employee> {
    private static final String FILE_PATH = "./xml_files/employees.xml";
    private static final String EMPLOYEE = "employee";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String MANAGER_ID = "manager_id";
    private static final String TASKS = "tasks";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected String getElementName() {
        return EMPLOYEE;
    }

    @Override
    protected Employee parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Employee employee = new Employee(id);
        employee.setFirstName(element.getChildText(FIRST_NAME));
        employee.setLastName(element.getChildText(LAST_NAME));
        employee.setManagerId(new BigInteger(element.getChildText(MANAGER_ID)));
        Set<BigInteger> tasksSet = new HashSet<>();
        List<Element> tasksID = element.getChild(TASKS).getChildren(ID);
        for (Element task : tasksID) {
            tasksSet.add(new BigInteger(task.getText()));
        }
        employee.setTasks(tasksSet);
        return employee;
    }

    @Override
    protected Element createElementFromObject(Employee object, BigInteger id) {
        Element child = new Element(EMPLOYEE);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(FIRST_NAME).setText(object.getFirstName()));
        child.addContent(new Element(LAST_NAME).setText(object.getLastName()));
        child.addContent(new Element(MANAGER_ID).setText(object.getManagerId().toString()));

        Element tasks = new Element(TASKS);
        Set<BigInteger> tasksID = object.getTasks();
        for (BigInteger taskID : tasksID) {
            tasks.addContent(new Element(ID).setText(taskID.toString()));
        }
        child.addContent(tasks);
        return child;
    }
}
