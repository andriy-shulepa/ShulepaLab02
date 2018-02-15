package dao.xml;

import Model.Qualification;
import Model.Task;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskDAO extends XMLAbstractDAO<Task> {
    private static final String FILE_PATH = "./xml_files/tasks.xml";
    private static final String TASK = "task";
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
    protected String getElementName() {
        return TASK;
    }

    @Override
    protected Task parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Task task = new Task(id);
        task.setName(element.getChildText(NAME));
        task.setSprintId(new BigInteger(element.getChildText(SPRINT_ID)));
        task.setParentTaskId(new BigInteger(element.getChildText(PARENT_TASK_ID)));
        task.setEstimate(Integer.parseInt(element.getChildText(ESTIMATE)));
        task.setQualification(Qualification.valueOf(element.getChildText(QUALIFICATION)));

        Set<BigInteger> subtasksSet = new HashSet<>();
        List<Element> subtasksID = element.getChild(SUBTASKS).getChildren(ID);
        for (Element subtaskID : subtasksID) {
            subtasksSet.add(new BigInteger(subtaskID.getText()));
        }
        task.setSubtasks(subtasksSet);

        Set<BigInteger> employeesSet = new HashSet<>();
        List<Element> employeesID = element.getChild(EMPLOYEES).getChildren(ID);
        for (Element employeeID : employeesID) {
            employeesSet.add(new BigInteger(employeeID.getText()));
        }
        task.setEmployees(employeesSet);
        return task;
    }

    @Override
    protected Element createElementFromObject(Task object, BigInteger id) {
        Element child = new Element(TASK);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(NAME).setText(object.getName()));
        child.addContent(new Element(SPRINT_ID).setText(object.getSprintId().toString()));
        child.addContent(new Element(PARENT_TASK_ID).setText(object.getParentTaskId().toString()));
        child.addContent(new Element(ESTIMATE).setText(Integer.toString(object.getEstimate())));
        child.addContent(new Element(QUALIFICATION).setText(object.getQualification().toString()));

        Element subtasks = new Element(SUBTASKS);
        Set<BigInteger> subtasksID = object.getSubtasks();
        for (BigInteger subtaskID : subtasksID) {
            subtasks.addContent(new Element(ID).setText(subtaskID.toString()));
        }
        child.addContent(subtasks);

        Element employees = new Element(EMPLOYEES);
        Set<BigInteger> employeesID = object.getEmployees();
        for (BigInteger employeeID : employeesID) {
            employees.addContent(new Element(ID).setText(employeeID.toString()));
        }
        child.addContent(employees);

        return child;
    }
}
