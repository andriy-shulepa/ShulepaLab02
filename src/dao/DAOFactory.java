package dao;

import Model.*;
import dao.binary.BinaryDAOFactory;
import dao.collectoins.CollectionsDAOFactory;
import dao.json.JSONDAOFactory;
import dao.oracle.OracleAbstractDAO;
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

    public abstract GenericDAO<Customer> getCustomerDAO(OracleAbstractDAO.Roles role);

    public abstract GenericDAO<Employee> getEmployeeDAO(OracleAbstractDAO.Roles role);

    public abstract GenericDAO<Manager> getManagerDAO(OracleAbstractDAO.Roles role);

    public abstract GenericDAO<Project> getProjectDAO(OracleAbstractDAO.Roles role);

    public abstract GenericDAO<Sprint> getSprintDAO(OracleAbstractDAO.Roles role);

    public abstract GenericDAO<Task> getTaskDAO(OracleAbstractDAO.Roles role);

    public enum DAOTypes {
        ORACLE,
        COLLECTIONS,
        BINARY,
        JSON,
        XML
    }

}
