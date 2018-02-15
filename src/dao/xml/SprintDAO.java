package dao.xml;

import Model.Sprint;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SprintDAO extends XMLAbstractDAO<Sprint> {

    private static final String FILE_PATH = "./xml_files/sprints.xml";
    private static final String SPRINT = "sprint";
    private static final String NAME = "name";
    private static final String PREVIOUS_SPRINT_ID = "previous_sprint_id";
    private static final String PROJECT_ID = "project_id";
    private static final String TASKS = "tasks";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected String getElementName() {
        return SPRINT;
    }

    @Override
    protected Sprint parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Sprint sprint = new Sprint(id);
        sprint.setName(element.getChildText(NAME));
        sprint.setPreviousSprintId(new BigInteger(element.getChildText(PREVIOUS_SPRINT_ID)));
        sprint.setProjectId(new BigInteger(element.getChildText(PROJECT_ID)));

        Set<BigInteger> tasksSet = new HashSet<>();
        List<Element> tasksID = element.getChild(TASKS).getChildren(ID);
        for (Element taskID : tasksID) {
            tasksSet.add(new BigInteger(taskID.getText()));
        }
        sprint.setTasks(tasksSet);
        return sprint;
    }

    @Override
    protected Element createElementFromObject(Sprint object, BigInteger id) {
        Element child = new Element(SPRINT);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(NAME).setText(object.getName()));
        child.addContent(new Element(PREVIOUS_SPRINT_ID).setText(object.getPreviousSprintId().toString()));
        child.addContent(new Element(PROJECT_ID).setText(object.getProjectId().toString()));

        Element tasks = new Element(TASKS);
        Set<BigInteger> tasksID = object.getTasks();
        for (BigInteger taskID : tasksID) {
            tasks.addContent(new Element(ID).setText(taskID.toString()));
        }
        child.addContent(tasks);
        return child;
    }
}
