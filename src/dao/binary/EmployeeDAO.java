package dao.binary;

import Model.Employee;

import java.math.BigInteger;

public class EmployeeDAO extends BinaryAbstractDAO<Employee> {

    @Override
    String getFileName() {
        return "employees.ser";
    }

    @Override
    Employee prepareObjectWithId(Employee object, BigInteger id) {
        return new Employee(id, object);
    }
}
