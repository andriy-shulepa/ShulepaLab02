package dao;

import Model.*;
import dao.binary.BinaryDAOFactory;
import dao.collectoins.CollectionsDAOFactory;
import dao.json.JSONDAOFactory;
import dao.oracle.OracleDAOFactory;
import dao.xml.XMLDAOFactory;

public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(DAOTypes which) {
        switch (which) {
            case ORACLE:
                return new OracleDAOFactory();
            case COLLECTIONS:
                return new CollectionsDAOFactory();
            case BINARY:
                return new BinaryDAOFactory();
            case JSON:
                return new JSONDAOFactory();
            case XML:
                return new XMLDAOFactory();
            default:
                return null;
        }
    }

    public abstract GenericDAO<Customer> getCustomerDAO();

    public abstract GenericDAO<Employee> getEmployeeDAO();

    public abstract GenericDAO<Manager> getManagerDAO();

    public abstract GenericDAO<Project> getProjectDAO();

    public abstract GenericDAO<Sprint> getSprintDAO();

    public abstract GenericDAO<Task> getTaskDAO();

    public enum DAOTypes {
        ORACLE,
        COLLECTIONS,
        BINARY,
        JSON,
        XML
    }

}
