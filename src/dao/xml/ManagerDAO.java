package dao.xml;

import Model.Manager;
import org.jdom2.Element;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerDAO extends XMLAbstractDAO<Manager> {

    private static final String FILE_PATH = "./xml_files/managers.xml";
    private static final String MANAGER = "manager";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMPLOYEES = "employees";

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected String getElementName() {
        return MANAGER;
    }

    @Override
    protected Manager parseObjectFromElement(Element element) {
        BigInteger id = new BigInteger(element.getChildText(ID));
        Manager manager = new Manager(id);
        manager.setFirstName(element.getChildText(FIRST_NAME));
        manager.setLastName(element.getChildText(LAST_NAME));
        Set<BigInteger> employeesSet = new HashSet<>();
        List<Element> employeesID = element.getChild(EMPLOYEES).getChildren(ID);
        for (Element employeeID : employeesID) {
            employeesSet.add(new BigInteger(employeeID.getText()));
        }
        manager.setEmployees(employeesSet);
        return manager;
    }

    @Override
    protected Element createElementFromObject(Manager object, BigInteger id) {
        Element child = new Element(MANAGER);
        child.addContent(new Element(ID).setText(id.toString()));
        child.addContent(new Element(FIRST_NAME).setText(object.getFirstName()));
        child.addContent(new Element(LAST_NAME).setText(object.getLastName()));

        Element employees = new Element(EMPLOYEES);
        Set<BigInteger> employeesID = object.getEmployees();
        for (BigInteger employeeID : employeesID) {
            employees.addContent(new Element(ID).setText(employeeID.toString()));
        }
        child.addContent(employees);
        return child;
    }
}
