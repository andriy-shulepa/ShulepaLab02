package dao.collectoins;

import Model.*;
import dao.DAOFactory;
import dao.GenericDAO;
import dao.dao_interfaces.*;

public class CollectionsDAOFactory extends DAOFactory {


    @Override
    public GenericDAO<Customer> getCustomerDAO() {
        return null;
    }

    @Override
    public GenericDAO<Employee> getEmployeeDAO() {
        return null;
    }

    @Override
    public GenericDAO<Manager> getManagerDAO() {
        return null;
    }

    @Override
    public GenericDAO<Project> getProjectDAO() {
        return null;
    }

    @Override
    public GenericDAO<Sprint> getSprintDAO() {
        return null;
    }

    @Override
    public GenericDAO<Task> getTaskDAO() {
        return null;
    }
}
