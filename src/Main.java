import Model.Customer;
import dao.DAOFactory;
import dao.GenericDAO;
import dao.IllegalRoleException;
import dao.oracle.OracleAbstractDAO;

import java.math.BigInteger;


public class Main {
    public static void main(String... args) throws IllegalRoleException {
//        GenericDAO<Customer> customerDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.JSON).getCustomerDAO(OracleAbstractDAO.Roles.Administrator);
//        Customer customer = new Customer();
//        customer.setName("Shulepa2");
//        customer.setDescription("The best customer ever");
//        BigInteger customer_ID = customerDAO.insert(customer);
////        BigInteger customer_ID = new BigInteger("1201802201931075496");
//        customer = customerDAO.getByPK(customer_ID);
//        System.out.println(customer);

//        Project project = new Project();
//        project.setName("Mario");
//        project.setCustomerId(new BigInteger("1201802181613263442"));
//        Calendar startDate = new GregorianCalendar();
//        startDate.set(2010, Calendar.FEBRUARY, 1);
//        project.setStartDate(startDate);
//        Calendar endDate = new GregorianCalendar();
//        endDate.set(2019, Calendar.AUGUST, 1);
//        project.setEndDate(endDate);
//        GenericDAO<Project> projectDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getProjectDAO(OracleAbstractDAO.Roles.Administrator);
//        BigInteger projectID = projectDAO.insert(project);

//        Project project1 = projectDAO.getByPK(projectID);
//        System.out.println(project1);
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

//        Employee employee = new Employee();
//        employee.setAttribute("first name", "Andriy");
//        employee.setAttribute("last name", "Shulepa");
//        employee.setAttribute("manager ID", "123");
//
//        System.out.println(employee);
//        System.out.println(employee.getAttribute("manager Id"));
//        System.out.println("select ot.write \"Object Type Grant\", o.write \"Object Grant\" \n" +
//                "from lab02.object_type_grants ot\n" +
//                "left join LAB02.OBJECT_GRANTS o on ot.role =o.role and o.OBJECT_ID =?\n" +
//                "where ot.role = '" + "Administrator" + "'  and ot.OBJECT_TYPE_ID = (Select object_type_id from lab02.object_types where name ='" + "Project" + "')");
//        GenericDAO<Customer> customerDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.XML).getCustomerDAO(OracleAbstractDAO.Roles.Administrator);
//        Set<Customer>customers = customerDAO.getAll();
//        for(Customer customer:customers) {
//            System.out.println(customer);
//        }
//        Customer customer = new Customer();
//        customer.setName("ParamPamPam");
//        customer.setDescription("Place for your ad");
//        Set<BigInteger> projects = new HashSet<>();
//        projects.add(new BigInteger("777"));
//        projects.add(new BigInteger("888"));
//        projects.add(new BigInteger("999"));
//        customer.setProjects(projects);
//        customerDAO.insert(customer);
//        Customer customer = customerDAO.getByPK(new BigInteger("1201802151454506979"));
//        customerDAO.delete(customer);


//        String query = "select o.object_id\n" +
//                "from lab02.objects o\n" +
//                "join lab02.attributes attr on o.object_type_id = (Select object_type_id from lab02.object_types where name ='Project')\n" +
//                "left join lab02.params p on p.attribute_id = attr.attribute_id\n" +
//                " and p.object_id = o.object_id\n" +
//                " where attr.name = 'Customer ID' and p.VALUE = '1201802181613263442'";
//        System.out.println(query);

//        CustomerDAO dao = new CustomerDAO(OracleAbstractDAO.Roles.Administrator);
//        Set<BigInteger> set = dao.selectForeignAttribute("Project", "Customer ID", new BigInteger("1201802181613263442"));

        GenericDAO<Customer> customerDAO = DAOFactory.getDAOFactory(DAOFactory.DAOTypes.ORACLE).getCustomerDAO(OracleAbstractDAO.Roles.Administrator);
        Customer customer = customerDAO.getByPK(new BigInteger("1201802181613263442"));
        System.out.println(customer);
    }
}

