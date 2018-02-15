package dao.xml;

import Model.*;
import dao.DAOFactory;
import dao.GenericDAO;
import dao.oracle.OracleAbstractDAO;

public class XMLDAOFactory extends DAOFactory {

    @Override
    public GenericDAO<Customer> getCustomerDAO(OracleAbstractDAO.Roles role) {
        return new CustomerDAO();
    }

    @Override
    public GenericDAO<Employee> getEmployeeDAO(OracleAbstractDAO.Roles role) {
        return null;
    }

    @Override
    public GenericDAO<Manager> getManagerDAO(OracleAbstractDAO.Roles role) {
        return null;
    }

    @Override
    public GenericDAO<Project> getProjectDAO(OracleAbstractDAO.Roles role) {
        return null;
    }

    @Override
    public GenericDAO<Sprint> getSprintDAO(OracleAbstractDAO.Roles role) {
        return null;
    }

    @Override
    public GenericDAO<Task> getTaskDAO(OracleAbstractDAO.Roles role) {
        return null;
    }
}