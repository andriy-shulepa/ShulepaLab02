package dao.xml;

import Model.AbstractDAOObject;
import dao.*;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class XMLAbstractDAO<E extends AbstractDAOObject> implements GenericDAO<E> {

    static final String ID = "id";
    private Cache<E> cache = new Cache<>();

    private static org.jdom2.Document useDOMParser(String fileName)
            throws ParserConfigurationException, SAXException, IOException {
        //creating DOM Document
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(fileName));
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);
    }

    protected abstract String getFilePath();

    protected abstract String getElementName();

    protected abstract E parseObjectFromElement(Element element);

    protected abstract Element createElementFromObject(E object, BigInteger id);

    @Override
    public E getByPK(BigInteger id) throws IllegalRoleException {
        if (cache.isActual(id)) {
            return cache.get(id);
        }
        org.jdom2.Document jdomDoc;
        E object = null;
        try {
            jdomDoc = useDOMParser(getFilePath());
            Element root = jdomDoc.getRootElement();
            List<Element> elements = root.getChildren(getElementName());
            for (Element element : elements) {
                if (element.getChildText(ID).equals(id.toString())) {
                    object = parseObjectFromElement(element);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cache.add(object);
        return object;
    }

    @Override
    public Set<E> getAll() throws IllegalRoleException {
        org.jdom2.Document jdomDoc;
        Set<E> objectSet = new HashSet<>();
        try {
            jdomDoc = useDOMParser(getFilePath());
            Element root = jdomDoc.getRootElement();
            List<Element> elements = root.getChildren(getElementName());
            for (Element element : elements) {
                E object = parseObjectFromElement(element);
                objectSet.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (E item : objectSet) {
            cache.add(item);
        }

        return objectSet;
    }

    @Override
    public BigInteger insert(E object) throws IllegalRoleException {
        org.jdom2.Document jdomDoc;
        BigInteger id = null;
        try {
            jdomDoc = useDOMParser(getFilePath());
            Element root = jdomDoc.getRootElement();
            id = DAOUtils.generateID(1);
            root.addContent(createElementFromObject(object, id));

            writeXMLDocument(jdomDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(E object) throws OutdatedObjectVersionException, IllegalRoleException {
        if (!cache.isCorrectVersion(object)) {
            throw new OutdatedObjectVersionException();
        }
        org.jdom2.Document jdomDoc;
        try {
            jdomDoc = useDOMParser(getFilePath());
            Element root = jdomDoc.getRootElement();
            List<Element> elements = root.getChildren(getElementName());
            for (Element element : elements) {
                if (element.getChildText(ID).equals(object.getId().toString())) {
                    root.removeContent(element);
                    root.addContent(createElementFromObject(object, object.getId()));
                    break;
                }
            }
            writeXMLDocument(jdomDoc);
            cache.update(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(E object) throws IllegalRoleException {
        org.jdom2.Document jdomDoc;
        try {
            jdomDoc = useDOMParser(getFilePath());
            Element root = jdomDoc.getRootElement();
            List<Element> elements = root.getChildren(getElementName());
            for (Element element : elements) {
                if (element.getChildText(ID).equals(object.getId().toString())) {
                    root.removeContent(element);
                    break;
                }
            }
            writeXMLDocument(jdomDoc);
            cache.remove(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeXMLDocument(org.jdom2.Document jdomDoc) {
        try {
            FileWriter writer = new FileWriter(getFilePath());
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(jdomDoc, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
