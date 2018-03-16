package dao.xml;

import Model.Customer;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerDAO extends XMLAbstractDAO<Customer> {
    private static final String FILE_PATH = "./xml_files/customers.xml";
    private static final String CUSTOMER = "customer";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PROJECTS = "projects";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected String getElementName() {
        return CUSTOMER;
    }

    @Override
    protected Customer parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Customer customer = new Customer(id);
        customer.setName(element.getChildText(NAME));
        customer.setDescription(element.getChildText(DESCRIPTION));
        Set<BigInteger> projectsSet = new HashSet<>();
        List<Element> projectsID = element.getChild(PROJECTS).getChildren(ID);
        for (Element proj : projectsID) {
            projectsSet.add(new BigInteger(proj.getText()));
        }
        customer.setProjects(projectsSet);
        return customer;
    }

    @Override
    protected Element createElementFromObject(Customer object, BigInteger id) {
        Element child = new Element(CUSTOMER);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(NAME).setText(object.getName()));
        child.addContent(new Element(DESCRIPTION).setText(object.getDescription()));

        Element projects = new Element(PROJECTS);
        Set<BigInteger> projectsID = object.getProjects();
        for (BigInteger proj : projectsID) {
            projects.addContent(new Element(ID).setText(proj.toString()));
        }
        child.addContent(projects);
        return child;
    }
}
