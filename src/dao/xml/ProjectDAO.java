package dao.xml;

import Model.Project;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.*;

public class ProjectDAO extends XMLAbstractDAO<Project> {
    private static final String FILE_PATH = "./xml_files/projects.xml";
    private static final String PROJECT = "project";
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
    protected String getElementName() {
        return PROJECT;
    }

    @Override
    protected Project parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Project project = new Project(id);
        project.setName(element.getChildText(NAME));
        project.setCustomerId(new BigInteger(element.getChildText(CUSTOMER_ID)));
        Calendar startDate = new GregorianCalendar();
        startDate.setTimeInMillis(Long.parseLong(element.getChildText(START_DATE)));
        project.setStartDate(startDate);
        Calendar endDate = new GregorianCalendar();
        endDate.setTimeInMillis(Long.parseLong(element.getChildText(END_DATE)));
        project.setEndDate(endDate);
        Set<BigInteger> sprintsSet = new HashSet<>();
        List<Element> sprintsID = element.getChild(SPRINTS).getChildren(ID);
        for (Element sprintID : sprintsID) {
            sprintsSet.add(new BigInteger(sprintID.getText()));
        }
        project.setSprints(sprintsSet);
        return project;
    }

    @Override
    protected Element createElementFromObject(Project object, BigInteger id) {
        Element child = new Element(PROJECT);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(NAME).setText(object.getName()));
        child.addContent(new Element(CUSTOMER_ID).setText(object.getCustomerId().toString()));
        child.addContent(new Element(START_DATE).setText(Long.toString(object.getStartDate().getTimeInMillis())));
        child.addContent(new Element(END_DATE).setText(Long.toString(object.getEndDate().getTimeInMillis())));

        Element sprints = new Element(SPRINTS);
        Set<BigInteger> sprintsID = object.getSprints();
        for (BigInteger sprintID : sprintsID) {
            sprints.addContent(new Element(ID).setText(sprintID.toString()));
        }
        child.addContent(sprints);
        return child;
    }
}
