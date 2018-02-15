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
    //Get JDOM document from DOM Parser
//    private static org.jdom2.Document useDOMParser(String fileName)
//            throws ParserConfigurationException, SAXException, IOException {
//        //creating DOM Document
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder;
//        dBuilder = dbFactory.newDocumentBuilder();
//        Document doc = dBuilder.parse(new File(fileName));
//        DOMBuilder domBuilder = new DOMBuilder();
//        return domBuilder.build(doc);
//
//    }
//
//    @Override
//    public Customer getByPK(BigInteger id) throws IllegalRoleException {
//        org.jdom2.Document jdomDoc;
//        Customer customer = null;
//        try {
//            jdomDoc = useDOMParser(FILE_PATH);
//            Element root = jdomDoc.getRootElement();
//            List<Element> elements = root.getChildren(CUSTOMER);
//            for (Element element : elements)  {
//                if(element.getChildText(ID).equals(id.toString())) {
//                    customer = new Customer(id);
//                    customer.setName(element.getChildText(NAME));
//                    customer.setDescription(element.getChildText(DESCRIPTION));
//                    Set<BigInteger> projectsSet = new HashSet<>();
//                    List<Element> projectsID = element.getChild(PROJECTS).getChildren(ID);
//                    for(Element proj : projectsID) {
//                        projectsSet.add(new BigInteger(proj.getText()));
//                    }
//                    customer.setProjects(projectsSet);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return customer;
//    }
//
//    @Override
//    public Set<Customer> getAll() throws IllegalRoleException {
//        org.jdom2.Document jdomDoc;
//        Set<Customer> customerSet = new HashSet<>();
//        try {
//            jdomDoc = useDOMParser(FILE_PATH);
//            Element root = jdomDoc.getRootElement();
//            List<Element> elements = root.getChildren(CUSTOMER);
//            for (Element element : elements)  {
//                BigInteger id = new BigInteger(element.getChildText(ID));
//                    Customer customer = new Customer(id);
//                    customer.setName(element.getChildText(NAME));
//                    customer.setDescription(element.getChildText(DESCRIPTION));
//                    Set<BigInteger> projectsSet = new HashSet<>();
//                    List<Element> projectsID = element.getChild(PROJECTS).getChildren(ID);
//                    for(Element proj : projectsID) {
//                        projectsSet.add(new BigInteger(proj.getText()));
//                    }
//                    customer.setProjects(projectsSet);
//                    customerSet.add(customer);
//                }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return customerSet;
//    }
//
//    @Override
//    public BigInteger insert(Customer object) throws IllegalRoleException {
//        org.jdom2.Document jdomDoc;
//        BigInteger id = null;
//        try {
//            jdomDoc = useDOMParser(FILE_PATH);
//            Element root = jdomDoc.getRootElement();
//            Element child = new Element(CUSTOMER);
//            id = DAOUtils.generateID(1);
//            child.addContent(new Element(ID).setText(id.toString()));
//            child.addContent(new Element(NAME).setText(object.getName()));
//            child.addContent(new Element(DESCRIPTION).setText(object.getDescription()));
//
//            Element projects = new Element(PROJECTS);
//            Set<BigInteger> projectsID = object.getProjects();
//            for(BigInteger proj : projectsID) {
//                projects.addContent(new Element(ID).setText(proj.toString()));
//            }
//            child.addContent(projects);
//            root.addContent(child);
//
//            FileWriter writer = new FileWriter(FILE_PATH);
//            XMLOutputter outputter = new XMLOutputter();
//            outputter.setFormat(Format.getPrettyFormat());
//            outputter.output(jdomDoc, writer);
//            outputter.output(jdomDoc, System.out);
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return id;
//    }
//
//    @Override
//    public void update(Customer object) throws OutdatedObjectVersionException, IllegalRoleException {
//
//    }
//
//    @Override
//    public void delete(Customer object) throws IllegalRoleException {
//
//    }

}
