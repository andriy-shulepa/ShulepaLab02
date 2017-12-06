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
    public GenericDAO<Customer> getCustomerDAO() {
        return new CustomerDAO();
    }

    @Override
    public GenericDAO<Employee> getEmployeeDAO() {
        return new EmployeeDAO();
    }

    @Override
    public GenericDAO<Manager> getManagerDAO() {
        return new ManagerDAO();
    }

    @Override
    public GenericDAO<Project> getProjectDAO() {
        return new ProjectDAO();
    }

    @Override
    public GenericDAO<Sprint> getSprintDAO() {
        return new SprintDAO();
    }

    @Override
    public GenericDAO<Task> getTaskDAO() {
        return new TaskDAO();
    }
}
