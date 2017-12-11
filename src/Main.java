import Model.*;
import dao.DAOFactory;
import dao.GenericDAO;
import com.rits.cloning.*;

import java.math.BigInteger;
import java.util.*;


public class Main {
    public static void main(String... args) {
        GenericDAO<Customer> customerDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getCustomerDAO();
//        Customer customer = new Customer();
//        customer.setName("Shulepa");
//        customer.setDescription("The best customer ever");
//        BigInteger customer_ID = new BigInteger("1201712031907090393");
//        customer = customerDAO.getByPK(customer_ID);
//        System.out.println(customer);
//        Project project = new Project();
//        project.setName("Mario");
//        project.setCustomerId(customer_ID);
//        Calendar startDate = new GregorianCalendar();
//        startDate.set(2017,Calendar.DECEMBER, 29);
//        project.setStartDate(startDate);
//        Calendar endDate = new GregorianCalendar();
//        endDate.set(2018,Calendar.NOVEMBER,29);
//        project.setEndDate(endDate);
//        GenericDAO<Project> projectDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getProjectDAO();
//        BigInteger project_ID = projectDAO.insert(project);
//        Set<Customer> customers = customerDAO.getAll();
//        for (Customer c:customers) {
//            System.out.println(c);
//        }

//        Project project = projectDAO.getByPK(new BigInteger("1201712031922255322"));
//        System.out.println(project);
//        Customer customer = customers.iterator().next();
//        customerDAO.delete(customer);
//        System.out.println();
//         customers = customerDAO.getAll();
//        for (Customer c:customers) {
//            System.out.println(c);
//        }
    //    GenericDAO<Sprint> sprintDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getSprintDAO();
//        Sprint sprint = new Sprint();
//        sprint.setProjectId(new BigInteger("1201712031922255322"));
//        sprint.setName("First Sprint");
//        BigInteger sprintID  = sprintDAO.insert(sprint);

//        long time  = System.nanoTime();
//       System.out.println(sprintDAO.getByPK(new BigInteger("1201712031946278435")));
//        System.out.println(System.nanoTime()-time);
//        time  = time  = System.nanoTime();
//        System.out.println(sprintDAO.getByPK(new BigInteger("1201712031946278435")));
//        System.out.println(System.nanoTime()-time);
//        time  = System.nanoTime();
//        System.out.println(sprintDAO.getByPK(new BigInteger("1201712031946278435")));
//        System.out.println(System.nanoTime()-time);
//        GenericDAO<Manager> managerDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getManagerDAO();
//        for (Manager m : managerDAO.getAll()) System.out.println(m);
//        Manager manager = new Manager();
//        manager.setFirstName("Dmytro");
//        manager.setLastName("Halushko");
//        BigInteger managerID =  managerDAO.insert(manager);
//        System.out.println(managerDAO.getByPK(managerID));
//        GenericDAO<Sprint> employeeDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getSprintDAO();
//        for (Sprint e: employeeDAO.getAll()) System.out.println(e);
//        Employee employee = new Employee();
//        employee.setFirstName("Petya");
//        employee.setLastName("Petrenko");
//        employee.setManagerId(new BigInteger("1201712051551166405"));
//        BigInteger employeeID = employeeDAO.insert(employee);
//        System.out.println(employeeDAO.getByPK(employeeID));

BigInteger bigInteger = new BigInteger("1231");
        System.out.println(bigInteger);
    }


}
