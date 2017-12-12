package dao.oracle;

import Model.*;
import dao.DAOFactory;
import dao.GenericDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDAOFactory extends DAOFactory {


    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DBURL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "SYSTEM";
    private static final String PASSWORD = "1234567890";

    static Connection createConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public GenericDAO<Customer> getCustomerDAO(OracleAbstractDAO.Roles role) {
        return new CustomerDAO(role);
    }

    @Override
    public GenericDAO<Employee> getEmployeeDAO(OracleAbstractDAO.Roles role) {
        return new EmployeeDAO(role);
    }

    @Override
    public GenericDAO<Manager> getManagerDAO(OracleAbstractDAO.Roles role) {
        return new ManagerDAO(role);
    }

    @Override
    public GenericDAO<Project> getProjectDAO(OracleAbstractDAO.Roles role) {
        return new ProjectDAO(role);
    }

    @Override
    public GenericDAO<Sprint> getSprintDAO(OracleAbstractDAO.Roles role) {
        return new SprintDAO(role);
    }

    @Override
    public GenericDAO<Task> getTaskDAO(OracleAbstractDAO.Roles role) {
        return new TaskDAO(role);
    }
}
